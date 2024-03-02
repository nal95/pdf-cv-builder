package com.nal.pdfcvbuilder.entities;

import lombok.*;

import java.nio.file.Path;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserImageDetail {
    private String imageName;
    private Path imagePath;
    private User user;
}
