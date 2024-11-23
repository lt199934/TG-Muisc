package net.ltbk.music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import net.ltbk.music.bean.Admin;
import net.ltbk.music.bean.User;
import net.ltbk.music.common.Constants;
import net.ltbk.music.common.exception.ServiceException;
import net.ltbk.music.mapper.UserMapper;
import net.ltbk.music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author taoge
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        return userMapper.selectByAccount(user.getAccount());
    }

    @Override
    public boolean saveUser(User user) {
        int i = 0;
        if (user.getUserId() == null) {
            User newuser = userMapper.selectByAccount(user.getAccount());
            log.info("数据库用户：{}", newuser);
            if (newuser == null) {
                try {
                    i = userMapper.add(user);
                } catch (Exception e) {
                    throw new ServiceException(Constants.CODE_ERROR, "业务异常");
                }
            } else {
                throw new ServiceException(Constants.CODE_ERROR, "用户重复");
            }
        } else {
            i = userMapper.updateByPrimaryKey(user);
        }
        return i != 0;
    }

    //注册
    public int register(User user) {
        int insert = 0;
        User newuser = userMapper.selectByAccount(user.getAccount());
        log.info("数据库用户：{}", newuser);
        if (newuser == null) {
            insert = userMapper.add(user);
        } else {
            return -1;
        }
        return insert;
    }

    //通过id删除用户
    @Override
    public int delUser(int userid) {
        return userMapper.deleteByPrimaryKey(userid);
    }

    @Override
    public Boolean delBatch(List<Integer> ids) {
        return userMapper.delBatchByIds(ids) == 1;
    }

    //通过id查询个人信息
    @Override
    public User selectByUserId(int userid) {
        return userMapper.selectByPrimaryKey(userid);
    }

    // 查询所有
    @Override
    public Page<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public User selectByAccount(String account) {
        return userMapper.selectByAccount(account);
    }

    //多条件显示所有用户
    @Override
    public Page<User> selectUserByExample(User user, Date startDate, Date endDate) {
        return userMapper.selectUserByExample(user, startDate, endDate);
    }

    // 所有收藏的歌单
    @Override
    public Page<User> selectSongLists(int userId) {
        return userMapper.selectSongLists(userId);
    }

    // 自己创建的歌单
    @Override
    public Page<User> selectSongListByMe(int userId) {
        return userMapper.selectSongListByMe(userId);
    }

    // 所有收藏的歌手
    @Override
    public Page<User> selectSingers(int userId) {
        return userMapper.selectSingers(userId);
    }

    // 所有收藏的歌曲
    @Override
    public Page<User> selectSongs(int userId) {
        return userMapper.selectSongs(userId);
    }

    // 所有收藏的专辑
    @Override
    public Page<User> selectAlbums(int userId) {
        return userMapper.selectAlbums(userId);
    }

    @Override
    public int selectAlbumStatus(int userId, int albumId) {
        return userMapper.selectAlbumStatus(userId, albumId);
    }

    @Override
    public int selectSongListStatus(int userId, int songListId) {
        return userMapper.selectSongListStatus(userId, songListId);
    }

    @Override
    public int selectSongStatus(int userId, int songId) {
        return userMapper.selectSongStatus(userId, songId);
    }

    // 收藏歌曲
    @Override
    public int insertSongsByCollected(int userId, int songId) {
        return userMapper.insertSongsByCollected(userId, songId);
    }

    // 收藏歌手
    @Override
    public int insertSingersByCollected(int userId, int singerId) {
        return userMapper.insertSingersByCollected(userId, singerId);
    }

    // 收藏歌单
    @Override
    public int insertSongListsByCollected(int userId, int songListId) {
        return userMapper.insertSongListsByCollected(userId, songListId);
    }

    // 收藏专辑
    @Override
    public int insertAlbumsByCollected(int userId, int albumId) {
        return userMapper.insertAlbumsByCollected(userId, albumId);
    }

    // 给创建的歌歌单添加歌曲
    @Override
    public int addSongsToSongList(int songListId, int songId) {
        return userMapper.selectSongIsAddToSongList(songListId, songId) == 0 ? userMapper.addSongsToSongList(songListId, songId) : -1;
    }

    @Override
    public int delSongsByCollected(int userId, int songId) {
        return userMapper.delSongsByCollected(userId, songId);
    }

    @Override
    public int delSingersByCollected(int userId, int singerId) {
        return userMapper.delSingersByCollected(userId, singerId);
    }

    @Override
    public int delSongListsByCollected(int userId, int songListId) {
        return userMapper.delSongListsByCollected(userId, songListId);
    }

    @Override
    public int delAlbumsByCollected(int userId, int albumId) {
        return userMapper.delAlbumsByCollected(userId, albumId);
    }

    @Override
    public int delSongsToSongList(int songListId, int songId) {
        return userMapper.delSongsToSongList(songListId, songId);
    }

    @Override
    public int selectIsCollectedSong(int userId, int songId) {
        return userMapper.selectIsCollectedSong(userId, songId);
    }

    @Override
    public List<User> selAllUser() {
        return userMapper.selAllUser();
    }

    @Override
    public Admin admin(Admin admin) {
        return userMapper.admin(admin);
    }

}
