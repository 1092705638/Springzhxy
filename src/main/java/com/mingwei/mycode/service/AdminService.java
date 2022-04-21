package com.mingwei.mycode.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mingwei.mycode.pojo.Admin;
import com.mingwei.mycode.pojo.LoginForm;

/**
 * @author Jacob
 * @create 2022-04-19 15:34
 */
public interface AdminService extends IService<Admin> {
    Admin login(LoginForm loginForm);

    Admin getAdminById(Long userId);

    IPage getAdminByContion(Page<Admin> page, String adminName);
}
