package com.portfolio.astrology.mapper;

import com.portfolio.astrology.dto.request.PersonDTO;
import com.portfolio.astrology.dto.request.UserDTO;
import com.portfolio.astrology.model.Person;
import com.portfolio.astrology.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    User toModel(UserDTO userDTO);
    UserDTO toDTO(User user);

}
