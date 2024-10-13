package br.com.riume.backendeureka.service;

import br.com.riume.backendeureka.config.AWSClientConfig;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class DocumentService {

    private final AmazonS3 amazonS3;
    private final AWSClientConfig awsClientConfig;

    public String upload(MultipartFile file) {
        File localFile = convertMultipartFileToFile(file);
        String bucketName = awsClientConfig.getBucketName();
        String fileName = file.getOriginalFilename();
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, localFile);
        PutObjectResult result = amazonS3.putObject(request);

        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            Files.copy(file.getInputStream(), convertedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return convertedFile;
    }

}

