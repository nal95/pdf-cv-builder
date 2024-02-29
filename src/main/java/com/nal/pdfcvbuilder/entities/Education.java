package com.nal.pdfcvbuilder.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
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
    private Date startDate;
    private Date endDate;
    private int duration;
    private String summary;
}

