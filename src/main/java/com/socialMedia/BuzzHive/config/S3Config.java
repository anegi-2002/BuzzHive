package com.socialMedia.BuzzHive.config;

import com.amazonaws.auth.AWS3Signer;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    @Value("${cloud.aws.credential.access-key}")
    private String awsAccessKey;

    @Value("${cloud.aws.credential.secret-key}")
    private String awsSecretKey;

    @Value("${cloud.aws.region.static}")
    private String regrion;

    @Bean
    public AmazonS3 client(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey,awsSecretKey);
       return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(regrion)
                .build();
    }
}
