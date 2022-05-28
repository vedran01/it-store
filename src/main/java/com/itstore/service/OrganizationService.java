package com.itstore.service;

import com.itstore.core.data.converter.Converter;
import com.itstore.core.data.dto.PageDTO;
import com.itstore.data.dto.OrganizationDTO;
import com.itstore.model.Organization;
import com.itstore.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repository;
    private final Converter<OrganizationDTO, Organization> converter;

    //@PreAuthorize("hasAuthority('user.viewAll')")
    public PageDTO<OrganizationDTO> findAll(Pageable pageable, String search) {

        Page<Organization> organizations;

        if (StringUtils.isNotBlank(search)) {

            Example<Organization> example = Example.of(Organization.example(search), ExampleMatcher.matchingAny()
                    .withIgnoreNullValues()
                    .withIgnorePaths("enabled")
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

            organizations = repository.findAll(example, pageable);

        } else {
            organizations = repository.findAll(pageable);
        }

        return PageDTO.of(organizations, converter::convert);
    }


    //@PostAuthorize("hasPermission(returnObject, 'READ')")
    public OrganizationDTO findById(Long id) {
        return repository.findById(id)
                .map(converter::convert)
                .orElseThrow(() -> new NotFoundException("not found"));

    }

    //@PreAuthorize("hasAuthority('user.create')")
    public OrganizationDTO create(OrganizationDTO organizationDTO) {
        Organization organization = converter.convert(organizationDTO);
        return converter.convert(repository.save(organization));
    }

    //@PreAuthorize("hasPermission(#id, 'com.itstore.data.dto.UserDTO', 'DELETE')")
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
