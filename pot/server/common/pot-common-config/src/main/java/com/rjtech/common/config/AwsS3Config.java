package com.rjtech.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsS3Config {

	@Value("${aws.s3.accessKey}")
	private String awsS3AccessKey;

	@Value("${aws.s3.secretKey}")
	private String awsS3SecretKey;

	@Value("${aws.s3.region}")
	private String awsS3Region;

	@Value("${aws.s3.bucket}")
	private String awsS3Bucket;

	/**
	 * Amazon S3 client for S3 file upload/download and delete
	 * 
	 * @return {@link AmazonS3} object.
	 */
	@Bean
	public AmazonS3 s3client() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsS3AccessKey, awsS3SecretKey);
		return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(awsS3Region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	}

}
