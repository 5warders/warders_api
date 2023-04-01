package com.warders.api.profile.controller.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestParam;

public record CommentWriteRequest(@RequestParam @NotBlank String content) {

}
