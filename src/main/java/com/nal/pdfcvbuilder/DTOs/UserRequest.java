package com.nal.pdfcvbuilder.DTOs;

import com.nal.pdfcvbuilder.entities.ResumeData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
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
    private ResumeData data;
}