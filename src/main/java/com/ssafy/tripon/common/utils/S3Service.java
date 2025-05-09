package com.ssafy.tripon.common.utils;

import java.io.IOException;
import java.util.UUID;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class S3Service implements FileStorageService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    @Override
    public String upload(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String modifiedName = UUID.randomUUID() + extension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            PutObjectRequest request = new PutObjectRequest(bucketName, modifiedName, file.getInputStream(), metadata);
            PutObjectResult result = amazonS3.putObject(request.withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            // @Todo: 커스텀 예외 처리
        }

        return amazonS3.getUrl(bucketName, modifiedName).toString();
    }

    @Override
    public void delete(String url) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        amazonS3.deleteObject(bucketName, fileName);
    }
}
