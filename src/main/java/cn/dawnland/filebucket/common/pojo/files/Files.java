package cn.dawnland.filebucket.common.pojo.files;

import java.util.Date;

public class Files {

    private  Long id;
    private  Long userId;
    private  String fileName;
    private  String bucketFileName;
    private  Long fileSize;
    private Date createTime;
    private  Date updataTime;
    private Integer deleted;

    public Files() {
    }

    public Files(Long userId, String fileName, String bucketFileName, Long fileSize) {
        this.userId = userId;
        this.fileName = fileName;
        this.bucketFileName = bucketFileName;
        this.fileSize = fileSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBucketFileName() {
        return bucketFileName;
    }

    public void setBucketFileName(String bucketFileName) {
        this.bucketFileName = bucketFileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdataTime() {
        return updataTime;
    }

    public void setUpdataTime(Date updataTime) {
        this.updataTime = updataTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
