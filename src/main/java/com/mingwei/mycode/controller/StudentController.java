package com.mingwei.mycode.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingwei.mycode.pojo.Student;
import com.mingwei.mycode.pojo.Teacher;
import com.mingwei.mycode.service.StudentService;
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
@Api(tags = "学生管理控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    /**
     * 自动注入属性studentService
     */
    @Autowired
    private StudentService studentService;


    /**
     * 分页-查询
     * url：/studentController/getStudentByOpr/1/3?name=123&clazzName=%EF%AD
     */
    @ApiOperation("分页-查询")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
            @ApiParam("分页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("查询条件") Student student
    ) {
        Page<Student> page = new Page<>(pageNo, pageSize);
        IPage iPage = studentService.getStudentByContion(page, student);

        return Result.ok(iPage);
    }

    /**
     * url：alhost:9001/sms/studentController/addOrUpdateStudent
     * 添加或者修改学生信息
     */
    @ApiOperation("添加或者修改学生的信息")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@ApiParam("添加或者修改学生的信息") @RequestBody Student student) {
        //加密
        student.setPassword(MD5.encrypt(student.getPassword()));
        studentService.saveOrUpdate(student);

        return Result.ok();
    }

    /**
     * url：ocalhost:9001/sms/studentController/delStudentById
     * 删除功能
     */
    @ApiOperation("删除学生")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@ApiParam("删除参数的集合") @RequestBody List<Integer> list) {
        studentService.removeByIds(list);
        return Result.ok();

    }

}
