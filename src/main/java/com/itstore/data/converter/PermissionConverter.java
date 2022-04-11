package com.itstore.data.converter;

import com.itstore.core.data.converter.Converter;
import com.itstore.data.dto.PermissionDTO;
import com.itstore.data.dto.ResourceDTO;
import com.itstore.security.model.SecurityPermission;
import com.itstore.security.model.SecurityResource;
import org.springframework.stereotype.Component;

@Component
public class PermissionConverter implements Converter<PermissionDTO, SecurityPermission> {

    @Override
    public PermissionDTO convert(SecurityPermission source) {

        PermissionDTO dto = new PermissionDTO();
        dto.setId(source.getId());
        dto.setName(source.getName());
        dto.setKey(source.getKey());
        dto.setDescription(source.getDescription());
        dto.setEnabled(source.isEnabled());
        SecurityResource resource = source.getResource();
        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setId(resource.getId());
        resourceDTO.setName(resource.getName());
        resourceDTO.setKey(resource.getKey());
        resourceDTO.setDescription(resource.getDescription());
        dto.setResource(resourceDTO);
        return dto;
    }
}
