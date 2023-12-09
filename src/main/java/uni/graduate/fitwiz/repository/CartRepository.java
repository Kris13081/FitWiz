package uni.graduate.fitwiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.graduate.fitwiz.model.entity.CartEntity;
import uni.graduate.fitwiz.model.entity.UserEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

     CartEntity findByUser(UserEntity user);
}
