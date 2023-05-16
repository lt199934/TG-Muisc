package net.ltbk.music.controller;

import com.github.pagehelper.PageHelper;
import net.ltbk.music.bean.Singer;
import net.ltbk.music.common.Result;
import net.ltbk.music.service.SingerService;
import net.ltbk.music.utils.FileHandleUtil;
import net.ltbk.music.vo.SingerVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
/**
 * @author liutao
 */
@RequestMapping("/singer")
@RestController
public class SingerController {

    @Autowired
    private SingerService singerService;

    //多条件查询歌手
    @PostMapping("/page")
    public Result<Singer> findPage(@RequestBody SingerVo singerVo) {
        PageHelper.startPage(singerVo.getPageNum(), singerVo.getPageSize());
        Singer singer = new Singer();
        singer.setSingerName(singerVo.getSingerName());
        singer.setSex(singerVo.getSex());
        return Result.success(singerService.selectSingerByExample(singer, singerVo.getStartDate(), singerVo.getEndDate()).toPageInfo());
    }

//    // 通过id查询歌手所有信息
//    @RequestMapping("/singer/{singerId}")
//    public Singer selectAllBySingerId(@PathVariable("singerId") String singerId) {
//        System.out.println(singerId);
//        return singerService.selectAllBySingerId(Integer.parseInt(singerId.trim()));
//    }

    //添加歌手
    @PostMapping("/insertOneSinger")
    public Object insertSinger(Singer singer, @Param("img") MultipartFile img) throws IOException {
        int i = 0;
        System.out.println(singer);
        System.out.println(img.getOriginalFilename());
        String fileURL = FileHandleUtil.upload(img.getInputStream(), "singer/", img.getOriginalFilename());
        System.out.println(fileURL);
        if (!"".equals(img.getOriginalFilename())) {
            singer.setImgUrl("/singer/" + img.getOriginalFilename());
        }
        System.out.println(singer);
        i = singerService.insertSinger(singer);
        System.out.println(i);
        return i;
    }

    //删除歌手
    @RequestMapping("/delSinger/{singerId}")
    @ResponseBody
    public int delSinger(@PathVariable("singerId") String singerId) {
        return singerService.delSinger(Integer.parseInt(singerId));
    }

    //查询所有歌手（不限制显示条数）
    @RequestMapping("/AllSingers")
    @ResponseBody
    public Object AllSingers() {
//        List<Singer> singers = singerService.AllSinger();
//        for (Singer s :singers) {
//            System.out.println(s);
//        }
//        String str = JSON.toJSONString(singers);
//        return str;
        return singerService.AllSinger();
    }
}
