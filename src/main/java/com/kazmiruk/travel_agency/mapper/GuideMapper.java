package com.kazmiruk.travel_agency.mapper;

import com.kazmiruk.travel_agency.dto.GuideDto;
import com.kazmiruk.travel_agency.dto.GuideRequest;
import com.kazmiruk.travel_agency.dto.GuideResponse;
import com.kazmiruk.travel_agency.model.Guide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GuideMapper {

    GuideResponse toResponse(Guide guide);

    Iterable<GuideResponse> toResponse(Iterable<Guide> guides);

    @Mapping(target = "id", ignore = true)
    Guide toEntity(GuideRequest guideRequest);

    Guide toEntity(GuideDto guideDto);
}
