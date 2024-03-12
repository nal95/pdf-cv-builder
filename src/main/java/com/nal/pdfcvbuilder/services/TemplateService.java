package com.nal.pdfcvbuilder.services;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class TemplateService {

    private final Environment env;

    public TemplateService(Environment env) {
        this.env = env;
    }

    public byte[] getTemplateStyle(String templateName) throws IOException {

        String basePath = Objects.requireNonNull(env.getProperty("app.style.base-path"));

        String pathString = basePath + templateName;

        Path stylePath = Paths.get(pathString);

        return Files.readAllBytes(stylePath);
    }
}
