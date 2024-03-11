package com.nal.pdfcvbuilder.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperience implements Serializable {
    private static final long serialVersionUID = 1L;

    private String company;
    private String companyCity;
    private String companyLink;
    private String occupiedPosition;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private String summary;
}
