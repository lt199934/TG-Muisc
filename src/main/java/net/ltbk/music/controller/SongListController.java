package net.ltbk.music.controller;


import net.ltbk.music.bean.SongList;
import net.ltbk.music.bean.SongListDto;
import net.ltbk.music.service.SongListService;
import com.github.pagehelper.PageHelper;
import net.ltbk.music.utils.FileHandleUtil;
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
public class SongListController {
    @Autowired
    private SongListService songListService;

    //所有歌单
    @RequestMapping("/allSongLists")
    @ResponseBody
    public Object allSongLists(@RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        System.out.println("pageNum:" + pageNum);
        System.out.println("pageSize" + pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        return songListService.selectAll().toPageInfo();
    }

    //所有歌单
    @RequestMapping("/allFenSongLists")
    @ResponseBody
    public Object allFenSongLists(@RequestParam(value = "fenId") String fenId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        System.out.println("pageNum:" + pageNum);
        System.out.println("pageSize" + pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        return songListService.selectFenAll(Integer.parseInt(fenId)).toPageInfo();
    }

    // 后台多条件查询
    @RequestMapping("/selectSongListByExample")
    @ResponseBody
    public Object selectSongListByExample(SongList songlist, Date startDate, Date endDate, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        System.out.println(songlist);
        System.out.println("pageNum:" + pageNum);
        System.out.println("pageSize" + pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
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

    //删除歌单
    @RequestMapping("/delSongList/{songListId}")
    @ResponseBody
    public Object delSongList(@PathVariable("songListId") String songListId) {
        return songListService.delSongList(Integer.parseInt(songListId));
    }

    //添加歌单
    @RequestMapping("/insertSongList")
    @ResponseBody
    public Object insertSongList(SongListDto songList, @Param("img") MultipartFile img) throws IOException {
        int num = 0;
        System.out.println(songList);
        String fileURL = FileHandleUtil.upload(img.getInputStream(), "songListImg/", img.getOriginalFilename());
        System.out.println(fileURL);
        if (!"".equals(img.getOriginalFilename())) {
            songList.setImgUrl("/songListImg/" + img.getOriginalFilename());
        }
        System.out.println(songList);
        num = songListService.insertSongList(songList);
        return num;
    }
}