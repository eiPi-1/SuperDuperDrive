package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getAllNotes(Integer userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, " +
            "#{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    int deleteNote(Integer noteId);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription}")
    int updateNote(Note note);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} " +
            "WHERE noteid = #{noteId}")
    int updateNoteById(Note note);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Note getNoteById(Note note);

    @Select("SELECT key FROM NOTES WHERE noteid = #{noteId}")
    String getKeyByNoteId(Note note);
}
