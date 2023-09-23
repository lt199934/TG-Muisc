package net.ltbk.music.mapper;

import com.github.pagehelper.Page;
import net.ltbk.music.bean.Singer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
@Mapper
public interface SingerMapper {
    // 添加歌手
    int add(Singer singer);

    //根据id删除
    int del(Integer singerId);

    // 前后端通过id修改歌手模板信息
    int update(@Param("singer") Singer singer);

    Singer selectByPrimaryKey(Integer singerId);

    List<Singer> list();

    Singer selectAllBySingerId(Integer singerId);

    Page<Singer> page(@Param("singer") Singer singer, @Param("start") Date startDate, @Param("end") Date endDate);
}