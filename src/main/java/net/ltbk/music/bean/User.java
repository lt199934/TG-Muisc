package net.ltbk.music.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@ApiModel("用户")
public class User implements Serializable {
    private Integer userId;

    private String nickName;

    private String account;

    private String pwd;

    private String phone;

    private String email;

    private String userName;

    private String sex;

    private String headImg;

    private String type;

    private String personalSignature;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp time;
    /**
     * 一个用户可收藏多个歌曲 1对多
     **/
    private List<Song> songs;
    /**
     * 一个用户可收藏多个歌手 1对多
     **/
    private List<Singer> singers;
    /**
     * 一个用户可收藏多个专辑 1对多
     **/
    private List<Album> albums;
    /**
     * 一个用户可创建多个歌单 1对多
     **/
    private List<SongList> createdList;
    /**
     * 一个用户可收藏多个歌单 1对多
     **/
    private List<SongList> colList;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPersonalSignature() {
        return personalSignature;
    }

    public void setPersonalSignature(String personalSignature) {
        this.personalSignature = personalSignature;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Singer> getSingers() {
        return singers;
    }

    public void setSingers(List<Singer> singers) {
        this.singers = singers;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<SongList> getCreatedList() {
        return createdList;
    }

    public void setCreatedList(List<SongList> createdList) {
        this.createdList = createdList;
    }

    public List<SongList> getColList() {
        return colList;
    }

    public void setColList(List<SongList> colList) {
        this.colList = colList;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", nickName='" + nickName + '\'' + ", account='" + account + '\'' + ", pwd='" + pwd + '\'' + ", phone='" + phone + '\'' + ", email='" + email + '\'' + ", userName='" + userName + '\'' + ", sex='" + sex + '\'' + ", headImg='" + headImg + '\'' + ", type='" + type + '\'' + ", personalSignature='" + personalSignature + '\'' + ", birthday=" + birthday + ", songs=" + songs + ", singers=" + singers + ", albums=" + albums + ", createdList=" + createdList + ", colList=" + colList + ", time=" + time + '}';
    }
}