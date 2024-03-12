package com.nal.pdfcvbuilder.services;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserImageService {

    byte[] saveImage(MultipartFile file, Long userId) throws IOException;

    byte[] getUserProfileImage(Long userId) throws IOException;
}
