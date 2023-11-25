package uni.graduate.fitwiz.service;

import org.springframework.http.ResponseEntity;
import uni.graduate.fitwiz.model.dto.BannerEntityDto;
import uni.graduate.fitwiz.model.entity.BannerEntity;

import java.io.IOException;
import java.util.List;

public interface BannerService {
    ResponseEntity<String> create(BannerEntityDto bannerEntityDto) throws IOException;

    List<BannerEntity> getBanners();
}
