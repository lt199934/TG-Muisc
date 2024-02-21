package net.ltbk.music.service.impl;

import com.github.pagehelper.Page;
import net.ltbk.music.bean.Album;
import net.ltbk.music.mapper.AlbumMapper;
import net.ltbk.music.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumMapper albumMapper;

    // 查询所有专辑
    public Page<Album> findAll() {
        return albumMapper.selectAll();
    }

    //   点击专辑通过id查询并显示专辑的所有信息
    public Album findById(int albumId) {
        return albumMapper.selectAllByAlbumId(albumId);
    }

    //  通过专辑id删除专辑
    public int delAlbumById(int albumId) {
        return albumMapper.deleteByPrimaryKey(albumId);
    }

    // 多条件查询用户
    public Page<Album> selectAlbumByExample(Album album, Date startDate, Date endDate) {
        return albumMapper.selectAlbumByExample(album, startDate, endDate);
    }

    public int count(int albumId) {
        return albumMapper.count(albumId);
    }

    //查询所有专辑
    public List<Album> allAlbum() {
        return albumMapper.findAll();
    }

    //通过歌手Id查询专辑
    public Page<Album> findBySingerId(int singerId) {
        return albumMapper.singerIdAlbum(singerId);
    }

    public int add(Album album) {
        return albumMapper.insert(album);
    }

    @Override
    public int save(Album album) {
        if (album.getAlbumId() == null) {
            return albumMapper.insert(album);
        } else {
            return albumMapper.updateByPrimaryKey(album);
        }
    }


}

