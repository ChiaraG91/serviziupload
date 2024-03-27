package com.servizi.servizifileupload.controllers;

import com.servizi.servizifileupload.services.FileStorageService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        fileStorageService.upload(file);

        return fileStorageService.upload(file);
    }

    @GetMapping("/download/{id}")
    public @ResponseBody byte[] download(@RequestParam String fileName, HttpServletResponse response) throws IOException {
        System.out.println("Downloading" + fileName);
        String extension = FilenameUtils.getExtension(fileName);
        switch(extension){
            case "gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
        }
        response.setHeader("Content disposition","attachment; filename=\""+fileName+"\"");
        return fileStorageService.download(fileName);
    }
}
