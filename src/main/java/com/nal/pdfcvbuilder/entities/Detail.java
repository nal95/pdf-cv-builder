package com.nal.pdfcvbuilder.entities;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Detail implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int level;
}
