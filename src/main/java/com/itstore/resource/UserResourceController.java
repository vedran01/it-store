package com.itstore.resource;

import com.itstore.core.data.dto.PageDTO;
import com.itstore.data.dto.UserDTO;
import com.itstore.data.dto.UserInfoDTO;
import com.itstore.security.PermissionDTO;
import com.itstore.security.SecurityService;
import com.itstore.security.token.JWTAuthenticationToken;
import com.itstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/info")
    ResponseEntity<UserInfoDTO> findUserInfo(Authentication details) {
        JWTAuthenticationToken token = (JWTAuthenticationToken) details;
        return ResponseEntity.ok(service.getUserInfo(token.getClaims().getUserId()));
    }

    @PostMapping
    ResponseEntity<UserDTO> create(@RequestBody UserDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(user));
    }

    @PutMapping
    ResponseEntity<UserDTO> update(@RequestBody UserDTO user) {
        return ResponseEntity.ok(service.update(user));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/permissions")
    ResponseEntity<List<PermissionDTO>> getUserPermissions(@PathVariable String id) {
        return ResponseEntity.ok(securityService.getPermissions(id));
    }

}
