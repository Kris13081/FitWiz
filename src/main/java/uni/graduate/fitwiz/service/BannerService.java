package uni.graduate.fitwiz.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uni.graduate.fitwiz.model.dto.BannerDisplayDto;
import uni.graduate.fitwiz.model.dto.BannerEntityDto;
import uni.graduate.fitwiz.model.dto.BannerUpdateDto;

import java.io.IOException;
import java.util.List;

public interface BannerService {
    ResponseEntity<String> create(BannerEntityDto bannerEntityDto) throws IOException;

    List<BannerDisplayDto> getBanners();

    void delete(Long id);

    HttpStatus updateBanner(Long id, BannerUpdateDto bannerUpdateDto);
}
