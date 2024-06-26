package Pegas.repository;

import Pegas.entity.Security;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityRepository extends JpaRepository<Security,Long> {
    Optional<Security> findByEmail(String userEmail);
}
