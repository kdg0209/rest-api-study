package com.example.kdg.mapper;

import com.example.kdg.dao.AuthDao;
import org.mapstruct.Mapper;

@Mapper
public interface AuthMapper {

    AuthDao findByAccountUserId(String userId);
}
