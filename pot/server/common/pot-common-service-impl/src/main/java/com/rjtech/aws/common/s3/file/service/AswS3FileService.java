package com.rjtech.aws.common.s3.file.service;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface AswS3FileService {

    /**
     * Uploads given file to AWS S3 bucket and returns unique key for the file.
     * 
     * @param fileObject      file to be uploaded.
     * @param uniqueKeyPrefix a unique key prefix for the file.
     * @return returns generated uniqueKey for the file
     */
    String uploadFile(File fileObject, String uniqueKeyPrefix);

    /**
     * Uploads given multipartFile to AWS S3 bucket and returns unique key for the
     * file.
     * 
     * @param multipartFile   multipartFile to be uploaded.
     * @param uniqueKeyPrefix a unique key prefix for the file.
     * @return returns generated uniqueKey for the file
     * @throws IOException
     */
    String uploadFile(MultipartFile multipartFile, String uniqueKeyPrefix) throws IOException;

    /**
     * Updates given existing file into AWS S3
     * 
     * @param multipartFile {@link MultipartFile} to be uploaded
     * @param uniqueKey     existing unique key
     * @throws IOException
     */
    void updateExistingFile(MultipartFile multipartFile, String uniqueKey) throws IOException;

    /**
     * Deletes given file in AWS S3 bucket.
     * 
     * @param fileKey key of the file to be deleted
     */
    void deleteFile(String fileKey);

    /**
     * Downloads the file by key from AWS S3 bucket.
     * 
     * @param key key of the file
     * @return file content if key found,<br>
     *         if key not found then returns an empty array
     * @throws IOException
     */
    byte[] downloadFile(String key) throws IOException;

}
