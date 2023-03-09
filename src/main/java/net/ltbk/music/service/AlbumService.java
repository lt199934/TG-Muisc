package net.ltbk.music.service;

import net.ltbk.music.bean.Album;
import com.github.pagehelper.Page;

import java.util.Date;
import java.util.List;

public interface AlbumService {
    //查询所有专辑（要分页）
    Page<Album> selectAll();

    //
    Album selectAllByAlbumId(int albumId);

    //通过iD删除专辑
    int delAlbumById(int albumId);

    //多条件查询专辑
    Page<Album> selectAlbumByExample(Album album, Date startDate, Date endDate);

    //通过AlbumId查询专辑所有信息
    int count(int albumId);

    //无条件查询所有专辑
    List<Album> allAlbum();

    //通过歌手ID查询专辑
    Page<Album> singerIdAlbum(int SingerId);

    //添加专辑
    int insertAlbum(Album album);
}
