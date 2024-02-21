package net.ltbk.music.controller;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import net.ltbk.music.bean.Singer;
import net.ltbk.music.bean.vo.SingerVo;
import net.ltbk.music.common.Result;
import net.ltbk.music.service.SingerService;
import net.ltbk.music.utils.FileHandleUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

/**
 * @author liutao
 */
@Slf4j
@RequestMapping("/singer")
@RestController
public class SingerController {

    @Autowired
    private SingerService singerService;

    /**
     * 通过id查询歌手所有信息
     **/
    @RequestMapping(value = "/s/{singerId}", method = RequestMethod.GET)
    public Singer findById(@PathVariable("singerId") Integer singerId) {
        log.info("查询的歌手id：" + singerId);
        return singerService.selectAllBySingerId(singerId);
    }

    /**
     * 查询所有歌手（不限制显示条数）
     **/
    @RequestMapping("/all")
    @ResponseBody
    public Object list() {
        return singerService.list();
    }

    /**
     * 多条件分页查询歌手
     **/
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Result<Object> findPage(SingerVo singerVo) {
        Singer singer = new Singer();
        if (Objects.nonNull(singerVo.getSingerId())) {
            singer.setSingerId(singerVo.getSingerId());
        }
        if (StringUtils.isNotBlank(singerVo.getSingerName())) {
            singer.setSingerName(singerVo.getSingerName());
        }
        if (StringUtils.isNotBlank(singerVo.getSex())) {
            singer.setSex(singerVo.getSex());
        }
        if (singerVo.getPageNum() != null && singerVo.getPageSize() != null) {
            PageHelper.startPage(singerVo.getPageNum(), singerVo.getPageSize());
            return Result.success(singerService.page(singer, singerVo.getStartDate(), singerVo.getEndDate()).toPageInfo());
        }

        return Result.success("成功", singerService.page(singer, singerVo.getStartDate(), singerVo.getEndDate()));
    }

    /**
     * 更新歌手
     **/
    @PostMapping("/save")
    public Result<String> save(Singer singer, @Param("img") MultipartFile img) throws IOException {
        String msg = "";
        if (!"".equals(img.getOriginalFilename())) {
            String fileURL = FileHandleUtil.upload(img.getInputStream(), "singer/", img.getOriginalFilename());
            singer.setImgUrl("/singer/" + img.getOriginalFilename());
            log.info("上传图片地址：" + fileURL);
        }
        log.info("歌手信息：" + singer);
        if (singer.getSingerId() == null) {
            msg = "添加";
        } else {
            msg = "修改";
        }
        return singerService.save(singer) == 1 ? Result.success(msg + "成功") : Result.error(msg + "失败");
    }

    /**
     * 删除歌手
     **/
    @RequestMapping("/delSinger/{singerId}")
    @ResponseBody
    public int delSinger(@PathVariable("singerId") String singerId) {
        return singerService.delSinger(Integer.parseInt(singerId));
    }
}
