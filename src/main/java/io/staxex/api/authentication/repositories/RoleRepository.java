package io.staxex.api.authentication.repositories;

import io.staxex.api.authentication.enums.ERole;
import io.staxex.api.authentication.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
