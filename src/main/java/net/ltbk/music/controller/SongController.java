package net.ltbk.music.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import net.ltbk.music.bean.Song;
import net.ltbk.music.common.Result;
import net.ltbk.music.service.SongService;
import net.ltbk.music.utils.FileHandleUtil;
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

@Slf4j
@Controller
public class SongController {
    @Autowired
    private SongService songService;

    //    后台多条件查询
    @RequestMapping("/selectSongByExample")
    @ResponseBody
    public Result<PageInfo<Song>> selectSongByExample(Song song, Date startDate, Date endDate, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        log.info("{}", song);
        log.info(startDate + "-" + endDate);
        log.info("pageNum：{} pageSize：{}", pageNum, pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        return Result.success(songService.selectSongByExample(song, startDate, endDate).toPageInfo());
    }

    //    显示曲库
    @RequestMapping("/allSongs")
    @ResponseBody
    public Object allSongs(@RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "2") String pageSize) {
        System.out.println("pageNum:" + pageNum);
        System.out.println("pageSize" + pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        return songService.selectAll().toPageInfo();
    }

    /**
     * 更新歌曲
     **/
    @RequestMapping("/uploadSong")
    @ResponseBody
    public Result<String> save(Song song, @RequestParam("songPath") MultipartFile songPath) throws IOException {
        String msg = "";
        log.info("更新歌曲：{}", song);
        if (!"".equals(songPath.getOriginalFilename())) {
            log.info("上传文件：{}", songPath.getOriginalFilename());
            System.out.println();
            int i = Objects.requireNonNull(songPath.getOriginalFilename()).lastIndexOf("-");
            String data = songPath.getOriginalFilename().substring(i + 1);
            String fileURL = FileHandleUtil.upload(songPath.getInputStream(), "headImg/", data);
            log.info("访问路径：{}", fileURL);
            song.setUrl("/songs/" + data);
        }
        if (song.getSongId() == null) {
            msg = "添加";
        } else {
            msg = "修改";
        }
        return songService.save(song) == 1 ? Result.success(msg+"成功") : Result.error(msg+"失败");
    }

    //管理员通过id删除歌曲
    @RequestMapping("/delSong/{songId}")
    @ResponseBody
    public int delSong(@PathVariable("songId") String songId) {
        return songService.deleteSongById(Integer.parseInt(songId));
    }

    //   播放音乐
    @RequestMapping("/playSong/{songId}")
    @ResponseBody
    public Object play(@PathVariable("songId") String songId) {
        System.out.println("歌曲id" + songId);
        return songService.selectSongById(Integer.parseInt(songId));
    }

    //播放排行榜
    @RequestMapping("/list")
    @ResponseBody
    public Object selectSongsByList(@RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        System.out.println("pageNum:" + pageNum);
        System.out.println("pageSize:" + pageSize);
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        return songService.selectSongsByList().toPageInfo();
    }

    //   播放量 播放一次+1
    @RequestMapping("/updatePlayCount/{songId}")
    @ResponseBody
    public int updatePlayCount(@PathVariable("songId") String songId) {
        System.out.println("歌曲id" + songId);
        return songService.updatePlayCount(Integer.parseInt(songId));
    }

    //   播放量 播放一次+1
    @RequestMapping("/addDownloadCount/{songId}")
    @ResponseBody
    public int updateDownloadCount(@PathVariable("songId") String songId) {
        return songService.updatePlayCount(Integer.parseInt(songId));
    }
}
