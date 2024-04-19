package com.kazmiruk.travel_agency.mapper;

import com.kazmiruk.travel_agency.model.dto.GuideDto;
import com.kazmiruk.travel_agency.model.entity.Guide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GuideMapper {

    GuideDto toDto(Guide guide);

    Guide toEntity(GuideDto guideDto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Guide guide, GuideDto guideDto);
}
