package com.edu.mapper;

import com.edu.bean.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    User selectByPrimaryKey(Integer userId);

    Page<User> selectAll();

    int updateByPrimaryKey(User record);

    Page<User> selectUserByExample(@Param("user") User user, @Param("start") Date startDate, @Param("end") Date endDate);

    User selectByAccountAndPwd(@Param("account") String account, @Param("pwd") String pwd);

    User selectByAccount(@Param("account") String account);
    // 所有收藏的歌单
    Page<User> selectSongLists(int userId);
    // 自己创建的歌单
    Page<User> selectSongListByMe(int userId);
    // 所有收藏的歌手
    Page<User> selectSingers(int userId);
    // 所有收藏的歌曲
    Page<User> selectSongs(int userId);
    // 所有收藏的专辑
    Page<User> selectAlbums(int userId);
    // 收藏歌曲
    int insertSongsByCollected(@Param("userId")int userId,@Param("songId")int songId);
    // 收藏歌手
    int insertSingersByCollected(@Param("userId")int userId,@Param("singerId")int singerId);
    // 收藏歌单
    int insertSongListsByCollected(@Param("userId")int userId,@Param("songListId")int songListId);
    // 收藏专辑
    int insertAlbumsByCollected(@Param("userId")int userId,@Param("albumId")int albumId);
    // 添加歌曲到自己创建的歌单
    int addSongsToSongList(@Param("songListId")int songListId,@Param("songId")int songId);
    // 取消收藏歌曲
    int delSongsByCollected(@Param("userId")int userId,@Param("songId")int songId);
    // 取消收藏歌手
    int delSingersByCollected(@Param("userId")int userId,@Param("singerId")int singerId);
    // 取消收藏歌单
    int delSongListsByCollected(@Param("userId")int userId,@Param("songListId")int songListId);
    // 取消收藏专辑
    int delAlbumsByCollected(@Param("userId")int userId,@Param("albumId")int albumId);
    // 删除歌曲从自己创建的歌单
    int delSongsToSongList(@Param("songListId")int songListId,@Param("songId")int songId);
    //  查询是否已收藏歌曲
    int selectIsCollectedSong(@Param("userId")int userId,@Param("songId")int songId);
    //查询所有用户
    List<User> selAllUser();

}