package com.lucasoliveira.cursomc.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.lucasoliveira.cursomc.services.exception.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Lucas Oliveira - 27-06/2019
 * Serviço que realiza upload de imagem no Amazon s3 */

@Service
public class S3Service {

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    public URI uploadFile(MultipartFile multipartFile) {

        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uploadFile(inputStream, fileName, contentType);
        } catch (IOException e) {
            throw new FileException("Erro de IO");
        }
    }

    public URI uploadFile(InputStream inputStream, String fileName, String contentType){
        try {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
            LOG.info("Iniciando upload");
            s3client.putObject(bucketName, fileName, inputStream, metadata);
            LOG.info("Upload finalizado");

            return s3client.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException e) {
            throw new FileException("Erro na conversão de URL para URI");
        }
    }
}
