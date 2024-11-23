package net.ltbk.music.service.impl;

import com.github.pagehelper.Page;
import net.ltbk.music.bean.FenLei;
import net.ltbk.music.bean.SongList;
import net.ltbk.music.bean.dto.SongListDto;
import net.ltbk.music.mapper.SongListMapper;
import net.ltbk.music.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SongListServiceImpl implements SongListService {
    @Autowired
    private SongListMapper songlistMapper;

    @Override
    public Page<SongList> selectSongListByExample(SongList songlist, Date startDate, Date endDate) {
        return songlistMapper.selectSongListByExample(songlist, startDate, endDate);
    }

    @Override
    public SongList selectAllBySongListId(int songListId) {
        return songlistMapper.selectAllBySongListId(songListId);
    }

    @Override
    public Page<SongList> selectAll() {
        return songlistMapper.selectAll();
    }

    @Override
    public int count(Integer songListId) {
        return songlistMapper.count(songListId);
    }

    @Override
    public List<FenLei> fen() {
        return songlistMapper.fen();
    }

    @Override
    public Page<FenLei> selectFenArrayBySongListId(int songListId) {
        return songlistMapper.selectFenArrayBySongListId(songListId);
    }

    @Override
    public Page<SongList> selectFenAll(int fenId) {
        return songlistMapper.selectFenAll(fenId);
    }

    @Override
    public int delSongList(int songListId) {
        return songlistMapper.deleteByPrimaryKey(songListId);
    }

    @Override
    public int save(SongListDto songList) {
        if (songList.getSongListId() == null) {
            return songlistMapper.insertSongList(songList);
        } else {
            return songlistMapper.updateByPrimaryKey(songList);

        }

    }
}
