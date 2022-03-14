package com.edu.service.impl;

import com.edu.bean.Admin;
import com.edu.mapper.AdminMapper;
import com.edu.service.AdminService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
//登录
    public Admin login(Admin admin) {
        return adminMapper.selectByAccountAndPwd(admin.getAccount(),admin.getPwd());
    }
    public int delAdmin(int superId) {
        return  adminMapper.deleteByPrimaryKey(superId);
    }

    public int updateAdmin(Admin admin) {
        return adminMapper.updateByPrimaryKey(admin);
    }

    public Admin selectByAdminId(int superId) {
        return adminMapper.selectByPrimaryKey(superId);
    }

    public Page<Admin> selectAll() {
        return adminMapper.selectAll();
    }


}
