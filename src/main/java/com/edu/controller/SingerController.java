package com.edu.controller;

import com.edu.bean.Singer;
import com.edu.service.SingerService;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
public class SingerController {
    @Autowired
    private SingerService singerService;

    //多条件查询歌手
    @RequestMapping("/selectAllSingers")
    @ResponseBody
    public Object selectAllSingers(Singer singer, Date startDate, Date endDate, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        System.out.println("pageNum:" + pageNum);
        System.out.println("pageSize" + pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        return singerService.selectSingerByExample(singer, startDate, endDate).toPageInfo();
    }

    // 通过id查询歌手所有信息
    @RequestMapping("/singer/{singerId}")
    @ResponseBody
    public Singer selectAllBySingerId(@PathVariable("singerId") String singerId) {
        return singerService.selectAllBySingerId(Integer.parseInt(singerId));
    }

    //添加歌手
    @RequestMapping("/insertOneSinger")
    @ResponseBody
    public Object insertSinger(Singer singer, @Param("img") MultipartFile img) throws IOException {
        int i = 0;
        File file = null;
        System.out.println(singer);
        System.out.println(img.getOriginalFilename());
        String os = System.getProperty("os.name");
        //Windows操作系统
        if (os != null && os.toLowerCase().startsWith("windows")) {
            file = new File("c:/music/singer/" + img.getOriginalFilename());
            System.out.printf("当前系统版本是:%s%n", os);
        } else if (os != null && os.toLowerCase().startsWith("linux")) {//Linux操作系统
            file = new File("/data/music/singer/" + img.getOriginalFilename());
            System.out.printf("当前系统版本是:%s%n", os);
        } else { //其它操作系统
            System.out.printf("当前系统版本是:%s%n", os);
        }
        //复制文件 到指定的file对象
        img.transferTo(file);
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
