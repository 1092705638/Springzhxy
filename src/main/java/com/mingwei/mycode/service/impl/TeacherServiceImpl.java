package com.mingwei.mycode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingwei.mycode.mapper.TeacherMapper;
import com.mingwei.mycode.pojo.Admin;
import com.mingwei.mycode.pojo.LoginForm;
import com.mingwei.mycode.pojo.Student;
import com.mingwei.mycode.pojo.Teacher;
import com.mingwei.mycode.service.TeacherService;
import com.mingwei.mycode.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author Jacob
 * @create 2022-04-19 15:45
 */
@Service("teacherServiceImpl")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    /**
     * 提交的loginform与数据库的账户密码进行校验
     * MD5加密
     *
     * @param loginForm
     * @return
     */
    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Teacher teacher = baseMapper.selectOne(queryWrapper);

        return teacher;
    }

    /**
     * 根据id获取对象
     *
     * @param userId
     * @return
     */
    @Override
    public Teacher getTeacherById(Long userId) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        return baseMapper.selectOne(queryWrapper);
    }


    /**
     * 分页-查询
     *
     * @param page
     * @param teacher
     * @return
     */
    @Override
    public IPage getTeacherByContion(Page<Teacher> page, Teacher teacher) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        String name = teacher.getName();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        String clazzName = teacher.getClazzName();
        if (!StringUtils.isEmpty(clazzName)) {
            queryWrapper.like("clazz_name", clazzName);
        }
        Page<Teacher> selectPage = baseMapper.selectPage(page, queryWrapper);

        return selectPage;

    }


}
