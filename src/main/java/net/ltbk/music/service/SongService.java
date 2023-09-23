package net.ltbk.music.service;

import net.ltbk.music.bean.Song;
import com.github.pagehelper.Page;

import java.util.Date;

public interface SongService {
    //查询所有歌曲显示曲库
    Page<Song> selectAll();

    // 后台管理添加音乐
    int save(Song song);

    //后台听过歌曲id删除
    int deleteSongById(int songId);

    // 播放音乐
    Song selectSongById(int songId);

    //多条件查询用户
    Page<Song> selectSongByExample(Song song, Date startDate, Date endDate);

    // 播放量 播放一次+1
    int updatePlayCount(int songId);

    // 下载量 下载一次+1
    int updateDownloadCount(int songId);

    //  播放排行榜
    Page<Song> selectSongsByList();
}