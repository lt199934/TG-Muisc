package net.ltbk.music.service;

import net.ltbk.music.bean.FenLei;
import net.ltbk.music.bean.SongList;
import net.ltbk.music.bean.SongListDto;
import com.github.pagehelper.Page;

import java.util.Date;
import java.util.List;

public interface SongListService {
    Page<SongList> selectSongListByExample(SongList songlist, Date startDate, Date endDate);

    SongList selectAllBySongListId(int songListId);

    Page<SongList> selectAll();

    int count(Integer songListId);

    List<FenLei> fen();

    List<FenLei> selectFenArrayBySongListId(int songListId);

    Page<SongList> selectFenAll(int fenId);

    int delSongList(int songListId);

    int insertSongList(SongListDto songList);
}
