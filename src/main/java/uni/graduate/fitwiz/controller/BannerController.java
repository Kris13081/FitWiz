package uni.graduate.fitwiz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.model.dto.BannerEntityDto;
import uni.graduate.fitwiz.model.dto.BannerUpdateDto;
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

    @PostMapping("/banner/delete/{id}")
    public ModelAndView deleteBanner(@PathVariable @RequestParam Long id) {
        bannerService.delete(id);

        return new ModelAndView("redirect:/api/admins/index");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/banner/update/{id}")
    public HttpStatus updateBanner(@PathVariable Long id, @RequestBody BannerUpdateDto bannerUpdateDto) {
        return bannerService.updateBanner(id, bannerUpdateDto);
    }


}
