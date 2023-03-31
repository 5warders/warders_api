package com.warders.api.profile.domain.mapper;

import com.warders.api.common.base.BaseMapping;
import com.warders.api.profile.controller.dto.ProfileCreationRequest;
import com.warders.api.profile.domain.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileDtoMapper {

    @BaseMapping
    Profile toProfile(ProfileCreationRequest source);

}
