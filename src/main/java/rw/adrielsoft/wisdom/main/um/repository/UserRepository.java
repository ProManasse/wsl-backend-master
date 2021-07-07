package rw.adrielsoft.wisdom.main.um.repository;

import org.springframework.stereotype.Repository;

import rw.adrielsoft.wisdom.main.um.models.User;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
