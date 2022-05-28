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

        Organization organization = new Organization();
        organization.setName(source.getName());

        Contact contact = new Contact(source.getEmail(), source.getPhone());
        organization.setContact(contact);

        Location location = new Location(source.getCountry(), source.getCity(), source.getStreet(), source.getZip());
        organization.setLocation(location);

        organization.setEnabled(source.isEnabled());

        return Converter.super.convert(source, additional);
    }
}
