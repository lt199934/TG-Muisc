package com.edu.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class Singer {
    private Integer singerId;

    private String singerName;

    private String sex;

    private String imgUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birthday;

    private String intrduction;
    //    一个歌手有多首歌曲 1对多
    private List<Song> songs;
    //    一一个歌手有多张专辑 1对多
    private List<Album> albums;

    private int count;

    public Integer getSingerId() {
        return singerId;
    }

    public void setSingerId(Integer singerId) {
        this.singerId = singerId;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIntrduction() {
        return intrduction;
    }

    public void setIntrduction(String intrduction) {
        this.intrduction = intrduction;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Singer{" +
                "singerId=" + singerId +
                ", singerName='" + singerName + '\'' +
                ", sex='" + sex + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", birthday=" + birthday +
                ", intrduction='" + intrduction + '\'' +
                ", songs=" + songs +
                ", albums=" + albums +
                ", count=" + count +
                '}';
    }
}