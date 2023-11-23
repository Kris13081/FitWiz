package uni.graduate.fitwiz.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GcsService {

    String  uploadProfileImages(String bucketName, MultipartFile file) throws IOException;
    String uploadProductImage(String bucketName, MultipartFile file) throws IOException;
    String uploadBannerImage(String bucketName, MultipartFile file) throws IOException;

}
