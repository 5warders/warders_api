package com.warders.api.profile.domain.mapper;

import com.warders.api.profile.controller.dto.ProfileDetailResponse;
import com.warders.api.profile.controller.dto.ProfileResponse;
import com.warders.api.profile.domain.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileDtoMapper {

    @Mapping(source = "user.userName", target = "userName")
    ProfileResponse toResponse(Profile source);

    @Mapping(source = "user.userName", target = "userName")
    ProfileDetailResponse toDetailResponse(Profile source);

}
