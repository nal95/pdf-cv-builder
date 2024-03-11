package com.nal.pdfcvbuilder.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Network implements Serializable {
    private static final long serialVersionUID = 1L;

    private String referenceName;
    private String link;
}

