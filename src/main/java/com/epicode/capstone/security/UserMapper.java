package com.epicode.capstone.security;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserCompleteResponse userToUserCompleteResponse(User user);
    List<UserCompleteResponse> usersToUserCompleteResponses(List<User> users);
}
