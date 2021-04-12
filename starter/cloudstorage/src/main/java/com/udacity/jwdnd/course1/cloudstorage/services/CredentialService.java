package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating CredentialService bean");
    }

    public Integer addCredential(CredentialForm credentialForm) {
        Credential credential = new Credential();

        credential.setUrl(credentialForm.getUrl());
        credential.setUserName(credentialForm.getUserName());
        credential.setPassword(credentialForm.getPassword());
        credential.setUserId(credentialForm.getUserId());
        credential.setKey(credentialForm.getKey());

        Integer result = this.credentialMapper.addCredential(credential);
        return result;
    }

    public Integer deleteCredential(Integer credentialId){
        return credentialMapper.deleteCredential(credentialId);
    }

    public List<Credential> getCredentials(Integer userId){
        return credentialMapper.getAllCredentials(userId);
    }
}
