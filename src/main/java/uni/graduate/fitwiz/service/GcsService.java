package uni.graduate.fitwiz.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GcsService {

    String  uploadFile(String bucketName, MultipartFile file) throws IOException;

}
