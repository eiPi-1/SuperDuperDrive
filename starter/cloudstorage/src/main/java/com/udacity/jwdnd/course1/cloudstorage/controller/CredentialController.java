package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Controller
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService,
                                EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/credentials")
    public String addCredential(Authentication authentication, Credential credential,
                                RedirectAttributes redirectAttributes) {
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();
        credential.setUserId(userId);

        String encodedKey = "";

        if (credential.getCredentialId() != null){
            encodedKey = this.credentialService.getKeyByCredentialId(credential);
        }
        else{
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            encodedKey = Base64.getEncoder().encodeToString(key);
        }

        String encryptedPassword = this.encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        if (this.credentialService.getCredentialByURLandUsername(credential.getUrl(), credential.getUserName()) != null){
            redirectAttributes.addFlashAttribute("credentialError",true);
            redirectAttributes.addFlashAttribute("credentialErrorMessage",
                    "Credential with this URL and Username already exists! Credential NOT created!");
            return "redirect:/home";
        }

        int numInsertedRows = this.credentialService.addOrEditCredential(credential);

        if (numInsertedRows >= 0){
            redirectAttributes.addFlashAttribute("credentialSuccess",true);
            redirectAttributes.addFlashAttribute("credentialSuccessMessage","A new credential was added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("credentialError",true);
            redirectAttributes.addFlashAttribute("credentialErrorMessage","Error while adding a credential. Please, try again!");
        }

        List<Credential> allExistingCredentials = this.credentialService.getCredentials(userId);

        redirectAttributes.addFlashAttribute("activeTab", "credentials");

        return "redirect:/home";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") int credentialId,
                                   RedirectAttributes redirectAttributes){
        int numDeletedRows = credentialService.deleteCredential(credentialId);

        if (numDeletedRows >= 0){
            redirectAttributes.addFlashAttribute("credentialSuccess",true);
            redirectAttributes.addFlashAttribute("credentialSuccessMessage","A credential was deleted!");
        } else {
            redirectAttributes.addFlashAttribute("credentialError",true);
            redirectAttributes.addFlashAttribute("credentialErrorMessage","Error while deleting a credential. Please, try again!");
        }

        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        //redirectAttributes.addFlashAttribute("encryptionService", encryptionService);

        return "redirect:/home";
    }
}
