package com.edu.controller;

import com.edu.bean.Song;
import com.edu.service.SongService;
import com.github.pagehelper.PageHelper;
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
public class SongController {
    @Autowired
    private SongService songService;
//    后台多条件查询
    @RequestMapping("/selectSongByExample")
    @ResponseBody
    public Object selectSongByExample(Song song,Date startDate,Date endDate,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        System.out.println(song);
        System.out.println("pageNum:"+pageNum);
        System.out.println("pageSize"+pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        System.out.println(startDate+"-"+endDate);
        return songService.selectSongByExample(song,startDate,endDate).toPageInfo();
    }

    //    显示曲库
    @RequestMapping("/allSongs")
    @ResponseBody
    public Object allSongs(@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum, @RequestParam(value = "pageSize",required = false,defaultValue = "2")String pageSize){
        System.out.println("pageNum:"+pageNum);
        System.out.println("pageSize"+pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        return songService.selectAll().toPageInfo();
    }
//上传歌曲
    @RequestMapping("/uploadSong")
    @ResponseBody
    public int uploadSong(Song song,@RequestParam("songPath")MultipartFile songPath) throws IOException {
        System.out.println(song);
        System.out.println(songPath.getOriginalFilename());
        String result=null;
        int num=0;
        int i=songPath.getOriginalFilename().lastIndexOf("-");
        String data=songPath.getOriginalFilename().substring(i+1);
        System.out.println(data);
        //windows
//        File file = new File("c:/music/songs/"+data);
        //linux
        File file = new File("/data/music/songs/"+data);
//        复制文件 到指定的file对象
        songPath.transferTo(file);
        if (!"".equals(songPath.getOriginalFilename())){
            song.setUrl("/songs/"+data);
        }
        System.out.println(song);
        num = songService.uploadSong(song);

        return num;
    }

    //管理员通过id删除歌曲
    @RequestMapping("/delSong/{songId}")
    @ResponseBody
    public int delSong(@PathVariable("songId") String songId){
        return songService.deleteSongById(Integer.parseInt(songId));
    }

    //   播放音乐
    @RequestMapping("/playSong/{songId}")
    @ResponseBody
    public Object play(@PathVariable("songId") String songId){
        System.out.println("歌曲id"+songId);
        return songService.selectSongById(Integer.parseInt(songId));
    }

    //播放排行榜
    @RequestMapping("/list")
    @ResponseBody
    public Object selectSongsByList(@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum, @RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        System.out.println("pageNum:"+pageNum);
        System.out.println("pageSize:"+pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        return songService.selectSongsByList().toPageInfo();
    }
    //   播放量 播放一次+1
    @RequestMapping("/updatePlayCount/{songId}")
    @ResponseBody
    public int updatePlayCount(@PathVariable("songId") String songId){
        System.out.println("歌曲id"+songId);
        return songService.updatePlayCount(Integer.parseInt(songId));
    }
    //   播放量 播放一次+1
    @RequestMapping("/addDownloadCount/{songId}")
    @ResponseBody
    public int updateDownloadCount(@PathVariable("songId") String songId){
        return songService.updatePlayCount(Integer.parseInt(songId));
    }
}
