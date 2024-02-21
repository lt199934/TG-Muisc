package net.ltbk.music.mapper;

import com.github.pagehelper.Page;
import net.ltbk.music.bean.FenLei;
import net.ltbk.music.bean.SongList;
import net.ltbk.music.bean.dto.SongListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
@Mapper
public interface SongListMapper {
    int deleteByPrimaryKey(Integer songListId);

    SongList selectByPrimaryKey(Integer songListId);

    Page<SongList> selectAll();

    int updateByPrimaryKey(SongListDto record);

    Page<SongList> selectSongListByExample(@Param("songList") SongList songlist, @Param("start") Date startDate, @Param("end") Date endDate);

    SongList selectAllBySongListId(int songListId);

    //    歌单里有歌曲
    int count(Integer songListId);

    List<FenLei> fen();

    Page<FenLei> selectFenArrayBySongListId(int songListId);

    Page<SongList> selectFenAll(int fenId);

    int insertSongList(SongListDto songList);
}