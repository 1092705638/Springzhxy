package com.mingwei.mycode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingwei.mycode.mapper.AdminMapper;
import com.mingwei.mycode.pojo.Admin;
import com.mingwei.mycode.pojo.LoginForm;
import com.mingwei.mycode.pojo.Student;
import com.mingwei.mycode.service.AdminService;
import com.mingwei.mycode.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author Jacob
 * @create 2022-04-19 15:35
 */
@Service("adminServiceImpl")
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    /**
     * 提交的loginform与数据库的账户密码进行校验
     *
     * @param loginForm
     * @return
     */
    @Override
    public Admin login(LoginForm loginForm) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Admin admin = baseMapper.selectOne(queryWrapper);

        return admin;
    }

    @Override
    public Admin getAdminById(Long userId) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage getAdminByContion(Page<Admin> page, String adminName) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(adminName)) {
            queryWrapper.like("name", adminName);
        }
        Page<Admin> selectPage = baseMapper.selectPage(page, queryWrapper);
        return selectPage;
    }
}
