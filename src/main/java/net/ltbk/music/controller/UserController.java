package net.ltbk.music.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.ltbk.music.bean.User;
import net.ltbk.music.bean.vo.UserVo;
import net.ltbk.music.common.Constants;
import net.ltbk.music.common.Result;
import net.ltbk.music.common.exception.ServiceException;
import net.ltbk.music.service.UserService;
import net.ltbk.music.utils.FileHandleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Slf4j
@Api(tags = "用户接口")
@RequestMapping("/user")
@RestController
public class UserController {
    private final Map<String, HttpSession> sessionMap = new HashMap<>();
    @Autowired
    private UserService userService;

    @ApiOperation("用户登录")
    @PostMapping("/userLogin")
    public Result<Integer> userLogin(User user, HttpSession session) {
        log.info("登录用户：{}", user);
        User login = userService.login(user);
        if (login == null || !user.getPwd().equals(login.getPwd())) {
            return Result.error("用户名或密码错误");
        }
        if (Objects.equals("普通用户", user.getType()) && !Objects.equals(user.getType(), login.getType())) {
            return Result.error("该角色不存在！");
        }
        // 存储会话
        if (!sessionMap.isEmpty()) {
            // 判断是否登录过
            if (sessionMap.containsKey(login.getUserId().toString())) {
                // 过期上一个session
                sessionMap.get(login.getUserId().toString()).invalidate();
                sessionMap.remove(login.getAccount());
            }
        }
        session.setMaxInactiveInterval(30 * 60);
        session.setAttribute("user", login);
        session.setAttribute(login.getUserId().toString(), login);
        sessionMap.put(login.getUserId().toString(), session);
        return Result.success("登录成功", login.getUserId());
    }


