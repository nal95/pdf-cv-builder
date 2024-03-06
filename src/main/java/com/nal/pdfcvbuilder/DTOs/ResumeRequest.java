package com.nal.pdfcvbuilder.DTOs;

import com.nal.pdfcvbuilder.entities.ResumeData;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeRequest {
    private ResumeData data;
}
