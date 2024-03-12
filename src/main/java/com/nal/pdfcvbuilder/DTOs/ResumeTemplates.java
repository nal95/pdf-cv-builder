package com.nal.pdfcvbuilder.DTOs;

import lombok.Getter;

@Getter
public enum ResumeTemplates {

    SUPER("superTemplate/resume", "superTemplate/style.css" ),
    ERROR("error", "error/style.css" ),
    SIMPLE("simpleTemplate/resume", "simpleTemplate/style.css" );

    private final String templatePath;
    private final String templateStylePath;

    ResumeTemplates(String templatePath, String templateStylePath) {
        this.templatePath = templatePath;
        this.templateStylePath = templateStylePath;
    }
}
