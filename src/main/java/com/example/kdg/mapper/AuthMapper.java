package com.example.kdg.mapper;

import com.example.kdg.dao.AuthDao;
import com.example.kdg.dto.auth.AuthDto;
import org.mapstruct.Mapper;

@Mapper
public interface AuthMapper {

    AuthDao findByAccountUserId(AuthDto authDto);
}
