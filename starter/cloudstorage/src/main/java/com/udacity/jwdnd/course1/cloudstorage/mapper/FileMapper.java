package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getAllFiles(Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, " +
            "#{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(Integer fileId);

    @Update("UPDATE FILES SET fileId = #{fileId}, filedata = #{fileData}")
    int updateFile(File file);

    @Update("UPDATE FILES SET fileId = #{fileId}, filedata = #{fileData} " +
            "WHERE fileId = #{fileId}")
    int updateFileById(File file);

    @Select("SELECT * FROM NOTES WHERE fileId = #{fileId}")
    File getFileById(File file);

    @Select("SELECT * FROM NOTES WHERE filename = #{fileName} AND userid = #{userId")
    File getFileByName(File file);
}
