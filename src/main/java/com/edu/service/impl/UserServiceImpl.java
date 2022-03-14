package com.edu.service.impl;

import com.edu.bean.User;
import com.edu.mapper.UserMapper;
import com.edu.service.UserService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
//登录
    public User login(User user) {
        return userMapper.selectByAccountAndPwd(user.getAccount(),user.getPwd());
    }
//注册
    public int register(User user) {
        int insert=0;
        User newuser = userMapper.selectByAccount(user.getAccount());
        System.out.println(newuser);
        if(newuser==null){
            insert = userMapper.insert(user);
        }else{
            return -1;
        }
        return insert;
    }
    //通过id删除用户
    public int delUser(int userid) {
        return userMapper.deleteByPrimaryKey(userid);
    }
    //按照指定模版修改用户信息
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKey(user);
    }
    //通过id查询个人信息
    public User selectByUserId(int userid) {
        return userMapper.selectByPrimaryKey(userid);
    }
    // 查询所有
    public Page<User> selectAll() {
        return userMapper.selectAll();
    }

    public User selectByAccount(String account) {
        return userMapper.selectByAccount(account);
    }

    //多条件显示所有用户
    public Page<User> selectUserByExample(User user, Date startDate, Date endDate) {
        return userMapper.selectUserByExample(user,startDate,endDate);
    }
    // 所有收藏的歌单
    public Page<User> selectSongLists(int userId) {
        return userMapper.selectSongLists(userId);
    }
    // 自己创建的歌单
    public Page<User> selectSongListByMe(int userId) {
        return userMapper.selectSongListByMe(userId);
    }
    // 所有收藏的歌手
    public Page<User> selectSingers(int userId) {
        return userMapper.selectSingers(userId);
    }
    // 所有收藏的歌曲
    public Page<User> selectSongs(int userId) {
        return userMapper.selectSongs(userId);
    }
    // 所有收藏的专辑
    public Page<User> selectAlbums(int userId) {
        return userMapper.selectAlbums(userId);
    }
    // 收藏歌曲
    public int insertSongsByCollected(int userId, int songId) {
        return userMapper.insertSongsByCollected(userId,songId);
    }
    // 收藏歌手
    public int insertSingersByCollected(int userId, int singerId) {
        return userMapper.insertSingersByCollected(userId,singerId);
    }
    // 收藏歌单
    public int insertSongListsByCollected(int userId, int songListId) {
        return userMapper.insertSongListsByCollected(userId,songListId);
    }
    // 收藏专辑
    public int insertAlbumsByCollected(int userId, int albumId) {
        return userMapper.insertAlbumsByCollected(userId,albumId);
    }
    // 给创建的歌歌单添加歌曲
    public int addSongsToSongList(int songListId, int songId) {
        return userMapper.addSongsToSongList(songListId,songId);
    }

    public int delSongsByCollected(int userId, int songId) {
        return userMapper.delSongsByCollected(userId,songId);
    }

    public int delSingersByCollected(int userId, int singerId) {
        return userMapper.delSingersByCollected(userId,singerId);
    }

    public int delSongListsByCollected(int userId, int songListId) {
        return userMapper.delSongListsByCollected(userId,songListId);
    }

    public int delAlbumsByCollected(int userId, int albumId) {
        return userMapper.delAlbumsByCollected(userId,albumId);
    }

    public int delSongsToSongList(int songListId, int songId) {
        return userMapper.delSongsToSongList(songListId,songId);
    }

    public int selectIsCollectedSong(int userId, int songId) {
        return userMapper.selectIsCollectedSong(userId,songId);
    }

    public List<User> selAllUser() {
        return userMapper.selAllUser();
    }


}
