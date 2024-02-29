package com.nal.pdfcvbuilder.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CVData implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Network> networks;
    private List<Education> educations;
    private List<WorkExperience> workExperiences;
    private List<TechnicalExperience> technicalExperiences;
}