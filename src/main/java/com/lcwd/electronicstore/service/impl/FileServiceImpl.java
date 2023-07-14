package com.lcwd.electronicstore.service.impl;

import com.lcwd.electronicstore.exception.BadApiRequestException;
import com.lcwd.electronicstore.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private static Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uplodFile(MultipartFile file, String path) throws IOException {

        String originalFilename = file.getOriginalFilename();

        String filename = UUID.randomUUID().toString();
        String extension  = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension =filename+extension;
        String fullPathWithFileName = path+File.separator + fileNameWithExtension;

        logger.info("full image path {}",fullPathWithFileName);
        if(extension.equalsIgnoreCase(".jpg")|| extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpeg")){

            //file save
            logger.info("file extension is {}", extension);
            File folder= new File(path);
            if(!folder.exists()) {

                //create folder
                folder.mkdirs();
            }
                Files.copy(file.getInputStream(),Paths.get(fullPathWithFileName));
                return fileNameWithExtension;

        }else{
            throw new BadApiRequestException("File with this "+extension +" not allowed");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullpath = path +File.separator+name;
        InputStream inputStream= new FileInputStream(fullpath);
        return inputStream;
    }
}
