package com.nal.pdfcvbuilder.DTOs;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String nationality;
    private String location;
    private String summary;
    private String objectives;
    private String profession;
    private String mobile;
    private String image;
    private int years;
    private ResumeRequest data;
}