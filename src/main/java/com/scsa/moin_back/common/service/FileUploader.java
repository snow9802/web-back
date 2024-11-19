package com.scsa.moin_back.common.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUploader {

    private final AmazonS3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    public String uploadFile(MultipartFile file) throws Exception {
        String fileName = "static/" + UUID.randomUUID() + file.getOriginalFilename();
        System.out.println(fileName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata));

        return s3Client.getUrl(bucket, fileName).toString();
    }

    // 기존 이미지 삭제
    public void deleteImage(String fileUrl) {
        String splitStr = ".com/";
        String fileName = fileUrl.substring(fileUrl.lastIndexOf(splitStr) + splitStr.length());

        s3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}
