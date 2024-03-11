package com.nal.pdfcvbuilder.DTOs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String profession;
    private String image;
    private String nationality;
    private String location;
    private String summary;
    private String title;
    private String mobile;
    private int years;
}
