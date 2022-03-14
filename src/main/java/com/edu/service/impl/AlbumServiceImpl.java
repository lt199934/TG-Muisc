package com.edu.service.impl;

import com.edu.bean.Album;
import com.edu.mapper.AlbumMapper;
import com.edu.service.AlbumService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumMapper albumMapper;
    // 查询所有专辑
    public Page<Album> selectAll() {
        return albumMapper.selectAll();
    }
    //   点击专辑通过id查询并显示专辑的所有信息
    public Album selectAllByAlbumId(int albumId) {
        return albumMapper.selectAllByAlbumId(albumId);
    }
    //  通过专辑id删除专辑
    public int delAlbumById(int albumId) {
        return albumMapper.deleteByPrimaryKey(albumId);
    }
// 多条件查询用户
    public Page<Album> selectAlbumByExample(Album album, Date startDate, Date endDate) {
        return albumMapper.selectAlbumByExample(album,startDate,endDate);
    }

    public int count(int albumId) {
        return albumMapper.count(albumId);
    }
    //查询所有专辑
    public List<Album> allAlbum() {
        return albumMapper.allAlbum();
    }
    //通过歌手Id查询专辑
    public Page<Album> singerIdAlbum(int SingerId) {
        return albumMapper.singerIdAlbum(SingerId);
    }

    public int insertAlbum(Album album) {
        return albumMapper.insert(album);
    }


}

