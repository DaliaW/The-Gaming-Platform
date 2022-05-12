package guc.bttsBtngan.user.services;

import guc.bttsBtngan.user.data.UserUserInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// this interface is responsible for data access layer in postgresql
// to perform CRUD operations on user entity
@Repository
public interface UserRepository extends JpaRepository<UserUserInteraction, String> {
    @Query(value = "SELECT s FROM UserUserInteraction s WHERE s.email = ?1")
    Optional<UserUserInteraction> findByEmail2(String email);

    @Query(value = "SELECT s FROM UserUserInteraction s WHERE s.photoRef = ?1")
    Optional<UserUserInteraction> findByphotoRef(String photoRef);

    @Query(value = "SELECT s FROM UserUserInteraction s WHERE s.userName = ?1")
    Optional<UserUserInteraction> findByUsername(String username);
}
