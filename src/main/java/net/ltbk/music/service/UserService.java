package net.ltbk.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import net.ltbk.music.bean.User;
import net.ltbk.music.common.Result;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author liutao
 */
public interface UserService extends IService<User> {
    /**登录**/
    User login(User user);
    /**注册和更新**/
    boolean saveUser(User user);

    /**管理员通过用户id删除指定用户**/
    int delUser(int userid);

    Boolean delBatch(List<Integer> ids);

    /**点击显示个人信息等待修改**/
    User selectByUserId(int userid);

    //  查询所有用户
    Page<User> selectAll();

    // 用户注册重复检验
    User selectByAccount(@Param("account") String account);

    //多条件查询用户
    Page<User> selectUserByExample(User user, Date startDate, Date endDate);

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

    int selectAlbumStatus(@Param("userId") int userId, @Param("albumId") int albumId);

    int selectSongListStatus(@Param("userId") int userId, @Param("songListId") int songListId);

    int selectSongStatus(@Param("userId") int userId, @Param("songId") int songId);

    // 收藏歌曲
    int insertSongsByCollected(@Param("userId") int userId, @Param("songId") int songId);

    // 收藏歌手
    int insertSingersByCollected(@Param("userId") int userId, @Param("singerId") int singerId);

    // 收藏歌单
    int insertSongListsByCollected(@Param("userId") int userId, @Param("songListId") int songListId);

    // 收藏专辑
    int insertAlbumsByCollected(@Param("userId") int userId, @Param("albumId") int albumId);

    // 添加歌曲到自己创建的歌单
    int addSongsToSongList(@Param("songListId") int songListId, @Param("songId") int songId);

    // 取消收藏歌曲
    int delSongsByCollected(@Param("userId") int userId, @Param("songId") int songId);

    // 取消收藏歌手
    int delSingersByCollected(@Param("userId") int userId, @Param("singerId") int singerId);

    // 取消收藏歌单
    int delSongListsByCollected(@Param("userId") int userId, @Param("songListId") int songListId);

    // 取消收藏专辑
    int delAlbumsByCollected(@Param("userId") int userId, @Param("albumId") int albumId);

    // 删除歌曲从自己创建的歌单
    int delSongsToSongList(@Param("songListId") int songListId, @Param("songId") int songId);

    //  查询是否已收藏歌曲
    int selectIsCollectedSong(@Param("userId") int userId, @Param("songId") int songId);

    //查询所有用户
    List<User> selAllUser();


}
