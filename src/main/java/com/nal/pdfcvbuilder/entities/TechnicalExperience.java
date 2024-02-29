package com.nal.pdfcvbuilder.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalExperience implements Serializable {
    private static final long serialVersionUID = 1L;

    private String topic; // example: PROGRAMMING, FRAMEWORK, DATABASE, CLOUD
    private Set<Detail> details;
}

