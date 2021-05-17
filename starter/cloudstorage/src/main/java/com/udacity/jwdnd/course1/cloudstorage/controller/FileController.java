package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class FileController {

    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication,
                             RedirectAttributes redirectAttributes) {

        if(fileUpload.isEmpty()) {
            redirectAttributes.addFlashAttribute("fileError",true);
            redirectAttributes.addFlashAttribute("fileErrorMessage","No file is chosen for an upload!");
            return "redirect:/home";
        }
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();

        try {
            File file = this.fileService.createFile(fileUpload, userId);
            if (this.fileService.getFileByName(file) != null){
                redirectAttributes.addFlashAttribute("fileError",true);
                redirectAttributes.addFlashAttribute("fileErrorMessage",
                        "File with this name already exists! File NOT uploaded!");
            }
            else {
                this.fileService.addOrEditFile(file);
                redirectAttributes.addFlashAttribute("fileSuccess", true);
                redirectAttributes.addFlashAttribute("fileSuccessMessage", "File uploaded successfully!");
            }
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("fileError",true);
            redirectAttributes.addFlashAttribute("fileErrorMessage",
                    "Error: " + e.getMessage());
        }

        redirectAttributes.addFlashAttribute("activeTab", "files");

        return "redirect:/home";
    }

    @GetMapping("/files/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") int fileId, RedirectAttributes redirectAttributes){
        this.fileService.deleteFile(fileId);

        redirectAttributes.addFlashAttribute("activeTab", "files");

        return "redirect:/home";
    }

    @GetMapping("/files/view/{fileId}")
    public ResponseEntity<InputStreamSource> getFile(@PathVariable Integer fileId)  {
        File file = this.fileService.getFileById(fileId);

        ByteArrayResource resource = new ByteArrayResource(file.getFileData());
        MediaType mt = MediaType.parseMediaType(file.getContentType());

        return ResponseEntity.ok().contentType(mt).header(HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"" + file.getFileName() + "\"").body(resource);
    }
}
