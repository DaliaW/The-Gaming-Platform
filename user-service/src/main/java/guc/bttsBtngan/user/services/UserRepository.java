package guc.bttsBtngan.user.services;

import guc.bttsBtngan.user.data.UserUserInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// this interface is responsible for data access layer in postgresql
// to perform CRUD operations on user entity
@Repository
public interface UserRepository extends JpaRepository<UserUserInteraction, String> {
    //@Query(value = "SELECT s FROM UserUserInteraction s WHERE s.email = ?1")
    Optional<UserUserInteraction> findByEmailIgnoreCase(String email);

    @Query(value = "SELECT s FROM UserUserInteraction s WHERE s.photoRef = ?1")
    Optional<UserUserInteraction> findByphotoRef(String photoRef);

    //@Query(value = "SELECT s FROM UserUserInteraction s WHERE s.username = ?1")
    Optional<UserUserInteraction> findByUsernameIgnoreCase(String username);
}
