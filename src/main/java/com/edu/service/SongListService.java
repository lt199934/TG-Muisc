package com.edu.service;

import com.edu.bean.FenLei;
import com.edu.bean.SongList;
import com.edu.bean.SongDto;
import com.edu.bean.SongListDto;
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
