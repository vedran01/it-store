package com.itstore.data.converter;

import com.itstore.core.data.converter.Converter;
import com.itstore.data.dto.UserDTO;
import com.itstore.data.dto.UserSettingsDTO;
import com.itstore.model.Contact;
import com.itstore.model.Location;
import com.itstore.model.User;
import com.itstore.security.model.SecurityIdentity;
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

    public UserSettingsDTO convertToSettings(User user) {
        UserSettingsDTO dto = new UserSettingsDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());

        Contact contact = user.getContact();;

        dto.setEmail(contact.getEmail());
        dto.setPhone(contact.getPhone());

        Location location = user.getLocation();

        dto.setCountry(location.getCountry());
        dto.setCity(location.getCity());
        dto.setZip(location.getZip());
        dto.setStreet(location.getStreet());

        dto.setCreated(user.getCreated());
        dto.setModified(user.getModified());
        dto.setEnabled(user.isEnabled());

        SecurityIdentity identity = user.getIdentity();
        dto.setType(identity.getType());
        dto.setEnabled2fa(identity.isEnabled2fa());
        dto.setConfigured2fa(identity.isConfigured2fa());
        dto.setUuid(identity.getUuid());
        dto.setValidFrom(identity.getValidFrom());
        dto.setValidTill(identity.getValidTill());
        dto.setPasswordChanged(identity.getPasswordChanged());
        return dto;
    }
}
