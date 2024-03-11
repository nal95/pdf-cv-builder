package com.nal.pdfcvbuilder.DTOs;

import com.nal.pdfcvbuilder.entities.ResumeData;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeResponse {
    private UserResponse userResponse;
    private ResumeData resumeData;
}
