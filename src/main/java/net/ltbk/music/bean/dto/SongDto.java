package net.ltbk.music.bean.dto;

import net.ltbk.music.bean.SongList;

public class SongDto extends SongList {
    private Integer songId;

    private String song;

    private String url;

    private Integer singerId;

    private String singerName;

    private String singerImg;

    private String songList;

    private Integer albumId;

    private String albumName;

    private String albumUrl;

    private Integer downloadCount;

    private Integer playCount;

    private String language;

    @Override
    public String getSongList() {
        return songList;
    }

    @Override
    public void setSongList(String songList) {
        this.songList = songList;
    }

    public Integer getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public String getSingerImg() {
        return singerImg;
    }

    public void setSingerImg(String singerImg) {
        this.singerImg = singerImg;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumUrl() {
        return albumUrl;
    }

    public void setAlbumUrl(String albumUrl) {
        this.albumUrl = albumUrl;
    }

    @Override
    public String toString() {
        return "SongDto{" + "songId=" + songId + ", song='" + song + '\'' + ", url='" + url + '\'' + ", singerId=" + singerId + ", singerName='" + singerName + '\'' + ", singerImg='" + singerImg + '\'' + ", songList='" + songList + '\'' + ", albumId=" + albumId + ", albumName='" + albumName + '\'' + ", albumUrl='" + albumUrl + '\'' + ", downloadCount=" + downloadCount + ", playCount=" + playCount + ", language='" + language + '\'' + '}';
    }
}
