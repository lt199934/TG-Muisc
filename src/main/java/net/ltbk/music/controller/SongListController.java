package net.ltbk.music.controller;


import com.github.pagehelper.PageHelper;
import net.ltbk.music.bean.SongList;
import net.ltbk.music.bean.dto.SongListDto;
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
    @RequestMapping("/songList/all")
    @ResponseBody
    public Object allSongLists(@RequestParam(value = "fenId") String fenId,
                               @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        log.info("pageNum：{} pageSize：{}", pageNum, pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        log.info(fenId);
        if (Objects.equals(fenId, "0")) {
            return songListService.selectAll().toPageInfo();
        }
        return songListService.selectFenAll(Integer.parseInt(fenId)).toPageInfo();
    }


    /*后台多条件查询*/
    @RequestMapping("/selectSongListByExample")
    @ResponseBody
    public Object selectSongListByExample(SongList songlist,
                                          Date startDate,
                                          Date endDate,
                                          @RequestParam(value = "fenId") String fenId,
                                          @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
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

    // 通过id查询歌单所有信息
    @RequestMapping("/songList/{songListId}")
    @ResponseBody
    public Object songList(@PathVariable("songListId") String songListId) {
        SongList songlist = songListService.selectAllBySongListId(Integer.parseInt(songListId));
        songlist.setFenLeis(songListService.selectFenArrayBySongListId(Integer.parseInt(songListId)));
        return songlist;
    }

    //    显示分类信息
    @RequestMapping("/fenLei")
    @ResponseBody
    public Object fenLei() {
        return songListService.fen();
    }

    /*删除歌单*/
    @RequestMapping("/delSongList/{songListId}")
    @ResponseBody
    public Object delSongList(@PathVariable("songListId") String songListId) {
        return songListService.delSongList(Integer.parseInt(songListId));
    }

    /*添加歌单*/
    @RequestMapping("/insertSongList")
    @ResponseBody
    public Object insertSongList(SongListDto songList, @Param("img") MultipartFile img) throws IOException {
        int num = 0;
        System.out.println(songList);
        if (!"".equals(img.getOriginalFilename())) {
            String fileURL = FileHandleUtil.upload(img.getInputStream(), "songListImg/", img.getOriginalFilename());
            System.out.println(fileURL);

            songList.setImgUrl("/songListImg/" + img.getOriginalFilename());
        }
        System.out.println(songList);
        num = songListService.insertSongList(songList);
        return num;
    }
}