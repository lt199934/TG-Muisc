package net.ltbk.music.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class SongList {
    private Integer songListId;

    private String songList;

    private String introduction;

    private String imgUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    private Integer userId;

    private String createUser;
    //一个歌单包含多个歌曲 多对多
    private List<Song> songs;

    private List<Album> albums;

    private List<Singer> singers;

    private Integer count;
    //    歌单分类
    private List<FenLei> fenLeis;

    private List<SongDto> songDto;

    public Integer getSongListId() {
        return songListId;
    }

    public void setSongListId(Integer songListId) {
        this.songListId = songListId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public List<Singer> getSingers() {
        return singers;
    }

    public void setSingers(List<Singer> singers) {
        this.singers = singers;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<FenLei> getFenLeis() {
        return fenLeis;
    }

    public void setFenLeis(List<FenLei> fenLeis) {
        this.fenLeis = fenLeis;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public List<SongDto> getSongDto() {
        return songDto;
    }

    public void setSongDto(List<SongDto> songDto) {
        this.songDto = songDto;
    }

    @Override
    public String toString() {
        return "SongList{" + "songListId=" + songListId + ", songList='" + songList + '\'' + ", introduction='" + introduction + '\'' + ", imgUrl='" + imgUrl + '\'' + ", time=" + time + ", userId=" + userId + ", createUser='" + createUser + '\'' + ", songs=" + songs + ", albums=" + albums + ", singers=" + singers + ", count=" + count + ", fenLeis=" + fenLeis + ", songDto=" + songDto + '}';
    }
}