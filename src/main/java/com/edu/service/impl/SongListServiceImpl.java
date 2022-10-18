package com.edu.service.impl;

import com.edu.bean.FenLei;
import com.edu.bean.SongList;
import com.edu.bean.SongListDto;
import com.edu.mapper.SongListMapper;
import com.edu.service.SongListService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SongListServiceImpl implements SongListService {
    @Autowired
    private SongListMapper songlistMapper;

    public Page<SongList> selectSongListByExample(SongList songlist, Date startDate, Date endDate) {
        return songlistMapper.selectSongListByExample(songlist, startDate, endDate);
    }

    public SongList selectAllBySongListId(int songListId) {
        return songlistMapper.selectAllBySongListId(songListId);
    }

    public Page<SongList> selectAll() {
        return songlistMapper.selectAll();
    }

    public int count(Integer songListId) {
        return songlistMapper.count(songListId);
    }

    public List<FenLei> fen() {
        return songlistMapper.fen();
    }

    public List<FenLei> selectFenArrayBySongListId(int songListId) {
        return songlistMapper.selectFenArrayBySongListId(songListId);
    }

    public Page<SongList> selectFenAll(int fenId) {
        return songlistMapper.selectFenAll(fenId);
    }

    public int delSongList(int songListId) {
        return songlistMapper.deleteByPrimaryKey(songListId);
    }

    public int insertSongList(SongListDto songList) {
        return songlistMapper.insertSongList(songList);
    }
}
