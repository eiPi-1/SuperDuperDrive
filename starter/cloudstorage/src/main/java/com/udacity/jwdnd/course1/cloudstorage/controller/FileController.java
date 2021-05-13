package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileController {

    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) {

        if(fileUpload.isEmpty()) {
            model.addAttribute("success",false);
            model.addAttribute("message","No file is chosen for an upload!");
            return "result";
        }
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();

        try {
            File file = this.fileService.createFile(fileUpload, userId);
            this.fileService.addOrEditFile(file);
            model.addAttribute("success",true);
            model.addAttribute("message","File uploaded successfully!");
            return "result";
        } catch (Exception e){
            model.addAttribute("error", true);
            model.addAttribute("message", e.getMessage());
        }

        return "redirect:/home";
    }

    @GetMapping("/files/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") int fileId, Model model){
        this.fileService.deleteFile(fileId);

        model.addAttribute("activeTab", "files");

        return "redirect:/home";
    }
}
