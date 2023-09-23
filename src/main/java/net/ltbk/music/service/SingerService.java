package net.ltbk.music.service;

import net.ltbk.music.bean.Singer;
import com.github.pagehelper.Page;

import java.util.Date;
import java.util.List;

public interface SingerService {
    //根据ID查询歌手信息
    Singer selectAllBySingerId(Integer singerId);

    //多条件查询歌手
    Page<Singer> page(Singer singer, Date startDate, Date endDate);

    //添加歌手
    int save(Singer singer);

    //删除歌手
    int delSinger(int SingerId);

    //无条件查询歌手（不分页）
    List<Singer> list();
}