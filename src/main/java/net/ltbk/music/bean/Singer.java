package net.ltbk.music.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("歌手")
@TableName("singer")
public class Singer implements Serializable {
    private Integer singerId;

    private String singerName;

    private String sex;

    private String imgUrl;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    private String introduction;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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
        return "Singer{" + "singerId=" + singerId + ", singerName='" + singerName + '\'' + ", sex='" + sex + '\'' + ", imgUrl='" + imgUrl + '\'' + ", birthday=" + birthday + ", intrduction='" + introduction + '\'' + ", songs=" + songs + ", albums=" + albums + ", count=" + count + '}';
    }
}