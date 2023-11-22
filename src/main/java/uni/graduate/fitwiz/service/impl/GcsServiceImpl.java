package uni.graduate.fitwiz.service.impl;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uni.graduate.fitwiz.service.GcsService;

import java.io.IOException;

@Service
public class GcsServiceImpl implements GcsService {

    private final Storage storage;

    public GcsServiceImpl(Storage storage) {
        this.storage = storage;
    }


    @Override
    public String uploadProfileImages(String bucketName, MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return "https://storage.googleapis.com/fitwiz_images_bucket/user-images/defaultProfile.jpg";
        }

        String fileName = generateUniqueFileName(file.getOriginalFilename());
        String directoryPath = "user-images/";

        return save(bucketName, file, directoryPath, fileName);
    }

    @Override
    public String uploadProductImage(String bucketName, MultipartFile file) throws IOException {
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        String directoryPath = "website-images/";

        return save(bucketName, file, directoryPath, fileName);
    }

    private String generateUniqueFileName(String originalFileName) {
        return System.currentTimeMillis() + "_" + originalFileName;
    }
    private String save(String bucketName, MultipartFile file, String directoryPath, String fileName) throws IOException {
        BlobId blobId = BlobId.of(bucketName, directoryPath + fileName);
        storage.create(BlobInfo.newBuilder(blobId).build(), file.getBytes());

        return getPath(bucketName, directoryPath, fileName);
    }

    private static String getPath(String bucketName, String directoryPath, String fileName) {
        return "https://storage.googleapis.com/" + bucketName + "/" + directoryPath + fileName;
    }

}
