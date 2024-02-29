package com.nal.pdfcvbuilder.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CVData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Set<Network> networks;
    private Set<Education> educations;
    private Set<WorkExperience> workExperiences;
    private Set<TechnicalExperience> technicalExperiences;
    private Set<String> tools;
    private Set<String> methodologies;
    private Set<String> skills;
}