    /***
     * @MethodName: userRegister
     * @description: 用户注册
     * @Author: LiuTao
     * @Param: [user, img]
     * @UpdateTime: 2022/11/1 14:50
     * @Return: int
     * @Throw: IOException
     **/
    @PostMapping("/save")
    public Result<Boolean> save(User user, @RequestParam("img") MultipartFile img) {
        String msg = "";
        if (user == null) {
            return Result.error("注册失败");
        }
        if (!"".equals(img.getOriginalFilename())) {
            log.info(img.getOriginalFilename());
            String fileURL = null;
            try {
                fileURL = FileHandleUtil.upload(img.getInputStream(), "headImg/", img.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            log.info("上传地址：{}", fileURL);
            user.setHeadImg("/headImg/" + img.getOriginalFilename());
        }
        log.info("更新用户：{}", user);
        if (user.getUserId() == null) {
            msg = "添加成功";
        } else {
            msg = "修改成功";
        }
        return Result.success(msg, userService.saveUser(user));
    }

    @GetMapping("/isLogin")
    public Boolean isLogin(String userId) {
        return sessionMap.containsKey(userId);
    }

    @GetMapping("/getUserInfo/{userId}")
    public Object getUserLoginInfo(@PathVariable("userId") String userId) {
        HttpSession session = sessionMap.get(userId);
        try {
            User user = (User) session.getAttribute(userId);
            log.info("当前用户：" + user);
            return user;
        } catch (NullPointerException e) {
            throw new ServiceException(Constants.CODE_NOT_FORBIDDEN, "登录失效");
        }
    }

    @PostMapping("/logout")
    public String isLogout(String userId, HttpSession session) {
        System.out.println("清除用户：" + userId);
        session.removeAttribute(userId);
        session.invalidate();
        return "/login";
    }

    /**
     * @MethodName: checkAccount
     * @description: 用户重复
     * @Author: LiuTao
     * @Param: [user]
     * @UpdateTime: 2023/6/22 19:50
     * @Return: java.util.Map<java.lang.String, java.lang.Boolean>
     * @Throw:
     **/
    @PostMapping("/checkAccount")
    public Map<String, Boolean> checkAccount(User user) {
        System.out.println(user);
        User login = userService.selectByAccount(user.getAccount());
        System.out.println(login);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        if (login == null) {
            map.put("valid", true);
        } else {
            map.put("valid", false);
        }
        System.out.println(user.getAccount() + map);
        return map;
    }

    /**
     * 管理员通过id删除用户
     **/
    @DeleteMapping("/del/{userId}")
    public int delUser(@PathVariable("userId") String userId) {
        return userService.delUser(Integer.parseInt(userId));
    }

    @DeleteMapping("/del/batch")
    public Result<Boolean> delBatch(@RequestBody List<Integer> ids) {
        return Result.success(userService.delBatch(ids));
    }


    /**
     * 管理员和用户根据指定模版进行用户信息修改
     **/
    @PostMapping("/updateUser")
    public Result<Boolean> updateUser(User user) {
        return Result.success(userService.saveUser(user));
    }

    //通过用户id显示需要修改的用户信息
    @GetMapping("/selectByUserId/{userId}")
    public User selectByUserId(@PathVariable("userId") String userId) {
        return userService.selectByUserId(Integer.parseInt(userId));
    }

    /**
     * 多条件显示所有用户
     **/
    @PostMapping("/page")
    public Result<PageInfo<User>> findPage(@RequestBody UserVo userVo) {
        log.info("pageNum：{} pageSize：{}", userVo.getPageNum(), userVo.getPageSize());
        User user = new User();
        user.setUserId(userVo.getUserId());
        user.setAccount(userVo.getAccount());
        user.setPhone(userVo.getPhone());
        user.setEmail(userVo.getEmail());
        user.setSex(userVo.getSex());
        if (Optional.ofNullable(userVo.getPageNum()).orElse(0) != 0 && Optional.ofNullable(userVo.getPageSize()).orElse(0) != 0) {
            PageHelper.startPage(userVo.getPageNum(), userVo.getPageSize());
        }
        return Result.success(userService.selectUserByExample(user, userVo.getStartDate(), userVo.getEndDate()).toPageInfo());
    }

    //  通过id查询自己创建的歌单
    @GetMapping("/selectAllByMine/{userId}")
    public Object selectSongListsByMe(@PathVariable("userId") String userId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        return userService.selectSongListByMe(Integer.parseInt(userId)).toPageInfo();
    }

    //  通过id查询自己收藏的歌单
    @GetMapping("/colSongLists/{userId}")
    public Object selectSongLists(@PathVariable("userId") String userId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        return userService.selectSongLists(Integer.parseInt(userId)).toPageInfo();
    }

    //  通过id查询自己收藏的歌曲
    @GetMapping("/colSongs/{userId}")
    public Object selectSongs(@PathVariable("userId") String userId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        return userService.selectSongs(Integer.parseInt(userId)).toPageInfo();
    }

    //  通过id查询自己收藏的歌手
    @GetMapping("/colSingers/{userId}")
    public Object selectSingers(@PathVariable("userId") String userId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        return userService.selectSingers(Integer.parseInt(userId)).toPageInfo();
    }

    //  通过id查询自己收藏的专辑
    @RequestMapping("/colAlbums/{userId}")
    @ResponseBody
    public Object selectAlbums(@PathVariable("userId") String userId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        return userService.selectAlbums(Integer.parseInt(userId)).toPageInfo();
    }

    //  收藏歌曲
    @RequestMapping("/collectSongs")
    @ResponseBody
    public Object collectSongs(@RequestParam("userId") String userId, @RequestParam("songId") String songId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        return userService.insertSongsByCollected(Integer.parseInt(userId), Integer.parseInt(songId));
    }

    //  查询是否已收藏歌曲
    @RequestMapping("/selectIsCollectedSong")
    @ResponseBody
    public Object selectIsCollectedSong(@RequestParam("userId") String userId, @RequestParam("songId") String songId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        return userService.insertSongsByCollected(Integer.parseInt(userId), Integer.parseInt(songId));
    }

    //  取消收藏歌曲
    @RequestMapping("/delCollectedSongs")
    @ResponseBody
    public Object delCollectSongs(@RequestParam("userId") String userId, @RequestParam("songId") String songId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        return userService.delSongsByCollected(Integer.parseInt(userId), Integer.parseInt(songId));
    }

    //  收藏歌单
    @RequestMapping("/collectSongLists")
    public Object collectSongLists(@RequestParam("userId") String userId, @RequestParam("songListId") String songListId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        return userService.insertSongListsByCollected(Integer.parseInt(userId), Integer.parseInt(songListId));
    }


    //  取消收藏歌单
    @RequestMapping("/delCollectSongLists")
    @ResponseBody
    public Object delCollectSongLists(@RequestParam("userId") String userId, @RequestParam("songListId") String songListId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        return userService.delSongListsByCollected(Integer.parseInt(userId), Integer.parseInt(songListId));
    }


    //  收藏歌手
    @RequestMapping("/collectSingers")
    @ResponseBody
    public Object collectSingers(@RequestParam("userId") String userId, @RequestParam("singerId") String singerId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        return userService.insertSingersByCollected(Integer.parseInt(userId), Integer.parseInt(singerId));
    }

    //  取消收藏歌手
    @RequestMapping("/delCollectSingers")
    @ResponseBody
    public Object delCollectSingers(@RequestParam("userId") String userId, @RequestParam("singerId") String singerId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        return userService.delSingersByCollected(Integer.parseInt(userId), Integer.parseInt(singerId));
    }

    //  收藏专辑
    @RequestMapping("/collectAlbums")
    @ResponseBody
    public Object collectAlbums(@RequestParam("userId") String userId, @RequestParam("albumId") String albumId, @RequestParam(value = "pageNum", defaultValue = "1", required = false) String pageNum, @RequestParam(value = "pageSize", required = false, defaultValue = "1") String pageSize) {
        return userService.insertAlbumsByCollected(Integer.parseInt(userId), Integer.parseInt(albumId));
    }

    @PostMapping("/albumStatus")
    public Object selectAlbumStatus(@RequestParam("userId") String userId, @RequestParam("albumId") String albumId) {
        return userService.selectAlbumStatus(Integer.parseInt(userId), Integer.parseInt(albumId));
    }

    @PostMapping("/songListStatus")
    public Object selectSongListStatus(@RequestParam("userId") String userId, @RequestParam("songListId") String songListId) {
        return userService.selectSongListStatus(Integer.parseInt(userId), Integer.parseInt(songListId));
    }

    @PostMapping("/songStatus")
    public Object selectSongStatus(@RequestParam("userId") String userId, @RequestParam("songId") String songId) {
        return userService.selectSongStatus(Integer.parseInt(userId), Integer.parseInt(songId));
    }

    //  收藏专辑
    @PostMapping("/delCollectAlbums")
    public Object delCollectAlbums(@RequestParam("userId") String userId, @RequestParam("albumId") String albumId) {
        return userService.delAlbumsByCollected(Integer.parseInt(userId), Integer.parseInt(albumId));
    }

    //  添加歌曲到自己创建的歌单
    @PostMapping("/addSongsToSongList/{songListId}")
    public Result<String> addSongsToSongList(@PathVariable String songListId, @RequestParam("songId") String songId) {
        log.info("歌单id：{} 歌曲id：{}", songListId, songId);
        int added = userService.addSongsToSongList(Integer.parseInt(songListId), Integer.parseInt(songId));
        return added == -1 ? Result.error("该歌曲已存在！") : (added == 0 ? Result.error("添加失败") : Result.success("添加成功"));
    }

    /**
     * 删除歌曲到自己创建的歌单
     **/
    @PostMapping("/delSongsToSongList")
    public Result<String> delSongsToSongList(@RequestParam String songListId, @RequestParam String songId) {
        log.info(songListId);
        return userService.delSongsToSongList(Integer.parseInt(songListId), Integer.parseInt(songId)) == 0 ? Result.error("删除失败") : Result.success("删除成功");
    }

    //查询所有用户
    @GetMapping("/allUsers")
    public Result<List<User>> selAllUser() {
        return Result.success(userService.selAllUser());
    }

}
