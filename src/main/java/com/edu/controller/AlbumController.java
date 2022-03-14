package com.edu.controller;

import com.alibaba.fastjson.JSON;
import com.edu.bean.Album;
import com.edu.service.AlbumService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class AlbumController {
    @Autowired
    private AlbumService albumService;
//点击专辑查询所有专辑信息
    @RequestMapping("/album/{albumId}")
    @ResponseBody
    public Object selectAllAlbums(@PathVariable("albumId")String albumId){
        System.out.println("albumId:"+albumId);
        Album album=albumService.selectAllByAlbumId(Integer.parseInt(albumId));
        album.setCount(albumService.count(Integer.parseInt(albumId)));
        return album;
    }
//查询所有专辑
    @RequestMapping("/allAlbums")
    @ResponseBody
    public Object selectAllAlbums(@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum, @RequestParam(value = "pageSize",required = false,defaultValue = "2")String pageSize){
        System.out.println("pageNum:"+pageNum);
        System.out.println("pageSize"+pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        return albumService.selectAll().toPageInfo();
    }
    //管理员通过id删除专辑
    @RequestMapping("/delAlbum/{albumId}")
    @ResponseBody
    public int delUser(@PathVariable("albumId") String albumId){
        System.out.println("albumId:"+Integer.parseInt(albumId));
        return albumService.delAlbumById(Integer.parseInt(albumId));
    }

    //  多条件查询
    @RequestMapping("/selectAlbumByExample")
    @ResponseBody
    public Object selectAlbum(Album album,Date startDate, Date endDate, @RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum, @RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        System.out.println(album);
        System.out.println(startDate+"-"+endDate);
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        return albumService.selectAlbumByExample(album,startDate,endDate).toPageInfo();
    }

    //查询所有专辑（不限制显示条数）
    @RequestMapping("/selectallAlbum")
    @ResponseBody
    public Object allAlbum(){
//        List<Album> albums = albumService.allAlbum();
//        String str = JSON.toJSONString(albums);
        return albumService.allAlbum();
    }

    //管理员通过SingerId查询专辑
    @RequestMapping("/SelAlbum/{singerId}")
    @ResponseBody
    public Object SelAlbum(@PathVariable("singerId") String singerId){
        System.out.println("singerId:"+Integer.parseInt(singerId));
        return albumService.singerIdAlbum(Integer.parseInt(singerId)).toPageInfo();
    }
    //添加专辑
    @RequestMapping("/insertAlbum")
    @ResponseBody
    public int insertAlbum(Album album){
        return albumService.insertAlbum(album);
    }
}
