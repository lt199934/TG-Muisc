package net.ltbk.music.mapper;

import net.ltbk.music.bean.Song;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface SongMapper {
    int deleteByPrimaryKey(Integer songId);

    int insert(Song record);

    Song selectByPrimaryKey(Integer songId);

    Page<Song> selectAll();

    int updateByPrimaryKey(Song record);

    Song selectAllBySongId(int songId);

    Page<Song> selectSongByExample(@Param("song") Song song, @Param("start") Date startDate, @Param("end") Date endDate);

    // 播放量 播放一次+1
    int updatePlayCount(int songId);

    // 下载量 下载一次+1
    int updateDownloadCount(int songId);

    //  播放排行榜
    Page<Song> selectSongsByList();
}