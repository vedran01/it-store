package com.itstore.resource;

import com.itstore.data.dto.PermissionDTO;
import com.itstore.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionResourceController {

    private final PermissionService service;

    @GetMapping
    ResponseEntity<List<PermissionDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

}
