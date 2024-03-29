package com.edu.mapper;

import com.edu.bean.Singer;
import com.edu.bean.Song;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SingerMapper {
    //根据id删除
    int deleteByPrimaryKey(Integer singerId);

    Singer selectByPrimaryKey(Integer singerId);

    List<Singer> selectAll();
    //前后端通过id修改歌手模板信息
    int updateByExampleAnSingerId(Singer record);
    
    Singer selectAllBySingerId(int singerId);

    Page<Singer> selectSingerByExample(@Param("singer") Singer singer, @Param("start") Date startDate, @Param("end") Date endDate);

    int insertSinger(Singer singer);
    List<Singer> AllSinger();
}