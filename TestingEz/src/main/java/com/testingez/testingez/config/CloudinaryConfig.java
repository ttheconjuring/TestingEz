package com.testingez.testingez.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "ditl40ows",
                "api_key", "854936962994645",
                "api_secret", "VWfMuwvXpXZn2Obo-8RGBK1MhEE"
        ));
    }

}
