# Demo Reactive Application with LocalStack

This repository contains a demo reactive application that interacts with AWS S3 using LocalStack. LocalStack is a fully functional local AWS cloud stack that allows you to develop and test your cloud applications offline.

## Getting Started

#### Start the Services

To start the services, run the following command:

```bash
docker-compose up --build
```

#### Create Bucket
```bash
aws --endpoint-url=http://localhost:4566 s3 mb s3://my-test-bucket
```

#### List Buckets
```bash
aws --endpoint-url=http://localhost:4566 s3 ls
```

#### List objects in a Bucket
```bash
aws --endpoint-url=http://localhost:4566 s3 ls s3://my-test-bucket
```

#### upload a file
```bash
echo "Hello, LocalStack!" > hello.txt
aws --endpoint-url=http://localhost:4566 s3 cp hello.txt s3://my-test-bucket/hello.txt
```

#### Run this to create a bucket using the application
```bash
docker exec demo-reactive curl -X POST "http://localhost:8082/create-bucket?bucketName=my-test-bucket-2"
```

#### Check the bucket using Local AWS CLI
```bash
aws --endpoint-url=http://localhost:4566 s3 ls
```

