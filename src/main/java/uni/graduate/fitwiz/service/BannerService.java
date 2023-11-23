package uni.graduate.fitwiz.service;

import org.springframework.http.ResponseEntity;
import uni.graduate.fitwiz.model.dto.BannerEntityDto;

import java.io.IOException;

public interface BannerService {
    ResponseEntity<String> create(BannerEntityDto bannerEntityDto) throws IOException;
}
