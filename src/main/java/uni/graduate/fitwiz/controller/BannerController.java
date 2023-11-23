package uni.graduate.fitwiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uni.graduate.fitwiz.model.dto.BannerEntityDto;
import uni.graduate.fitwiz.service.BannerService;

import java.io.IOException;

@RestController
@RequestMapping("/api/admins/management")
public class BannerController {

    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @PostMapping("/add-banner")
    public ResponseEntity<String> createBanner(BannerEntityDto bannerEntityDto) throws IOException {
        return bannerService.create(bannerEntityDto);
    }


}
