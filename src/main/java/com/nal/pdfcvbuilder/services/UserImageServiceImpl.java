package com.nal.pdfcvbuilder.services;

import com.nal.pdfcvbuilder.entities.User;
import com.nal.pdfcvbuilder.entities.UserImageDetail;
import com.nal.pdfcvbuilder.pdfCvBuilderExceptions.UserNotFoundException;
import com.nal.pdfcvbuilder.repositories.UserRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class UserImageServiceImpl implements UserImageService {

    private final Environment env;
    private final UserRepository repository;

    public UserImageServiceImpl(Environment env, UserRepository repository) {
        this.env = env;
        this.repository = repository;
    }

    @Override
    public byte[] saveImage(MultipartFile file, Long userId) throws IOException {

        UserImageDetail imageDetails = getUserImageDetails(userId);

        byte[] profileImageContent = convertToProfileImage(file);

        Path filePath = imageDetails.getImagePath().resolve(imageDetails.getImageName());
        Files.write(filePath, profileImageContent);

        // update user image in db
        imageDetails.getUser().setImage(imageDetails.getImageName());
        repository.save(imageDetails.getUser());

        return profileImageContent;
    }


    private byte[] convertToProfileImage(MultipartFile file) throws IOException {
        // Get BufferedImage from MultipartFile
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        BufferedImage jpegImage = convertToJpeg(originalImage);

        return convertToByteArray(jpegImage);
    }

    @Override
    public byte[] getUserProfileImage(Long userId) throws IOException {
        UserImageDetail imageDetails = getUserImageDetails(userId);
        // Create a mock MultipartFile
        Path filePath = imageDetails.getImagePath().resolve(imageDetails.getImageName());

        return Files.readAllBytes(filePath);
    }

    private UserImageDetail getUserImageDetails(Long userId) {
        User user = repository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with ID " + userId + " not found"));

        String basePath = Objects.requireNonNull(env.getProperty("app.image.base-path"));
        String uploadPath = Objects.requireNonNull(env.getProperty("app.image.upload-path"));

        String imageName = user.getFirstName() + "_" + user.getLastName() + ".jpeg";

        Path imagePath = Paths.get(basePath, uploadPath);

        return UserImageDetail.builder()
                .imagePath(imagePath)
                .imageName(imageName)
                .user(user)
                .build();
    }


    private BufferedImage convertToJpeg(BufferedImage originalImage) {
        // Create a new BufferedImage with RGB type
        BufferedImage jpegImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        // Draw the original image onto the new image with RGB type
        // also look at https://stackoverflow.com/questions/62800886/how-to-convert-any-image-to-jpg for alternative
        jpegImage.createGraphics().drawImage(originalImage, 0, 0, null);

        return jpegImage;
    }


    private byte[] convertToByteArray(BufferedImage image) throws IOException {
        // Write the BufferedImage to a ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", byteArrayOutputStream);

        // Convert the ByteArrayOutputStream to a byte array
        return byteArrayOutputStream.toByteArray();
    }

}
