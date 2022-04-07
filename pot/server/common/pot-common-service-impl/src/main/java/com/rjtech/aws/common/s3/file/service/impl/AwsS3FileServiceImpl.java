package com.rjtech.aws.common.s3.file.service.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.rjtech.aws.common.s3.file.service.AswS3FileService;

@Service
public class AwsS3FileServiceImpl implements AswS3FileService {

    @Autowired
    private AmazonS3 awsS3Client;

    @Value("${aws.s3.bucket}")
    private String awsS3Bucket;

    @Override
    public String uploadFile(File fileObject, String uniqueKeyPrefix) {
        final String finalKey = uniqueKeyPrefix + "_" + fileObject.getName();
        awsS3Client.putObject(awsS3Bucket, finalKey, fileObject);
        return finalKey;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile, String uniqueKeyPrefix) throws IOException {
        final String finalKey = uniqueKeyPrefix + "_" + multipartFile.getOriginalFilename();
        awsS3Client.putObject(awsS3Bucket, finalKey, multipartFile.getInputStream(), getObjectMetadata(multipartFile));
        return finalKey;
    }

    @Override
    public void deleteFile(String key) {
        awsS3Client.deleteObject(awsS3Bucket, key);
    }

    @Override
    public byte[] downloadFile(String key) throws IOException {
        S3Object file = awsS3Client.getObject(awsS3Bucket, key);
        if (file != null)
            return IOUtils.toByteArray(file.getObjectContent());
        return new byte[0];
    }

    @Override
    public void updateExistingFile(MultipartFile multipartFile, String uniqueKey) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            // delete existing file
            deleteFile(uniqueKey);
            // upload new file with same key
            awsS3Client.putObject(awsS3Bucket, uniqueKey, multipartFile.getInputStream(),
                    getObjectMetadata(multipartFile));
        }

    }

    private ObjectMetadata getObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        return objectMetadata;
    }

}
