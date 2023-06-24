package net.ltbk.music.controller;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import net.ltbk.music.bean.Album;
import net.ltbk.music.service.AlbumService;
import net.ltbk.music.utils.FileHandleUtil;
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

@Slf4j
@Controller
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    //点击专辑查询所有专辑信息
    @RequestMapping("/albums/{albumId}")
    @ResponseBody
    public Object selectAllAlbum(@PathVariable("albumId") String albumId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        log.info("albumId:" + albumId);
        Album album = albumService.selectAllByAlbumId(Integer.parseInt(albumId));
        album.setCount(albumService.count(Integer.parseInt(albumId)));
        return album;
    }

    //查询所有专辑
    @RequestMapping("/allAlbums")
    @ResponseBody
    public Object selectAllAlbumsByPage(@RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "3") String pageSize) {
       log.info("pageNum:{} pageSize：{}" , pageNum,pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        return albumService.selectAll().toPageInfo();
    }

    //  后台在添加歌曲时动态加载所有专辑不分页
    @RequestMapping("/allAlbum")
    @ResponseBody
    public Object selectAllAlbumsByNoPage() {
        return albumService.selectAll();
    }

    //管理员通过id删除专辑
    @RequestMapping("/delAlbum/{albumId}")
    @ResponseBody
    public int delUser(@PathVariable("albumId") String albumId) {
        System.out.println("albumId:" + Integer.parseInt(albumId));
        return albumService.delAlbumById(Integer.parseInt(albumId));
    }

    //  多条件查询
    @RequestMapping("/selectAlbumByExample")
    @ResponseBody
    public Object selectAlbum(Album album, Date startDate, Date endDate, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        System.out.println(album);
        System.out.println(startDate + "-" + endDate);
        System.out.println("pageNum:" + pageNum);
        System.out.println("pageSize" + pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        return albumService.selectAlbumByExample(album, startDate, endDate).toPageInfo();
    }

    //查询所有专辑（不限制显示条数）
    @RequestMapping("/selectAllAlbum")
    @ResponseBody
    public Object allAlbum() {
//        List<Album> albums = albumService.allAlbum();
//        String str = JSON.toJSONString(albums);
        return albumService.allAlbum();
    }

    //管理员通过SingerId查询专辑
    @RequestMapping("/SelAlbum/{singerId}")
    @ResponseBody
    public Object SelAlbum(@PathVariable("singerId") String singerId) {
        System.out.println("singerId:" + Integer.parseInt(singerId));
        return albumService.singerIdAlbum(Integer.parseInt(singerId)).toPageInfo();
    }

    //添加专辑
    @RequestMapping("/insertAlbum")
    @ResponseBody
    public int insertAlbum(Album album, @RequestParam("img") MultipartFile img) throws IOException {
        System.out.println(album);
        System.out.println(img.getOriginalFilename());
        int num = 0;
        String fileURL = FileHandleUtil.upload(img.getInputStream(), "album/", img.getOriginalFilename());
        System.out.println(fileURL);
        if (!"".equals(img.getOriginalFilename())) {
            album.setAlbumImg("/album/" + img.getOriginalFilename());
        }
        System.out.println(album);
        num = albumService.insertAlbum(album);
        return num;
    }


}
