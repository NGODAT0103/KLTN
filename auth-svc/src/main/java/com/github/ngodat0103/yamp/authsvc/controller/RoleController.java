package com.github.ngodat0103.yamp.authsvc.controller;


import com.github.ngodat0103.yamp.authsvc.dto.RoleDto;
import com.github.ngodat0103.yamp.authsvc.service.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@Slf4j
@RequestMapping(value = "/roles")
@AllArgsConstructor
@SecurityRequirement(name = "oauth2")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    private RoleService roleService;
    @GetMapping(produces = "application/json")
    public Set<RoleDto> getRole() {
        return roleService.getRole();
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createRole(@RequestBody RoleDto roleDto){
        log.debug("Controller createRole method called");
        roleService.addRole(roleDto);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteRole(String roleName){
        log.debug("Controller updateRole method called");
        roleService.deleteRole(roleName);
    }
}