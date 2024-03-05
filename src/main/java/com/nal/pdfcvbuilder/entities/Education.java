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
public class Education implements Serializable {
    private static final long serialVersionUID = 1L;

    private String school;
    private String field;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private String summary;
}

