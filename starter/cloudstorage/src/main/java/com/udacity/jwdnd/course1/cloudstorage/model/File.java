package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    private Integer fileId;
    private Integer userId;
    private String fileName;
    private String contentType;
    private byte[] fileData;
    private long fileSize;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setFileSize(long fileSize){ this.fileSize = fileSize; }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) { this.fileData = fileData; }
}
