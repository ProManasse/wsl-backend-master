package rw.adrielsoft.wisdom.main.um.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rw.adrielsoft.wisdom.main.um.models.ERole;
import rw.adrielsoft.wisdom.main.um.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}