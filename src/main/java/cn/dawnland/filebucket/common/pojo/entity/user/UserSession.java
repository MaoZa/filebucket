package cn.dawnland.filebucket.common.pojo.entity.user;

public class UserSession {
    private String userName;
    private String eMail;
    private String tencentNumber;
    private String loginIp;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getTencentNumber() {
        return tencentNumber;
    }

    public void setTencentNumber(String tencentNumber) {
        this.tencentNumber = tencentNumber;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
}
