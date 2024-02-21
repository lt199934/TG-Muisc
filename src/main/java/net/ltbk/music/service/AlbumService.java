package net.ltbk.music.service;

import com.github.pagehelper.Page;
import net.ltbk.music.bean.Album;

import java.util.Date;

public interface AlbumService {
    //查询所有专辑（要分页）
    Page<Album> findAll();

    //
    Album findById(int albumId);

    //通过iD删除专辑
    int delAlbumById(int albumId);

    //多条件查询专辑
    Page<Album> selectAlbumByExample(Album album, Date startDate, Date endDate);

    //通过AlbumId查询专辑所有信息
    int count(int albumId);

    //通过歌手ID查询专辑
    Page<Album> findBySingerId(int SingerId);

    /**
     * 更新专辑
     **/
    int save(Album album);
}
