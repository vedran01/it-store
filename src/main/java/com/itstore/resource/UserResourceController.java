package com.itstore.resource;

import com.itstore.core.data.dto.PageDTO;
import com.itstore.data.dto.UserDTO;
import com.itstore.security.PermissionDTO;
import com.itstore.security.SecurityService;
import com.itstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserResourceController {

    private final UserService service;
    private final SecurityService securityService;

    @GetMapping
    ResponseEntity<PageDTO<UserDTO>> findAll(Pageable pageable, @RequestParam(required = false) String search) {
        return ResponseEntity.ok(service.findAll(pageable, search));
    }

    @GetMapping("/{id}/permissions")
    ResponseEntity<List<PermissionDTO>> getUserPermissions(@PathVariable String id) {
        return ResponseEntity.ok(securityService.getPermissions(id));
    }

}
