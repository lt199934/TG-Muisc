package com.edu.controller;

import com.alibaba.fastjson.JSON;
import com.edu.bean.User;
import com.edu.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;


    //用户登录
    @RequestMapping("/userLogin")
    @ResponseBody
    public User userLogin(User user){
        Map<String,Object> map = new HashMap<String, Object>();
        System.out.println(user);
        User login = userService.login(user);
        System.out.println(login);
        if(login ==null){
            map.put("result","登录失败");
        }else{
            map.put("result","登录成功");
            map.put("user",login);
        }
        return login;
    }


//用户注册
    @RequestMapping("/userRegister")
    @ResponseBody
    public int userRegister(User user,@RequestParam("img") MultipartFile img) throws IOException {
        System.out.println(user);
        int register=0;
        File file=null;
        System.out.println(img.getOriginalFilename());
        String data=System.currentTimeMillis()+img.getOriginalFilename();
        String os = System.getProperty("os.name");
        //Windows操作系统
        if (os != null && os.toLowerCase().startsWith("windows")) {
            file = new File("c:/music/headImg/"+data);
            System.out.println(String.format("当前系统版本是:%s", os));
        } else if (os != null && os.toLowerCase().startsWith("linux")) {//Linux操作系统
            file = new File("/data/music/headImg/"+data);
            System.out.println(String.format("当前系统版本是:%s", os));
        } else { //其它操作系统
            System.out.println(String.format("当前系统版本是:%s", os));
        }
        //复制文件 到指定的file对象
        img.transferTo(file);
        if (!"".equals(img.getOriginalFilename())){
            user.setHeadImg("/headImg/"+data);
        }
        System.out.println(user);
          register = userService.register(user);
        System.out.println(register);
        return register;
    }
    //用户重复
    @RequestMapping("/checkAccount")
    @ResponseBody
    public  Map<String,Boolean> checkAccount(User user){
        System.out.println(user);
        User login = userService.selectByAccount(user.getAccount());
        System.out.println(login);
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        if (login==null){
            map.put("valid",true);
        }else {
            map.put("valid",false);
        }
        System.out.println(user.getAccount()+map);
        return map;
    }
    //管理员通过id删除用户
    @RequestMapping("/delUser/{userId}")
    @ResponseBody
    public int delUser(@PathVariable("userId") String userId){
        return userService.delUser(Integer.parseInt(userId));
    }

    //管理员和用户根据指定模版进行用户信息修改
    @RequestMapping("/updateUser")
    @ResponseBody
    public int updateUser(User user){
        return userService.updateUser(user);
    }

    //通过用户id显示需要修改的用户信息
    @RequestMapping("/selectByUserId/{userId}")
    @ResponseBody
    public User selectByUserId(@PathVariable("userId") String userId){
        return userService.selectByUserId(Integer.parseInt(userId));
    }

    //多条件显示所有用户
    @RequestMapping("/allUser")
    @ResponseBody
    public Object selectAll(User user,Date startDate, Date endDate,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        System.out.println(user);
        System.out.println("pageNum:"+pageNum);
        System.out.println("pageSize"+pageSize);
        System.out.println(startDate+"-"+endDate);
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        return userService.selectUserByExample(user,startDate,endDate).toPageInfo();
    }
    //  通过id查询自己创建的歌单
    @RequestMapping("/selectAllByMine/{userId}")
    @ResponseBody
    public Object selectSongListsByMe(@PathVariable("userId") String userId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.selectSongListByMe(Integer.parseInt(userId)).toPageInfo();
    }

    //  通过id查询自己收藏的歌单
    @RequestMapping("/colSongLists/{userId}")
    @ResponseBody
    public Object selectSongLists(@PathVariable("userId") String userId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.selectSongLists(Integer.parseInt(userId)).toPageInfo();
    }

    //  通过id查询自己收藏的歌曲
    @RequestMapping("/colSongs/{userId}")
    @ResponseBody
    public Object selectSongs(@PathVariable("userId") String userId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.selectSongs(Integer.parseInt(userId)).toPageInfo();
    }

    //  通过id查询自己收藏的歌手
    @RequestMapping("/colSingers/{userId}")
    @ResponseBody
    public Object selectSingers(@PathVariable("userId") String userId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.selectSingers(Integer.parseInt(userId)).toPageInfo();
    }

    //  通过id查询自己收藏的专辑
    @RequestMapping("/colAlbums/{userId}")
    @ResponseBody
    public Object selectAlbums(@PathVariable("userId") String userId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.selectAlbums(Integer.parseInt(userId)).toPageInfo();
    }

    //  收藏歌曲
    @RequestMapping("/collectSongs")
    @ResponseBody
    public Object collectSongs(@RequestParam("userId")String userId,@RequestParam("songId")String songId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.insertSongsByCollected(Integer.parseInt(userId),Integer.parseInt(songId));
    }

    //  查询是否已收藏歌曲
    @RequestMapping("/selectIsCollectedSong")
    @ResponseBody
    public Object selectIsCollectedSong(@RequestParam("userId")String userId,@RequestParam("songId")String songId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.insertSongsByCollected(Integer.parseInt(userId),Integer.parseInt(songId));
    }

    //  取消收藏歌曲
    @RequestMapping("/delCollectedSongs")
    @ResponseBody
    public Object delCollectSongs(@RequestParam("userId")String userId,@RequestParam("songId")String songId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.delSongsByCollected(Integer.parseInt(userId),Integer.parseInt(songId));
    }

    //  收藏歌单
    @RequestMapping("/collectSongLists")
    @ResponseBody
    public Object collectSongLists(@RequestParam("userId") String userId,@RequestParam("songListId")String songListId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.insertSongListsByCollected(Integer.parseInt(userId),Integer.parseInt(songListId));
    }
    //  取消收藏歌单
    @RequestMapping("/delCollectSongLists")
    @ResponseBody
    public Object delCollectSongLists(@RequestParam("userId") String userId,@RequestParam("songListId")String songListId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.delSongListsByCollected(Integer.parseInt(userId),Integer.parseInt(songListId));
    }


    //  收藏歌手
    @RequestMapping("/collectSingers")
    @ResponseBody
    public Object collectSingers(@RequestParam("userId")String userId,@RequestParam("singerId")String singerId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.insertSingersByCollected(Integer.parseInt(userId),Integer.parseInt(singerId));
    }

    //  取消收藏歌手
    @RequestMapping("/delCollectSingers")
    @ResponseBody
    public Object delCollectSingers(@RequestParam("userId")String userId,@RequestParam("singerId")String singerId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.delSingersByCollected(Integer.parseInt(userId),Integer.parseInt(singerId));
    }

    //  收藏专辑
    @RequestMapping("/collectAlbums")
    @ResponseBody
    public Object collectAlbums(@RequestParam("userId")String userId,@RequestParam("albumId")String albumId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.insertSongListsByCollected(Integer.parseInt(userId),Integer.parseInt(albumId));
    }

    //  收藏专辑
    @RequestMapping("/delCollectAlbums")
    @ResponseBody
    public Object delCollectAlbums(@RequestParam("userId")String userId,@RequestParam("albumId")String albumId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.delAlbumsByCollected(Integer.parseInt(userId),Integer.parseInt(albumId));
    }
    //  添加歌曲到自己创建的歌单
    @RequestMapping("/addSongsToSongList")
    @ResponseBody
    public Object addSongsToSongList(@RequestParam("songListId")String songListId,@RequestParam("songId")String songId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.addSongsToSongList(Integer.parseInt(songListId),Integer.parseInt(songId));
    }

    //  添加歌曲到自己创建的歌单
    @RequestMapping("/delSongsToSongList")
    @ResponseBody
    public Object delSongsToSongList(@RequestParam("songListId")String songListId,@RequestParam("songId")String songId,@RequestParam(value = "pageNum",defaultValue = "1",required = false) String pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "1")String pageSize){
        return userService.delSongsToSongList(Integer.parseInt(songListId),Integer.parseInt(songId));
    }
    //查询所有用户
    @RequestMapping("/allUsers")
    @ResponseBody
    public Object selAllUser(){
//        List<User> users = userService.selAllUser();
//        for (User s : users) {
//            System.out.println(s);
//        }
//        String str = JSON.toJSONString(users);
//        return str;
        return userService.selAllUser();
    }

}
