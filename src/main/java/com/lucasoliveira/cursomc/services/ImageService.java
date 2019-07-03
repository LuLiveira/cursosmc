package com.lucasoliveira.cursomc.services;

import com.lucasoliveira.cursomc.services.exception.FileException;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class ImageService {

    public BufferedImage getJpgImagemFromFile(MultipartFile multipartFile){
        /**
         * Utilizando a dependencia "commons io" para extrair com facilidade a extensão do arquivo
         */
        String extensaoDoArquivo = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        if(!"png".equals(extensaoDoArquivo) && !"jpg".equals(extensaoDoArquivo)){
            throw new FileException("Formato do arquivo não permitido");
        }

        try {
            BufferedImage img = ImageIO.read(multipartFile.getInputStream());
            if("png".equals(extensaoDoArquivo)){
                img = pngToJpg(img);
            }
            return img;
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
    }

    /**
     * Método para converter a imagem para JPG quando for PNG
     * @param img
     * @return
     */
    public BufferedImage pngToJpg(BufferedImage img) {

        BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
        return jpgImage;
    }

    /**
     * Método para transformar o BufferedImage em InputStream pois o método uploadFile em S3Service só aceita InputStream
     * @param img
     * @param extensao
     * @return
     */
    public InputStream getInputStream (BufferedImage img, String extensao){
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(img, extensao, outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        }catch (IOException e){
            throw  new FileException("Erro ao ler arquivo");
        }
    }

    public BufferedImage cropSquare(BufferedImage sourceImg){
        int min = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight() : sourceImg.getWidth();
        return Scalr.crop(
                sourceImg,
                (sourceImg.getWidth()/2) - (min/2),
                (sourceImg.getHeight()/2) - (min/2),
                min,
                min);
    }

    public BufferedImage resize (BufferedImage sourceImage, int size){
        return Scalr.resize(sourceImage, Scalr.Method.ULTRA_QUALITY, size);
    }
}
