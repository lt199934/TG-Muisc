package net.ltbk.music.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import net.ltbk.music.bean.Album;
import net.ltbk.music.bean.Singer;
import net.ltbk.music.bean.SongList;

import java.util.Date;
import java.util.List;

@ApiModel("歌曲")
public class SongVo {
    private Integer songId;

    private String song;

    private Integer singerId;

    private Integer playCount;

    private Integer downloadCount;

    private String url;

    private String language;

    private Integer albumId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;
    /**
     * 多首音乐被多个歌单收藏 多对多
     **/
    private List<SongList> songLists;

    private Album album;

    private Singer singer;

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public Integer getSingerId() {
        return singerId;
    }

    public void setSingerId(Integer singerId) {
        this.singerId = singerId;
    }

    public Integer getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<SongList> getSongLists() {
        return songLists;
    }

    public void setSongLists(List<SongList> songLists) {
        this.songLists = songLists;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    @Override
    public String toString() {
        return "SongVo{" +
                "songId=" + songId +
                ", song='" + song + '\'' +
                ", singerId=" + singerId +
                ", playCount=" + playCount +
                ", downloadCount=" + downloadCount +
                ", url='" + url + '\'' +
                ", language='" + language + '\'' +
                ", albumId=" + albumId +
                ", time=" + time +
                ", songLists=" + songLists +
                ", album=" + album +
                ", singer=" + singer +
                '}';
    }
}