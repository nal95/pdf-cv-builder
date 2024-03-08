package com.nal.pdfcvbuilder.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumeData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Set<Network> networks;
    private Set<Education> educations;
    private Set<WorkExperience> workExperiences;
    private Set<TechnicalExperience> technicalExperiences;
    private Set<String> tools;
    private Set<String> methodologies;
    private Set<String> skills;
    private Set<String> hobbiesAndInterest;
    private Set<String> trainingsAndCertifications;
}