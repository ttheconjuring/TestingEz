package com.testingez.testingez.web.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@RestController
public class CloudinaryController {

    private final Cloudinary cloudinary;

    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestPart("avatar") MultipartFile file) {
        try {
            Map uploadResult = this.cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", "TestingEz"));
            return new ResponseEntity<>(uploadResult.get("url"), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
