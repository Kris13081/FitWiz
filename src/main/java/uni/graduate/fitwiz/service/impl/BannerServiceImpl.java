package uni.graduate.fitwiz.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uni.graduate.fitwiz.model.dto.BannerDisplayDto;
import uni.graduate.fitwiz.model.dto.BannerEntityDto;
import uni.graduate.fitwiz.model.dto.BannerUpdateDto;
import uni.graduate.fitwiz.model.entity.BannerEntity;
import uni.graduate.fitwiz.repository.BannerRepository;
import uni.graduate.fitwiz.service.BannerService;
import uni.graduate.fitwiz.service.GcsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<BannerDisplayDto> getBanners() {
        List<BannerEntity> allBanners = bannerRepository.findAll();
        List<BannerDisplayDto> dtoBannersList = new ArrayList<>();

        allBanners.forEach(bannerEntity -> dtoBannersList.add(mapEntityToDto(bannerEntity)));

        return dtoBannersList;
    }



    @Override
    public void delete(Long id) {
        Optional<BannerEntity> optionalBanner = bannerRepository.findById(id);

        if (optionalBanner.isPresent()) {
            bannerRepository.delete(optionalBanner.get());
        } else {
            throw new EntityNotFoundException("Banner with ID " + id + " not found");
        }
    }

    @Override
    public HttpStatus updateBanner(Long id, BannerUpdateDto bannerUpdateDto) {
        Optional<BannerEntity> optionalBanner = bannerRepository.findById(id);

        if (optionalBanner.isPresent()) {
            BannerEntity bannerEntity = getBannerEntity(bannerUpdateDto, optionalBanner);

            bannerRepository.save(bannerEntity);
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    private static BannerEntity getBannerEntity(BannerUpdateDto bannerUpdateDto, Optional<BannerEntity> optionalBanner) {


        BannerEntity bannerEntity = optionalBanner.get();

        // Update fields from the DTO
        if (bannerUpdateDto.getName() != null) {
            bannerEntity.setName(bannerUpdateDto.getName());
        }

        if (bannerUpdateDto.getTitle() != null){
            bannerEntity.setTitle(bannerUpdateDto.getTitle());
        }

        if (bannerUpdateDto.getText() != null) {
            bannerEntity.setText(bannerUpdateDto.getText());
        }
        return bannerEntity;
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

    private BannerDisplayDto mapEntityToDto(BannerEntity entity) {

        BannerDisplayDto dto = new BannerDisplayDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setTitle(entity.getTitle());
        dto.setText(entity.getText());
        dto.setImagePath(entity.getImagePath());

        return dto;
    }
}
