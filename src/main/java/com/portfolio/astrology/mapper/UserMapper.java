package com.portfolio.astrology.mapper;

import com.portfolio.astrology.dto.request.UserDTO;
import com.portfolio.astrology.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "dd-MM-yyyy")
    User toModel(UserDTO userDTO);
    UserDTO toDTO(User person);

}
