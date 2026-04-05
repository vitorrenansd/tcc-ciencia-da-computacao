package com.tcc.serveme.api.restaurant.service;

import com.tcc.serveme.api.exception.BadRequestException;
import com.tcc.serveme.api.exception.NotFoundException;
import com.tcc.serveme.api.restaurant.dto.RestaurantConfigResponse;
import com.tcc.serveme.api.restaurant.dto.UpdateRestaurantConfigRequest;
import com.tcc.serveme.api.restaurant.entity.RestaurantConfig;
import com.tcc.serveme.api.restaurant.mapper.RestaurantConfigMapper;
import com.tcc.serveme.api.restaurant.repository.RestaurantConfigRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class RestaurantConfigService {
    private static final Long CONFIG_ID = 1L;
    private final RestaurantConfigRepository restaurantConfigRepo;

    @Value("${serve-me.images.path}")
    private String uploadDir;
    @Value("${serve-me.images.base-url}")
    private String baseUrl;

    @Autowired
    public RestaurantConfigService(RestaurantConfigRepository restaurantConfigRepo) {
        this.restaurantConfigRepo = restaurantConfigRepo;
    }

    public RestaurantConfigResponse getConfig() {
        RestaurantConfig config = restaurantConfigRepo.find(CONFIG_ID)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Configuração não encontrada"));
        return RestaurantConfigMapper.toResponse(config, baseUrl);
    }

    public void updateConfig(UpdateRestaurantConfigRequest request) {
        RestaurantConfig config = restaurantConfigRepo.find(CONFIG_ID)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Configuração não encontrada"));

        RestaurantConfig updated = new RestaurantConfig(
                config.getId(),
                request.name(),
                config.getIconFilename()
        );
        restaurantConfigRepo.update(updated);
    }

    public void updateIcon(MultipartFile file) {
        RestaurantConfig restConf = restaurantConfigRepo.find(CONFIG_ID)
                .orElseThrow(NotFoundException::new);

        // Valida se é imagem
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new BadRequestException("Formato de arquivo inválido.");
        }

        // Gera nome único com UUID mantendo a extensão original
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID().toString() + "." + extension;

        // Salva o arquivo no diretório configurado
        try {
            Path destination = Paths.get(uploadDir).resolve("config/").resolve(filename);
            Files.createDirectories(destination.getParent());
            file.transferTo(destination);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem.", e);
        }

        // Deleta ícone anterior se existia
        if (restConf.getIconFilename() != null) {
            try {
                Path old = Paths.get(uploadDir).resolve("config/").resolve(restConf.getIconFilename());
                Files.deleteIfExists(old);
            } catch (IOException ignored) {}
        }

        // Atualiza o filename no banco
        restaurantConfigRepo.updateIconFilename(CONFIG_ID, filename);
    }
}