package com.scsa.moin_back.review.controller;

import com.scsa.moin_back.common.service.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewImageUploadController {

    private FileUploader fileUploader;

    @PostMapping("/image-upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            fileUploader.uploadFile(file);
            return ResponseEntity.ok(fileUploader.uploadFile(file));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("이미지 업로드 실패");
        }
    }

}
