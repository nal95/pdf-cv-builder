package com.nal.pdfcvbuilder.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    private Environment environment;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    public void testSaveImage() throws Exception {
        // Given
        String imageTestPath = "src/test/java/com/nal/pdfcvbuilder/resources/";

        // Create a mock MultipartFile
        Path imagePath = Paths.get(imageTestPath, "testImage.jpg");
        byte[] fileContent = Files.readAllBytes(imagePath);
        MockMultipartFile file = new MockMultipartFile("testFile", "testFile.jpg", "image/jpeg", fileContent);

        // When
        when(environment.getProperty("app.image.base-path")).thenReturn("src/main/resources");
        when(environment.getProperty("app.image.upload-path")).thenReturn("images");


        // Test the saveImage method
        String result = imageService.saveImage(file);

        // Verify that the file was saved to the correct location
        String expectedPath = "src/main/resources/images/testFile.jpg";
        File savedFile = new File(expectedPath);

        // Then
        assertTrue(savedFile.exists());
        assertEquals("images/testFile.jpg", result.replace("\\", "/"));

        // Cleanup: Delete the created file after the test
        savedFile.deleteOnExit();
    }
}