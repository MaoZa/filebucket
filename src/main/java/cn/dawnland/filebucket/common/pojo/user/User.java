package cn.dawnland.filebucket.common.pojo.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 对应user表
 */
public class User implements Serializable {

    private static final long serialVersionUID = -2732225113658543457L;
    private Long id;
    private String username;
    private String password;
    private String tencentNumber;
    private String phone;
    private String email;
    private Date createTime;
    private Date updateTime;
    private Date lastLogin;
    private Integer userStatus;
    private Integer deleted;
    private String emailCode; //注册及重置时入参使用

    /**
     * 必填信息空验证
     */
    public Boolean isNotNull(){
        if(username == null || password == null || email == null || emailCode == null){
            return true;
        }
        return false;
    }

    /**
     * 清空除了id的其他属性
     */
    public void clean(){
        this.username = null;
        this.password = null;
        this.tencentNumber = null;
        this.phone = null;
        this.email = null;
        this.createTime = null;
        this.updateTime = null;
        this.lastLogin = null;
        this.userStatus = null;
        this.deleted = null;
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTencentNumber() {
        return tencentNumber;
    }

    public void setTencentNumber(String tencentNumber) {
        this.tencentNumber = tencentNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
