package com.edu.controller;


import com.edu.bean.SongList;
import com.edu.bean.SongDto;
import com.edu.bean.SongListDto;
import com.edu.service.SongListService;
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
import java.util.List;

@Controller
public class SongListController {
    @Autowired
    private SongListService songListService;
//所有歌单
    @RequestMapping("/allSongLists")
    @ResponseBody
    public Object allSongLists(@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum, @RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        System.out.println("pageNum:"+pageNum);
        System.out.println("pageSize"+pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        return songListService.selectAll().toPageInfo();
    }

    //所有歌单
    @RequestMapping("/allFenSongLists")
    @ResponseBody
    public Object allFenSongLists(@RequestParam(value = "fenId") String fenId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum, @RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        System.out.println("pageNum:"+pageNum);
        System.out.println("pageSize"+pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        return songListService.selectFenAll(Integer.parseInt(fenId)).toPageInfo();
    }
// 后台多条件查询
    @RequestMapping("/selectSongListByExample")
    @ResponseBody
    public Object selectSongListByExample(SongList songlist,Date startDate,Date endDate,@RequestParam(value = "pageNum",defaultValue = "1",required = false)String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        System.out.println(songlist);
        System.out.println("pageNum:"+pageNum);
        System.out.println("pageSize"+pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        return songListService.selectSongListByExample(songlist,startDate,endDate).toPageInfo();
    }

    // 通过id查询歌单所有信息
    @RequestMapping("/songList/{songListId}")
    @ResponseBody
    public Object songList(@PathVariable("songListId") String songListId){
        SongList songlist=songListService.selectAllBySongListId(Integer.parseInt(songListId));
        songlist.setFenLeis(songListService.selectFenArrayBySongListId(Integer.parseInt(songListId)));
        return songlist;
    }
    //    显示分类信息
    @RequestMapping("/fenLei")
    @ResponseBody
    public Object fenLei(){
        return songListService.fen();
    }
    //删除歌单
    @RequestMapping("/delSongList/{songListId}")
    @ResponseBody
    public Object delSongList(@PathVariable("songListId") String songListId){
        return songListService.delSongList(Integer.parseInt(songListId));
    }
    //添加歌单
    @RequestMapping("/insertSongList")
    @ResponseBody
    public Object insertSongList(SongListDto songList , @Param("img") MultipartFile img) throws IOException {
        int num=0;
        File file=null;
        System.out.println(songList);
        System.out.println(img.getOriginalFilename());
        String os = System.getProperty("os.name");
        //Windows操作系统
        if (os != null && os.toLowerCase().startsWith("windows")) {
            file = new File("c:/music/songlistImg/"+img.getOriginalFilename());
            System.out.println(String.format("当前系统版本是:%s", os));
        } else if (os != null && os.toLowerCase().startsWith("linux")) {//Linux操作系统
            file = new File("/data/music/songlistImg/"+img.getOriginalFilename());
            System.out.println(String.format("当前系统版本是:%s", os));
        } else { //其它操作系统
            System.out.println(String.format("当前系统版本是:%s", os));
        }
        //复制文件 到指定的file对象
        img.transferTo(file);
        if (!"".equals(img.getOriginalFilename())){
            songList.setImgUrl("/songlistImg/"+img.getOriginalFilename());
        }
        System.out.println(songList);
        num = songListService.insertSongList(songList);
        return num;
    }
}
