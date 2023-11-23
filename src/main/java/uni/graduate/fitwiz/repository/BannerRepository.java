package uni.graduate.fitwiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.graduate.fitwiz.model.entity.BannerEntity;

@Repository
public interface BannerRepository extends JpaRepository<BannerEntity, Long> {
}
