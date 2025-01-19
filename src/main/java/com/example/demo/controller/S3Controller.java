package com.example.demo.controller;

import com.example.demo.dto.CreateBucketResponseDTO;
import com.example.demo.service.S3Service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;

@RestController
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/create-bucket")
    public Mono<CreateBucketResponseDTO> createBucket(@RequestParam String bucketName) {
        return s3Service.createBucket(bucketName);
    }

    @GetMapping("/buckets")
    public Flux<String> getBuckets() {
        return s3Service.getBuckets();
    }
}
