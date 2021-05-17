package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
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
    public String addNote(Authentication authentication, Note note, RedirectAttributes redirectAttributes) {

        Integer userId = this.userService.getUser(authentication.getName()).getUserId();
        note.setUserId(userId);

        if (this.noteService.getNoteByTitle(note.getNoteTitle()) != null){
            redirectAttributes.addFlashAttribute("noteError",true);
            redirectAttributes.addFlashAttribute("noteErrorMessage",
                    "Note with this title already exists! Note NOT created!");
            return "redirect:/home";
        }

        int numInsertedRows = this.noteService.addOrEditNote(note);

        if (numInsertedRows >= 0){
            redirectAttributes.addFlashAttribute("noteSuccess",true);
            redirectAttributes.addFlashAttribute("noteSuccessMessage","A new note was added or edited successfully!");
        } else {
            redirectAttributes.addFlashAttribute("noteError",true);
            redirectAttributes.addFlashAttribute("noteErrorMessage","Error while adding a note. Please, try again!");
        }

        redirectAttributes.addFlashAttribute("activeTab", "notes");

        return "redirect:/home";
    }

    @GetMapping("/notes/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") int noteId, RedirectAttributes redirectAttributes){
        int numDeletedRows = this.noteService.deleteNote(noteId);

        if (numDeletedRows >= 0){
            redirectAttributes.addFlashAttribute("noteSuccess",true);
            redirectAttributes.addFlashAttribute("noteSuccessMessage","A note was deleted!");
        } else {
            redirectAttributes.addFlashAttribute("noteError",true);
            redirectAttributes.addFlashAttribute("noteErrorMessage","Error while deleting a note. Please, try again!");
        }

        redirectAttributes.addFlashAttribute("activeTab", "notes");

        return "redirect:/home";
    }
}
