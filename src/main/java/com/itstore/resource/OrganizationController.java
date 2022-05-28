package com.itstore.resource;

import com.itstore.core.data.dto.PageDTO;
import com.itstore.data.dto.OrganizationDTO;
import com.itstore.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService service;

    @GetMapping
    ResponseEntity<PageDTO<OrganizationDTO>> findAll(Pageable pageable, @RequestParam(required = false) String search) {
        return ResponseEntity.ok(service.findAll(pageable, search));
    }

    @GetMapping("/{id}")
    ResponseEntity<OrganizationDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }


    @PostMapping
    ResponseEntity<OrganizationDTO> create(@RequestBody OrganizationDTO organization) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(organization));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
