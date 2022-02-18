package com.example.kdg.mapper;

import com.example.kdg.dao.User;
import org.mapstruct.Mapper;

@Mapper
public interface AuthMapper {

    User findByEmail(String email);
}
