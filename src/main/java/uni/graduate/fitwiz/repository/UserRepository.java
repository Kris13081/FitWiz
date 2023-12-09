package uni.graduate.fitwiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.graduate.fitwiz.model.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByEmail(String email);

    UserEntity getUserEntitiesByEmail(String email);

    UserEntity findUserEntityByUsername(String name);
}
