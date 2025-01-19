package com.example.demo.service;

import com.example.demo.dto.CreateBucketResponseDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.Bucket;


@Service
public class S3Service {

    private final S3AsyncClient s3AsyncClient;

    public S3Service(S3AsyncClient s3AsyncClient) {
        this.s3AsyncClient = s3AsyncClient;
    }

    public Mono<CreateBucketResponseDTO> createBucket(String bucketName) {
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        return Mono.fromFuture(() -> s3AsyncClient.createBucket(createBucketRequest))
                .map(response -> new CreateBucketResponseDTO(response.location()));  // Map to DTO
    }

    public Flux<String> getBuckets() {
        return Mono.fromFuture(s3AsyncClient.listBuckets())
                .flatMapMany(response -> Flux.fromIterable(response.buckets()))
                .map(Bucket::name);
    }
}
