package com.warders.api.profile.controller.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestParam;

public record ProfileCreationRequest(@RequestParam @NotBlank String description) {

}
