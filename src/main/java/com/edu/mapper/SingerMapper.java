package com.edu.mapper;

import com.edu.bean.Singer;
import com.edu.bean.Song;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SingerMapper {

    int deleteByPrimaryKey(Integer singerId);

    int insert(Singer record);

    Singer selectByPrimaryKey(Integer singerId);

    List<Singer> selectAll();

    int updateByPrimaryKey(Singer record);
    Singer selectAllBySingerId(int singerId);

    Page<Singer> selectSingerByExample(@Param("singer") Singer singer, @Param("start") Date startDate, @Param("end") Date endDate);

    int insertSinger(Singer singer);
    List<Singer> AllSinger();
}