package net.ltbk.music.service;

import net.ltbk.music.bean.Admin;
import com.github.pagehelper.Page;

public interface AdminService {
    //登录
    Admin login(Admin admin);

    //管理员通过用户id删除指定用户
    int delAdmin(int superId);

    //个人信息修改
    int updateAdmin(Admin admin);

    //点击显示个人信息等待修改
    Admin selectByAdminId(int superId);

    //  查询所有管理员
    Page<Admin> selectAll();
}
