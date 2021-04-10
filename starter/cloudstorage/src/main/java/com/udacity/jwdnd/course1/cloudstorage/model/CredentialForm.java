package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {
    private String url;
    private String username;
    private String password;
    private Integer userId;
    private String key;

    public CredentialForm() {
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getCredentialKey() {
        return key;
    }

    public void setCredentialKey(String key) {
        this.key = key;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
