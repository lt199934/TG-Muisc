package net.ltbk.music.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class Album {
    private Integer albumId;

    private String album;

    private Integer singerId;

    private String albumImg;

    private String introduction;

    private String company;
    //   多张专辑对应一个歌手 多对-
    private Singer singer;
    //    一张专辑对应多首音乐 1对多
    private List<Song> songs;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;
    //    用于封装歌曲数量
    private int count;


    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Integer getSingerId() {
        return singerId;
    }

    public void setSingerId(Integer singerId) {
        this.singerId = singerId;
    }

    public String getAlbumImg() {
        return albumImg;
    }

    public void setAlbumImg(String albumImg) {
        this.albumImg = albumImg;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Album{" + "albumId=" + albumId + ", album='" + album + '\'' + ", singerId=" + singerId + ", albumImg='" + albumImg + '\'' + ", introduction='" + introduction + '\'' + ", company='" + company + '\'' + ", singer=" + singer + ", songs=" + songs + ", time=" + time + ", count=" + count + '}';
    }

}