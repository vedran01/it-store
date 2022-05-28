package com.itstore.data.converter;


import com.itstore.core.data.converter.Converter;
import com.itstore.data.dto.OrganizationDTO;
import com.itstore.model.Contact;
import com.itstore.model.Location;
import com.itstore.model.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationConverter implements Converter<OrganizationDTO, Organization> {

    @Override
    public OrganizationDTO convert(Organization source) {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setName(source.getName());

        Contact contact = source.getContact();
        Location location = source.getLocation();

        dto.setCountry(location.getCountry());
        dto.setCity(location.getCity());
        dto.setZip(location.getZip());
        dto.setStreet(location.getStreet());
        dto.setEmail(contact.getEmail());
        dto.setPhone(contact.getPhone());
        dto.setEnabled(source.isEnabled());
        dto.setCreated(source.getCreated());
        return dto;
    }

    @Override
    public Organization convert(OrganizationDTO source, Object... additional) {
        return Converter.super.convert(source, additional);
    }
}
