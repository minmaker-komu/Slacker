package woowoong.slacker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import woowoong.slacker.dto.UploadResponse;
import woowoong.slacker.service.S3Service;

@RestController
public class FileUploadController {

    private final S3Service s3Service;

    @Autowired
    public FileUploadController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            String imageUrl = s3Service.uploadFile(file);
            return ResponseEntity.ok(new UploadResponse(imageUrl));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new UploadResponse(e.getMessage()));
        }
    }
}

