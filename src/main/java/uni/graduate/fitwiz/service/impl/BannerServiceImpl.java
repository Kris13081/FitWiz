package uni.graduate.fitwiz.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uni.graduate.fitwiz.model.dto.BannerEntityDto;
import uni.graduate.fitwiz.model.entity.BannerEntity;
import uni.graduate.fitwiz.repository.BannerRepository;
import uni.graduate.fitwiz.service.BannerService;
import uni.graduate.fitwiz.service.GcsService;

import java.io.IOException;
import java.util.Optional;

@Service
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;
    private final GcsService gcsService;

    public BannerServiceImpl(BannerRepository bannerRepository,
                             GcsService gcsService) {
        this.bannerRepository = bannerRepository;
        this.gcsService = gcsService;
    }

    @Override
    public ResponseEntity<String> create(BannerEntityDto bannerEntityDto) throws IOException {

        Optional<BannerEntity> optionalBanner = bannerRepository.findByName(bannerEntityDto.getName());

        if (optionalBanner.isPresent()) {
            return new ResponseEntity<>("Banner with this name already exist!" ,HttpStatus.FOUND);
        }

        BannerEntity banner = mapDtoToEntity(bannerEntityDto);
        bannerRepository.save(banner);

        return new ResponseEntity<>("Banner created!", HttpStatus.CREATED);
    }

    private BannerEntity mapDtoToEntity(BannerEntityDto bannerEntityDto) throws IOException {

        BannerEntity newBanner = new BannerEntity();
        String bannerImage = gcsService.uploadBannerImage("fitwiz_images_bucket", bannerEntityDto.getImage());

        newBanner.setName(bannerEntityDto.getName());
        newBanner.setTitle(bannerEntityDto.getTitle());
        newBanner.setText(bannerEntityDto.getText());
        newBanner.setImagePath(bannerImage);

        return newBanner;
    }
}
