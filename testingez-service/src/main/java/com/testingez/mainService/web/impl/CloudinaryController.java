package com.testingez.mainService.web.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.testingez.mainService.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
public class CloudinaryController {

    private final Cloudinary cloudinary;
    private final UserService userService;

    @PostMapping("/upload-avatar")
    public ResponseEntity<String> uploadAvatar(@RequestPart("avatar") MultipartFile file) {
        try {
            String url = (String) this.cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", "TestingEz")).get("url");
            this.userService.changeAvatar(url);
            return new ResponseEntity<>(url, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("The upload failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
