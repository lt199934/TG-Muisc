package net.ltbk.music.mapper;

import net.ltbk.music.bean.FenLei;
import net.ltbk.music.bean.SongList;
import net.ltbk.music.bean.SongListDto;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SongListMapper {
    int deleteByPrimaryKey(Integer songListId);

    int insert(SongList record);

    SongList selectByPrimaryKey(Integer songListId);

    Page<SongList> selectAll();

    int updateByPrimaryKey(SongList record);

    Page<SongList> selectSongListByExample(@Param("songList") SongList songlist, @Param("start") Date startDate, @Param("end") Date endDate);

    SongList selectAllBySongListId(int songListId);

    //    歌单里有歌曲
    int count(Integer songListId);

    List<FenLei> fen();

    List<FenLei> selectFenArrayBySongListId(int songListId);

    Page<SongList> selectFenAll(int fenId);

    int insertSongList(SongListDto songList);
}