package net.ltbk.music.mapper;

import net.ltbk.music.bean.Admin;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer superId);

    int insert(Admin record);

    Admin selectByPrimaryKey(Integer superId);

    Page<Admin> selectAll();

    int updateByPrimaryKey(Admin record);

    Admin selectByAccountAndPwd(@Param("account") String account, @Param("pwd") String pwd);

    Admin selectByAccount(@Param("account") String account);
}