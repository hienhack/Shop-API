package com.example.tutorial.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.util.FileHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final AmazonS3 s3Client;

    @Value("${aws.s3.endpoint}")
    private String endpoint;

    @Value("${aws.s3.bucket_name}")
    private String bucketName;

    public String upload(MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            File file = FileHelper.convertToFile(multipartFile);
            String fileName = FileHelper.generateNewFileName(multipartFile);
            fileUrl = endpoint + "/temp/" + fileName;
            s3Client.putObject(new PutObjectRequest(bucketName, "images/" + fileName, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong");
        }
        return fileUrl;
    }

    public void delete(String fileName) {
        try {
            s3Client.deleteObject(bucketName, "images/" + fileName);
        } catch (AmazonServiceException e) {
            throw new BusinessException(e.getErrorMessage());
        }
    }
}
