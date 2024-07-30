package org.example.authservice.controller;
import jakarta.validation.Valid;
import org.example.authservice.entity.Role;
import org.example.authservice.service.RoleService;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public Role createRole(@RequestBody @Valid Role role){
        return roleService.createRole(role);
    }


    @PutMapping("/update")
    public Role updateRole(@RequestBody @Valid Role role){
        return roleService.updateRole(role);
    }
    @DeleteMapping()
    public void deleteRole(@RequestParam String roleName){
        roleService.deleteRole(roleName);
    }


    @GetMapping("getAccountRoles")
    public String getRole(@RequestHeader(value = "x-account-uuid") UUID accountUuid){
        return "get access ok with " + accountUuid.toString();
    }

}
