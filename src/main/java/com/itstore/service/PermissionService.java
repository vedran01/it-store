package com.itstore.service;

import com.itstore.data.converter.PermissionConverter;
import com.itstore.data.dto.PermissionDTO;
import com.itstore.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionConverter converter;
    private final PermissionRepository repository;

    public List<PermissionDTO> findAll() {
        return repository.findAll().stream().map(converter::convert)
                .collect(Collectors.toList());
    }

}
