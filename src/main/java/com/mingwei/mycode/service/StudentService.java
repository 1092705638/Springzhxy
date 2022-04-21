package com.mingwei.mycode.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mingwei.mycode.pojo.LoginForm;
import com.mingwei.mycode.pojo.Student;

/**
 * @author Jacob
 * @create 2022-04-19 15:38
 */
public interface StudentService extends IService<Student> {
    Student login(LoginForm loginForm);

    Student getStudentById(Long userId);

    IPage getStudentByContion(Page<Student> page, Student student);
}
