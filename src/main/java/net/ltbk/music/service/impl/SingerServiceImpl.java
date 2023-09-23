package net.ltbk.music.service.impl;

import net.ltbk.music.bean.Singer;
import net.ltbk.music.mapper.SingerMapper;
import net.ltbk.music.service.SingerService;
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
    public Singer selectAllBySingerId(Integer singerId) {
        return singerMapper.selectAllBySingerId(singerId);
    }

    //多条件查询歌手
    public Page<Singer> page(Singer singer, Date startDate, Date endDate) {
        return singerMapper.page(singer, startDate, endDate);
    }

    //添加歌手
    public int save(Singer singer) {
		if (singer.getSingerId() == null) {
            return singerMapper.add(singer);
        }else {
            return singerMapper.update(singer);
        }
    }

    //通过歌手删除歌手
    public int delSinger(int SingerId) {
        return singerMapper.del(SingerId);
    }

    public List<Singer> list() {
        return singerMapper.list();
    }

}
