package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class NoteController {

    private UserService userService;
    private NoteService noteService;
    private EncryptionService encryptionService;

    public NoteController(UserService userService, NoteService noteService, EncryptionService encryptionService) {
        this.userService = userService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/notes")
    public String addNote(Authentication authentication, Note note, Model model) {

        Integer userId = this.userService.getUser(authentication.getName()).getUserId();
        note.setUserId(userId);

        int numInsertedRows = this.noteService.addOrEditNote(note);

        if (numInsertedRows >= 0){
            model.addAttribute("noteSuccess","A new note was added successfully!");
        } else {
            model.addAttribute("noteError","Error while adding a note. Please, try again!");
        }

        model.addAttribute("activeTab", "notes");

        return "redirect:/home";
    }

    @GetMapping("/notes/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") int noteId, Model model){
        this.noteService.deleteNote(noteId);

        model.addAttribute("activeTab", "notes");

        return "redirect:/home";
    }
}
