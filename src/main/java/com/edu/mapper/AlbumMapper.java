package com.edu.mapper;

import com.edu.bean.Album;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AlbumMapper {
    int deleteByPrimaryKey(Integer albumId);

    int insert(Album record);

    Album selectByPrimaryKey(Integer albumId);

    Page<Album> selectAll();

    int updateByPrimaryKey(Album record);

    Album selectAllByAlbumId(int albumId);

    Page<Album> selectAlbumByExample(@Param("album") Album album, @Param("start") Date startDate, @Param("end") Date endDate);

    int count(int albumId);

    List<Album> allAlbum();

    Page<Album> singerIdAlbum(@Param("SingerId")int SingerId);
}