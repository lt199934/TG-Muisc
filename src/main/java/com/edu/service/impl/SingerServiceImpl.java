package com.edu.service.impl;

import com.edu.bean.Admin;
import com.edu.bean.Singer;
import com.edu.mapper.AdminMapper;
import com.edu.mapper.SingerMapper;
import com.edu.service.AdminService;
import com.edu.service.SingerService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SingerServiceImpl implements SingerService {
    @Autowired
    private SingerMapper singerMapper;

    //通过ID查询歌手
    public Singer selectAllBySingerId(int singerId) {
        return singerMapper.selectAllBySingerId(singerId);
    }
    //多条件查询歌手
    public Page<Singer> selectSingerByExample(Singer singer, Date startDate, Date endDate) {
        return singerMapper.selectSingerByExample(singer,startDate,endDate);
    }
    //添加歌手
    public int insertSinger(Singer singer) {

        return singerMapper.insertSinger(singer);
    }
    //通过歌手删除歌手
    public int delSinger(int SingerId) {
        return singerMapper.deleteByPrimaryKey(SingerId);
    }

    public List<Singer> AllSinger() {
        return singerMapper.AllSinger();
    }

}
