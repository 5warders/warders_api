package com.warders.api.profile.controller.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record ProfileCreationRequest(@NotBlank String description, MultipartFile imageFile) {

}
