package com.udacity.jwdnd.course1.cloudstorage.services;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating FileService bean");
    }

    public void addFile(File file) {
        fileMapper.addFile(file);
    }

    public Integer addOrEditFile(File file) {

        if (file.getFileId() == null){
            return this.fileMapper.addFile(file);
        }
        else{
            return this.fileMapper.updateFileById(file);
        }
    }

    public File createFile(MultipartFile multipartFile, Integer userId) throws IOException {
        File file = new File();

        file.setUserId(userId);
        file.setFileName(multipartFile.getOriginalFilename());
        file.setContentType(multipartFile.getContentType());
        file.setFileData(multipartFile.getBytes());
        file.setFileSize(multipartFile.getSize());

        return file;
    }

    public Integer deleteFile(Integer fileId){
        return this.fileMapper.deleteFile(fileId);
    }

    public List<File> getFiles(Integer userId) {
        return fileMapper.getAllFiles(userId);
    }

    public File getFileById(Integer fileId){ return this.fileMapper.getFileById(fileId); }

    public File getFileByName(File file){ return this.fileMapper.getFileByName(file); }
}
