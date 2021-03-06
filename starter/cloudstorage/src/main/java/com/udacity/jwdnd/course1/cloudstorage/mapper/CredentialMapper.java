package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getAllCredentials(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{userName}," +
            " #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int addCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    int deleteCredential(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{userName}, password = #{password}")
    int updateCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{userName}, password = #{password} " +
            "WHERE credentialid = #{credentialId}")
    int updateCredentialById(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredentialById(Credential credential);

    @Select("SELECT key FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    String getKeyByCredentialId(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE url = #{url} AND username = #{userName}")
    Credential getCredentialByURLandUsername(String url, String userName);
}
