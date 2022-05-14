package com.itstore.data.converter;

import com.itstore.core.data.converter.Converter;
import com.itstore.data.dto.UserInfoDTO;
import com.itstore.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserInfoConverter implements Converter<UserInfoDTO, User> {
    @Override
    public UserInfoDTO convert(User source) {
        UserInfoDTO dto = new UserInfoDTO();
        dto.setId(source.getId());
        dto.setUsername(source.getUsername());
        dto.setFirstname(source.getFirstname());
        dto.setLastname(source.getLastname());
        return dto;
    }
}
