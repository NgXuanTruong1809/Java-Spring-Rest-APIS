package vn.hoidanit.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Role;

@RestController
public class RoleController {
    public ResponseEntity<Role> create(@Valid @RequestBody Role reqRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
