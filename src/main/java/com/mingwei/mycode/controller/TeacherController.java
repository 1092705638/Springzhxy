package com.mingwei.mycode.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingwei.mycode.pojo.Teacher;
import com.mingwei.mycode.service.TeacherService;
import com.mingwei.mycode.util.MD5;
import com.mingwei.mycode.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jacob
 * @create 2022-04-19 15:55
 */
@Api(tags = "教师控制器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {


    /**
     * 自动注入属性teacherService
     */
    @Autowired
    private TeacherService teacherService;

    /**
     * url：sms/teacherController/saveOrUpdateTeacher
     * 添加或者修改教师
     */
    @ApiOperation("添加教师")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@ApiParam("添加或者修改的信息") @RequestBody Teacher teacher) {
        //加密
        teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        teacherService.saveOrUpdate(teacher);
        return Result.ok();

    }

    /**
     * url：ms/teacherController/deleteTeacher
     * 删除教师
     */
    @ApiOperation("删除教师")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@ApiParam("获取教师JSON对象的删除id集合") @RequestBody List<Integer> list) {

        teacherService.removeByIds(list);
        return Result.ok();

    }

    /**
     * 教师信息分页查询
     * url：ocalhost:9001/sms/teacherController/getTeachers/1/3?name=&clazzName=
     */
    @ApiOperation("教师信息分页查询")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")

    public Result getTeachers(
            @ApiParam("分页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("查询条件") Teacher teacher
    ) {
        Page<Teacher> page = new Page<Teacher>(pageNo, pageSize);
        IPage iPage = teacherService.getTeacherByContion(page, teacher);

        return Result.ok(iPage);
    }
}
