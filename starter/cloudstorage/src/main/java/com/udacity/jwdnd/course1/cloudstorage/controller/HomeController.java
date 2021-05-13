package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final NoteService noteService;
    private final UserService userService;
    private EncryptionService encryptionService;
    private final CredentialService credentialService;
    private final FileService fileService;

    public HomeController(UserService userService, CredentialService credentialService, NoteService noteService,
                          EncryptionService encryptionService, FileService fileService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @GetMapping()
    public String loginView(Authentication authentication, Model model) {
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("credentials", credentialService.getCredentials(userId));
        model.addAttribute("notes", noteService.getNotes(userId));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("files", fileService.getFiles(userId));

        return "home";
    }
}
