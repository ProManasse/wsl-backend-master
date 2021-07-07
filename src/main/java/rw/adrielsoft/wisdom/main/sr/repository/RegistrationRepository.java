package rw.adrielsoft.wisdom.main.sr.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.adrielsoft.wisdom.main.sr.domain.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, UUID>{

}
