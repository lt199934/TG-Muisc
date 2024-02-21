package net.ltbk.music.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import net.ltbk.music.bean.SongList;
import net.ltbk.music.bean.dto.SongListDto;
import net.ltbk.music.common.Result;
import net.ltbk.music.service.SongListService;
import net.ltbk.music.utils.FileHandleUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@RequestMapping("/songList")
@Controller
public class SongListController {
    private final Logger log = LoggerFactory.getLogger(SongListController.class);
    @Autowired
    private SongListService songListService;

    /***
     * @MethodName: allSongLists
     * @description: 所有/分页 查询歌单
     * @Author: LiuTao
     * @Param: [fenId, pageNum, pageSize]
     * @UpdateTime: 2023/6/24 21:28
     * @Return: java.lang.Object
     **/
    @RequestMapping("/all")
    @ResponseBody
    public Object allSongLists() {
        return songListService.selectAll();
    }


    /**
     * 所有/分页 查询歌单
     **/
    @RequestMapping("/page")
    @ResponseBody
    public Object page(SongList songlist, Date startDate, Date endDate, @RequestParam(value = "fenId") String fenId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        log.info("{}", songlist);
        log.info(fenId);
        System.out.println("pageNum:" + pageNum);
        System.out.println("pageSize" + pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        if (!Objects.equals(fenId, "0")) {
            return songListService.selectFenAll(Integer.parseInt(fenId)).toPageInfo();
        }
        return songListService.selectSongListByExample(songlist, startDate, endDate).toPageInfo();
    }

    /**
     * id查询歌单所有信息
     **/
    @RequestMapping("/{songListId}")
    @ResponseBody
    public Object songList(@PathVariable("songListId") String songListId, @RequestParam(required = false) String pageNum, @RequestParam(required = false) String pageSize) {
        if (StringUtil.isNotEmpty(pageNum) && StringUtil.isNotEmpty(pageSize)) {
            PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        }
        SongList songlist = songListService.selectAllBySongListId(Integer.parseInt(songListId));
        songlist.setFenLeis(songListService.selectFenArrayBySongListId(Integer.parseInt(songListId)));
        return songlist;
    }

    /**
     * 显示分类信息
     **/
    @RequestMapping("/fenLei")
    @ResponseBody
    public Object fenLei() {
        return songListService.fen();
    }

    /**
     * 删除歌单
     **/
    @RequestMapping("/delSongList/{songListId}")
    @ResponseBody
    public Object delSongList(@PathVariable("songListId") String songListId) {
        return songListService.delSongList(Integer.parseInt(songListId));
    }

    /**
     * 添加歌单
     **/
    @RequestMapping("/save")
    @ResponseBody
    public Result<String> save(SongListDto songList, @Param("img") MultipartFile img) throws IOException {
        String msg = "";
        log.info("更新歌单：{}", songList);
        if (!"".equals(img.getOriginalFilename())) {
            log.info("上传文件：{}", img.getOriginalFilename());
            String fileURL = FileHandleUtil.upload(img.getInputStream(), "songListImg/", img.getOriginalFilename());
            log.info("访问路径：{}", fileURL);
            songList.setImgUrl("/songListImg/" + img.getOriginalFilename());
        }
        if (songList.getSongListId() == null) {
            msg = "添加";
        } else {
            msg = "修改";
        }
        return songListService.save(songList) == 1 ? Result.success(msg + "成功") : Result.error(msg + "失败");
    }
}