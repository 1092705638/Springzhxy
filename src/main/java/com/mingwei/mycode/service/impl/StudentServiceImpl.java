package com.mingwei.mycode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingwei.mycode.mapper.StudentMapper;
import com.mingwei.mycode.pojo.Admin;
import com.mingwei.mycode.pojo.LoginForm;
import com.mingwei.mycode.pojo.Student;
import com.mingwei.mycode.pojo.Teacher;
import com.mingwei.mycode.service.StudentService;
import com.mingwei.mycode.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author Jacob
 * @create 2022-04-19 15:43
 */
@Service("studentServiceImpl")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    /**
     * 提交的loginform与数据库的账户密码进行校验
     *
     * @param loginForm
     * @return
     */
    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Student student = baseMapper.selectOne(queryWrapper);

        return student;
    }


    /**
     * 校验正确后浏览器端发来token，token中包含userid和type，根据id查询用户信息并返回
     *
     * @param userId
     * @return
     */
    @Override
    public Student getStudentById(Long userId) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage getStudentByContion(Page<Student> page, Student student) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        String name = student.getName();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        String clazzName = student.getClazzName();
        if (!StringUtils.isEmpty(clazzName)) {
            queryWrapper.like("clazz_name", clazzName);
        }
        Page<Student> selectPage = baseMapper.selectPage(page, queryWrapper);
        return selectPage;
    }
}
