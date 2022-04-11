package com.itstore.data.converter;

import com.itstore.core.data.converter.Converter;
import com.itstore.data.dto.UserDTO;
import com.itstore.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<UserDTO, User> {

    @Override
    public UserDTO convert(User source) {
        UserDTO user = new UserDTO();
        user.setId(source.getId());
        user.setFirstname(source.getFirstname());
        user.setLastname(source.getLastname());
        user.setUsername(source.getUsername());
        return user;

    }
}
