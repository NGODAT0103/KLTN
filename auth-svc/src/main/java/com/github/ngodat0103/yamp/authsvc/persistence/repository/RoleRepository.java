package com.github.ngodat0103.yamp.authsvc.persistence.repository;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findRoleByRoleName(String roleName);
    Set<Role> findRolesByRoleNameIn(Set<String> roleNames);
    boolean existsByRoleName(String roleName);
}
