package com.nal.pdfcvbuilder.entities;

import jakarta.persistence.*;
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

    private NetworkOption option;
    private String link;
    private String referenceName;
}

