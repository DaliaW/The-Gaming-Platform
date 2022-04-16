package guc.bttsBtngan.user.services;

import guc.bttsBtngan.user.data.UserUserInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// this interface is responsible for data access layer in postgresql
@Repository
public interface UserRepository extends JpaRepository<UserUserInteraction, Long> {
    @Query(value = "SELECT s FROM UserUserInteraction s WHERE s.email = ?1")
    Optional<UserUserInteraction> findByEmail(String email);
}
