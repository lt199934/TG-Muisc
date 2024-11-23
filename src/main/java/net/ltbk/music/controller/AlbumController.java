package net.ltbk.music.controller;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import net.ltbk.music.bean.Album;
import net.ltbk.music.common.Constants;
import net.ltbk.music.common.Result;
import net.ltbk.music.common.exception.ServiceException;
import net.ltbk.music.service.AlbumService;
import net.ltbk.music.utils.FileHandleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Slf4j
@RequestMapping("/albums")
@Controller
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    /**
     * 点击专辑查询所有专辑信息
     **/
    @RequestMapping(value = "/{albumId}", method = RequestMethod.GET)
    @ResponseBody
    public Object findOne(@PathVariable("albumId") Integer albumId) {
        log.info("albumId:" + albumId);
        Album album = albumService.findById(albumId);
        album.setCount(albumService.count(albumId));
        return album;
    }

    /**
     * 查询所有专辑
     **/
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Object findAll() {
        return albumService.findAll();
    }

    /**
     * 管理员通过id删除专辑
     **/
    @RequestMapping(value = "/del/{albumId}", method = RequestMethod.DELETE)
    @ResponseBody
    public int delUser(@PathVariable("albumId") String albumId) {
        System.out.println("albumId:" + Integer.parseInt(albumId));
        return albumService.delAlbumById(Integer.parseInt(albumId));
    }

    /**
     * 分页多条件查询
     **/
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public Object page(Album album, Date startDate, Date endDate, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        log.info("{}", album);
        log.info(startDate + "-" + endDate);
        log.info("pageNum：{} pageSize：{}", pageNum, pageSize);
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        if (Objects.nonNull(album) || Objects.nonNull(startDate) || Objects.nonNull(endDate)) {
            return albumService.selectAlbumByExample(album, startDate, endDate).toPageInfo();
        }
        return albumService.findAll();
    }

    /**
     * 管理员通过SingerId查询专辑
     **/
    @RequestMapping(value = "/s/{singerId}", method = RequestMethod.GET)
    @ResponseBody
    public Object findBySingerId(@PathVariable("singerId") Integer singerId) {
        log.info("singerId:" + singerId);
        return albumService.findBySingerId(singerId).toPageInfo();
    }

    /**
     * 更新专辑
     **/
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> save(Album album, @RequestParam("img") MultipartFile img) throws IOException {
        String msg = "";
        log.info("更新专辑：{}", album);
        if (!"".equals(img.getOriginalFilename())) {
            log.info("上传专辑封面：{}", img.getOriginalFilename());
            String fileURL = null;
            try {
                fileURL = FileHandleUtil.upload(img.getInputStream(), "album/", img.getOriginalFilename());
            } catch (IOException e) {
                throw new ServiceException(Constants.CODE_WARNING, "专辑封面上传失败");
            }
            log.info("访问路径：{}", fileURL);
            album.setAlbumImg("/album/" + img.getOriginalFilename());
        }
        Assert.notNull(album.getAlbumId(), msg = "修改");
        if (album.getAlbumId() == null) {
            msg = "添加";
        } else {
            msg = "修改";
        }
        return albumService.save(album) == 1 ? Result.success(msg + "成功") : Result.error(msg + "失败");
    }


}
