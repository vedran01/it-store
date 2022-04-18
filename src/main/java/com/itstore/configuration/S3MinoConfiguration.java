package com.itstore.configuration;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3MinoConfiguration {

    @Value("${minioS3.url}")
    private String url;
    @Value("${minioS3.username}")
    private String username;

    @Value("${minioS3.password}")
    private String password;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(username, password)
                .build();
    }

}
