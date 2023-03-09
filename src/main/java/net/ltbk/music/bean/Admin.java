package net.ltbk.music.bean;

public class Admin {
    private Integer superId;

    private String userName;

    private String account;

    private String pwd;

    private String type;

    public Integer getSuperId() {
        return superId;
    }

    public void setSuperId(Integer superId) {
        this.superId = superId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Admin{" + "superId=" + superId + ", userName='" + userName + '\'' + ", account='" + account + '\'' + ", pwd='" + pwd + '\'' + ", type='" + type + '\'' + '}';
    }
}