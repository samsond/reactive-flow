package com.example.demo.service;

import com.example.demo.dto.CreateBucketResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;

public class S3ServiceTest {

    private S3AsyncClient s3AsyncClient;
    private S3Service s3Service;

    @BeforeEach
    public void setUp() {
        s3AsyncClient = Mockito.mock(S3AsyncClient.class);
        s3Service = new S3Service(s3AsyncClient);
    }

    @Test
    public void testCreateBucket() {
        // Mock a CreateBucketResponse with the location field set
        CreateBucketResponse createBucketResponse = CreateBucketResponse.builder()
                .location("/test-bucket")  // Set the location to match your expected value
                .build();

        Mockito.when(s3AsyncClient.createBucket(any(CreateBucketRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(createBucketResponse));

        Mono<CreateBucketResponseDTO> result = s3Service.createBucket("test-bucket");

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getLocation().equals("/test-bucket"))  // Check location
                .verifyComplete();
    }

    @Test
    public void testGetBuckets() {
        Bucket bucket1 = Bucket.builder().name("bucket1").build();
        Bucket bucket2 = Bucket.builder().name("bucket2").build();
        ListBucketsResponse listBucketsResponse = ListBucketsResponse.builder().buckets(Arrays.asList(bucket1, bucket2)).build();
        Mockito.when(s3AsyncClient.listBuckets()).thenReturn(CompletableFuture.completedFuture(listBucketsResponse));

        Flux<String> result = s3Service.getBuckets();

        StepVerifier.create(result)
                .expectNext("bucket1")
                .expectNext("bucket2")
                .verifyComplete();
    }
}
