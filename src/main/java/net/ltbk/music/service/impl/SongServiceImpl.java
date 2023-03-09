package net.ltbk.music.service.impl;

import net.ltbk.music.bean.Song;
import net.ltbk.music.mapper.SongMapper;
import net.ltbk.music.service.SongService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongMapper songMapper;

    //查询所有歌曲显示曲库
    public Page<Song> selectAll() {
        return songMapper.selectAll();
    }

    //后台上传歌曲
    public int uploadSong(Song song) {
        return songMapper.insert(song);
    }

    //后台删除歌曲
    public int deleteSongById(int songid) {
        return songMapper.deleteByPrimaryKey(songid);
    }

    // 播放音乐
    public Song selectSongById(int songId) {
        return songMapper.selectAllBySongId(songId);
    }

    //
    public Page<Song> selectSongByExample(Song song, Date startDate, Date endDate) {
        return songMapper.selectSongByExample(song, startDate, endDate);
    }

    // 播放量 播放一次+1
    public int updatePlayCount(int songId) {
        return songMapper.updatePlayCount(songId);
    }

    // 下载量 下载一次+1
    public int updateDownloadCount(int songId) {
        return songMapper.updateDownloadCount(songId);
    }

    // 播放排行榜
    public Page<Song> selectSongsByList() {
        return songMapper.selectSongsByList();
    }
}
