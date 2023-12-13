package com.Bookshop.service;



import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties(prefix = "app.file.storage")
    public static class ConfigProperties {
        private String path;
    }

    private Path root;

    public ImageService(ConfigProperties properties) {
        this.root = Paths.get(properties.getPath());
    }

    public String store(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            //todo create exception
            throw new IllegalStateException();
        }

        String generatedString = genString();
        String fullName = generatedString + Objects.requireNonNull(file.getOriginalFilename());

        Path destinationFile = this.root.resolve(
                        Paths.get(fullName))
                .normalize().toAbsolutePath();
        if (!destinationFile.getParent().equals(this.root.toAbsolutePath())) {
            //todo create exception
            throw new RuntimeException(
                    "Cannot store file outside current directory.");
        }
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }

        return fullName;
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                //todo create exception
                throw new RuntimeException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            //todo create exception
            throw new RuntimeException(
                    "Could not read file: " + filename, e);
        }
    }


    private static String genString() {
        byte[] array = new byte[10];
        new Random().nextBytes(array);
        byte[] generatedArray = Base64.getEncoder().encode(array);

        return new String(generatedArray, StandardCharsets.UTF_8);
    }
}
