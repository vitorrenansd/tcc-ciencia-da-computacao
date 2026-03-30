package com.tcc.serveme.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageConfig implements WebMvcConfigurer {

    @Value("${serve-me.images.path}")
    private String imagesPath;

    // Serve os arquivos da pasta de imagens como recursos estáticos em /images/**
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = imagesPath.startsWith("/") || imagesPath.contains(":")
                ? "file:" + imagesPath          // Caminho absoluto (ex: C:/imagens/)
                : "file:" + System.getProperty("user.dir") + "/" + imagesPath; // Relativo

        registry.addResourceHandler("/images/**")
                .addResourceLocations(location);
    }
}