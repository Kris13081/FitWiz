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
    public String uploadFile(String bucketName, MultipartFile file) throws IOException {

        // Generate a unique file name or use the original file name
        String fileName = generateUniqueFileName(file.getOriginalFilename());

        // Specify the bucket and file name
        BlobId blobId = BlobId.of(bucketName, fileName);

        // Upload the file to Google Cloud Storage
        storage.create(BlobInfo.newBuilder(blobId).build(), file.getBytes());


        return "gs://" + bucketName + "/" + fileName;
    }

    private String generateUniqueFileName(String originalFileName) {
        return System.currentTimeMillis() + "_" + originalFileName;
    }

}
