package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    public Integer addOrEditCredential(Credential credential) {
        if (credential.getCredentialId() == null){
            return this.credentialMapper.addCredential(credential);
        }
        else{
            return this.credentialMapper.updateCredentialById(credential);
        }
    }

    public Integer deleteCredential(Integer credentialId){
        return credentialMapper.deleteCredential(credentialId);
    }

    public List<Credential> getCredentials(Integer userId){
        return credentialMapper.getAllCredentials(userId);
    }

    public Credential getCredentialById(Credential credential){ return credentialMapper.getCredentialById(credential); }

    public String getKeyByCredentialId(Credential credential){ return credentialMapper.getKeyByCredentialId(credential); }
}
