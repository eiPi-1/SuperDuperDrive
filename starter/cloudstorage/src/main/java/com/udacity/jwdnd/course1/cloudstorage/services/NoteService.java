package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating NoteService bean");
    }

    public Integer addOrEditNote(Note note) {
        if (note.getNoteId() == null){
            return this.noteMapper.addNote(note);
        }
        else{
            return this.noteMapper.updateNoteById(note);
        }
    }

    public Integer deleteNote(Integer noteId){
        return this.noteMapper.deleteNote(noteId);
    }

    public List<Note> getNotes(Integer userId) {
        return noteMapper.getAllNotes(userId);
    }

    public Note getNoteById(Note note){ return this.noteMapper.getNoteById(note); }

    public Note getNoteByTitle(String title){ return this.noteMapper.getNoteByTitle(title); }

}
