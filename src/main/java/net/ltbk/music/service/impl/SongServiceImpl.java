package net.ltbk.music.service.impl;

import com.github.pagehelper.Page;
import net.ltbk.music.bean.Song;
import net.ltbk.music.bean.vo.SongVo;
import net.ltbk.music.mapper.SongMapper;
import net.ltbk.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongMapper songMapper;

    //查询所有歌曲显示曲库
    @Override
    public Page<SongVo> selectAll() {
        return songMapper.selectAll();
    }

    //后台上传歌曲
    @Override
    public int save(Song song) {
        if (song.getSongId() == null) {
            return songMapper.insert(song);
        } else {
            return songMapper.updateByPrimaryKey(song);
        }
    }

    //后台删除歌曲
    @Override
    public int deleteSongById(int songid) {
        return songMapper.deleteByPrimaryKey(songid);
    }

    // 播放音乐
    @Override
    public SongVo selectSongById(int songId) {
        return songMapper.selectAllBySongId(songId);
    }

    //
    @Override
    public Page<SongVo> selectSongByExample(Song song, Date startDate, Date endDate) {
        return songMapper.selectSongByExample(song, startDate, endDate);
    }

    // 播放量 播放一次+1
    @Override
    public int updatePlayCount(int songId) {
        return songMapper.updatePlayCount(songId);
    }

    // 下载量 下载一次+1
    @Override
    public int updateDownloadCount(int songId) {
        return songMapper.updateDownloadCount(songId);
    }

    // 播放排行榜
    @Override
    public Page<Song> selectSongsByList() {
        return songMapper.selectSongsByList();
    }
}
