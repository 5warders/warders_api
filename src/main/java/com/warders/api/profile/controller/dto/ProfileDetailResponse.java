package com.warders.api.profile.controller.dto;

import com.warders.api.profile.domain.Comment;
import java.time.LocalDateTime;
import java.util.List;

public record ProfileDetailResponse(
    Long profileId,
    String description,
    String imageUrl,
    int subscribeCount,
    String userName,
    LocalDateTime registerDtm,
    LocalDateTime modifyDtm,
    List<Comment> comments
    ) {

}
