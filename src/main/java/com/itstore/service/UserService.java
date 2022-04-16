package com.itstore.service;

import com.itstore.core.data.converter.Converter;
import com.itstore.core.data.dto.PageDTO;
import com.itstore.data.dto.UserDTO;
import com.itstore.model.User;
import com.itstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final Converter<UserDTO, User> converter;

    @PreAuthorize("hasAuthority('user.viewAll')")
    public PageDTO<UserDTO> findAll(Pageable pageable, String search) {

        Page<User> users;

        if (StringUtils.isNotBlank(search)) {

            Example<User> example = Example.of(User.example(search), ExampleMatcher.matchingAny()
                    .withIgnoreNullValues()
                    .withIgnorePaths("enabled")
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

            users = repository.findAll(example, pageable);

        } else {
            users = repository.findAll(pageable);
        }

        return PageDTO.of(users, converter::convert);
    }


    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public UserDTO findById(Long id) {
        return repository.findById(id)
                .map(converter::convert)
                .orElseThrow(() -> new NotFoundException("not found"));

    }

    @PreAuthorize("hasAuthority('user.create')")
    public UserDTO create(UserDTO user) {
        return null;
    }

    @PreAuthorize("hasPermission(#user, 'WRITE')")
    public UserDTO update(UserDTO user) {
        return null;
    }

    @PreAuthorize("hasPermission(#id, 'com.itstore.data.dto.UserDTO', 'DELETE')")
    public void deleteById(Long id) {

    }
}
