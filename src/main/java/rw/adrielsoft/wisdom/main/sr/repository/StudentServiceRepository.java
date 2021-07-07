package rw.adrielsoft.wisdom.main.sr.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.adrielsoft.wisdom.main.sr.domain.StudentService;

public interface StudentServiceRepository extends JpaRepository<StudentService, UUID>{

}
