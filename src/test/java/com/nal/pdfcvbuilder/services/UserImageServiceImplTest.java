package com.nal.pdfcvbuilder.services;

import com.nal.pdfcvbuilder.entities.UserImageDetail;
import com.nal.pdfcvbuilder.helpers.Response;
import com.nal.pdfcvbuilder.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserImageServiceImplTest {

    @Mock
    private Environment env;

    @Mock
    private UserRepository repository;

    @Spy
    @InjectMocks
    private UserImageServiceImpl userImageService;

    String tempDir = "src/test/java/com/nal/pdfcvbuilder/resources/images";
    String testFilename = "test.png";
    byte[] content = null;
    MockMultipartFile file = null;
    Path testPath = Paths.get(tempDir + "/" + testFilename);
    Response responseHelper = null;

    @BeforeEach
    void Init() throws IOException {
        content = Files.readAllBytes(testPath);
        file = new MockMultipartFile("image", "test.png", "image/png", content);
        responseHelper = new Response();
    }

    @Test
    public void saveImage_shouldSaveImageAndUpdateDatabase() throws IOException {
        // When
        UserImageDetail detail = responseHelper.getImageDetail(tempDir);
        doReturn(detail).when(userImageService).getUserImageDetails(1L);

        // Call the method to test
        byte[] savedContent = userImageService.saveImage(file, 1L);

        // Then
        // Verify that the user's image is updated in the database
        assertEquals("John_Doe.jpeg", detail.getUser().getImage());
        verify(repository, times(1)).save(detail.getUser());

        // Verify the returned content
        assertNotEquals(content, savedContent);
        Files.delete(Paths.get(tempDir + "/John_Doe.jpeg"));
    }

    @Test
    public void convertToProfileImage_shouldConvertToJpeg() throws IOException {

        // Call the method to test
        byte[] profileImage = userImageService.convertToProfileImage(file);

        // Verify that the returned content is not null
        assertNotNull(profileImage);
    }

    @Test
    public void getUserImageDetails_shouldReturnUserImageDetails() {
        // Given
        Path path = Paths.get(tempDir, "");
        UserImageDetail detailsExpected = responseHelper.getImageDetail(tempDir);
        detailsExpected.setImagePath(path);

        // When
        when(repository.findById(1L)).thenReturn(Optional.of(responseHelper.getUser()));
        when(env.getProperty("app.image.base-path")).thenReturn(tempDir);
        when(env.getProperty("app.image.upload-path")).thenReturn("");

        UserImageDetail detailsResult = userImageService.getUserImageDetails(1L);

        // Then
        assertEquals(detailsExpected.getUser().getId(), detailsResult.getUser().getId());
        assertEquals(detailsExpected.getUser().getFirstName(), detailsResult.getUser().getFirstName());
        assertEquals(detailsExpected.getUser().getLastName(), detailsResult.getUser().getLastName());
        assertEquals(detailsExpected.getImageName(), detailsResult.getImageName());
        assertEquals(detailsExpected.getImagePath(), detailsResult.getImagePath());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void getUserProfileImage_shouldReturnUserProfileImage() throws IOException {
        // When
        UserImageDetail detail = responseHelper.getImageDetail(tempDir);
        detail.setImageName("");
        detail.setImagePath(testPath);

        doReturn(detail).when(userImageService).getUserImageDetails(1L);

        // Call the method to test
        byte[] userProfileImage = userImageService.getUserProfileImage(1L);

        // Verify that the returned content is not null
        assertNotNull(userProfileImage);

        // Verify that the returned content matches the mocked content
        assertArrayEquals(content, userProfileImage);
    }
}