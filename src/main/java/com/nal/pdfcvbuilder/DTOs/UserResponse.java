package com.nal.pdfcvbuilder.DTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String profession;
    private String image;
}
