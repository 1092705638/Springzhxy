package com.mingwei.mycode.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mingwei.mycode.pojo.LoginForm;
import com.mingwei.mycode.pojo.Teacher;

/**
 * @author Jacob
 * @create 2022-04-19 15:39
 */
public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);

    IPage getTeacherByContion(Page<Teacher> page, Teacher teacher);


}
