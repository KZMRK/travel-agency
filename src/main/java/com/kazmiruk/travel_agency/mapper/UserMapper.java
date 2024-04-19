package com.kazmiruk.travel_agency.mapper;

import com.kazmiruk.travel_agency.model.dto.UserDto;
import com.kazmiruk.travel_agency.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

}
