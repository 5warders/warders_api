package com.warders.api.profile.controller.dto;

import java.time.LocalDateTime;

public record ProfileResponse(
    Long profileId,
    String description,
    String imageUrl,
    int subscribeCount,
    String userName,
    LocalDateTime registerDtm,
    LocalDateTime modifyDtm
) {

}
