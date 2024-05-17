package com.ten022.diary.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.ten022.diary.dto.file.UploadFileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageService {

    private final AmazonS3 s3Client;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Value("${application.user-id}")
    private String userId;

    public UploadFileDto uploadFile(MultipartFile multipartFile) throws IOException {

        String originalFilename = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        int pos = originalFilename.lastIndexOf(".");
        String fileType = originalFilename.substring(pos + 1);

        String storageFileName = uuid + "." + fileType;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        // 버킷 저장
        try (InputStream inputStream = multipartFile.getInputStream()) {
            s3Client.putObject(new PutObjectRequest(bucketName, storageFileName, inputStream, metadata));
        } catch (IOException e) {
            throw new IOException("버킷에 해당 파일이 저장되지 않았습니다.");
        }

        // 권한 설정
        try {
            AccessControlList acl = s3Client.getObjectAcl(bucketName, storageFileName);
            acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);

            s3Client.setObjectAcl(bucketName, storageFileName, acl);
        } catch (Exception e) {
            throw new IOException("파일 권한 설정이 제대로 실행되지 않았습니다.");
        }

        return new UploadFileDto(storageFileName, originalFilename);
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed...";
    }

    public URL getFilePath(String fileName) {
        return s3Client.getUrl(bucketName, fileName);
    }

    public byte[] downloadFile(String fileName) throws IOException {
        S3Object object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = object.getObjectContent();
        byte[] content = IOUtils.toByteArray(inputStream);
        return content;
    }
}
