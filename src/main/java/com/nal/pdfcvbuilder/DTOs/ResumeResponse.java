package com.nal.pdfcvbuilder.DTOs;

import com.nal.pdfcvbuilder.entities.ResumeData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResumeResponse {
    private UserResponse user;
    private ResumeData data;
}
