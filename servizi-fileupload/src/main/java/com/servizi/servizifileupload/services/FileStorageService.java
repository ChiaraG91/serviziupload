package com.servizi.servizifileupload.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("{filerepositoryfolder}")
    private String filerepositoryfolder;

    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);
        String newFileName = UUID.randomUUID().toString();
        String completeFileName = newFileName + "." + extension;
        File finalFolder = new File(filerepositoryfolder);
        if (!finalFolder.exists() || !finalFolder.isDirectory()) {
            throw new IOException("La cartella finale non esiste o non è una directory valida");
        }
        File finalDestination = new File(filerepositoryfolder + File.separator + completeFileName);
        if (finalDestination.exists()) {
            throw new IOException("Esiste già un file con lo stesso nome nella cartella di destinazione");
        }
        file.transferTo(finalDestination);
        return completeFileName;
    }

    public byte[] download(String fileName) throws IOException{
        File fileFromRepository = new File(filerepositoryfolder + "\\" + fileName);
        if(!fileFromRepository.exists()) throw new IOException("File does not exist");
        return IOUtils.toByteArray(new FileInputStream(fileFromRepository));
    }
}
