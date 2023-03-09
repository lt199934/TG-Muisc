package net.ltbk.music.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SongListDto {

    private String songList;

    private String introduction;

    private String imgUrl;

    @JsonFormat(locale = "yyyy-MM-dd")
    private Date time;

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSongList() {
        return songList;
    }

    public void setSongList(String songList) {
        this.songList = songList;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SongListDto{" + "songList='" + songList + '\'' + ", introduction='" + introduction + '\'' + ", imgUrl='" + imgUrl + '\'' + ", time=" + time + ", userId=" + userId + '}';
    }
}
