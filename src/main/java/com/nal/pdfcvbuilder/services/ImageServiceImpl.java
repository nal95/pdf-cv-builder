package com.nal.pdfcvbuilder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class ImageServiceImpl implements ImageService {

    private final Environment env;

    public ImageServiceImpl(Environment env) {
        this.env = env;
    }

    @Override
    public String saveImage(MultipartFile file) throws IOException {
//        try {
            String fileName = file.getOriginalFilename();
            String relativePath = Paths.get(Objects.requireNonNull(env.getProperty("app.image.upload-path")), fileName).toString();
            String fullPath = Paths.get(Objects.requireNonNull(env.getProperty("app.image.base-path")), relativePath).toString();

            file.transferTo(new File(fullPath));
            return relativePath;
//        } catch (IOException e) {
//            throw new RuntimeException("Error saving image", e);
//        }
    }
}
