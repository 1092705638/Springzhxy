package com.mingwei.mycode.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingwei.mycode.pojo.Admin;
import com.mingwei.mycode.pojo.Student;
import com.mingwei.mycode.pojo.Teacher;
import com.mingwei.mycode.service.AdminService;
import com.mingwei.mycode.service.StudentService;
import com.mingwei.mycode.util.MD5;
import com.mingwei.mycode.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author Jacob
 * @create 2022-04-19 15:55
 */
@Api(tags = "管理员控制器")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    /**
     * 自动注入属性studentService
     */
    @Autowired
    private AdminService adminService;


    /**
     * 分页-查询
     * url：alhost:9001/sms/adminController/getAllAdmin/1/3?adminName=12313
     */

    @ApiOperation("分页查询")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @ApiParam("分页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("查询条件") @PathParam("adminName") String adminName
    ) {
        Page<Admin> page = new Page<>(pageNo, pageSize);
        IPage iPage = adminService.getAdminByContion(page, adminName);

        return Result.ok(iPage);
    }

    /**
     * 添加管理员信息
     * <p>
     * url：p://localhost:9001/sms/adminController/saveOrUpdateAdmin
     */
    @ApiOperation("添加或者修改管理员信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@ApiParam("添加或者修改的信息") @RequestBody Admin admin) {
        //加密
        admin.setPassword(MD5.encrypt(admin.getPassword()));
        adminService.saveOrUpdate(admin);
        return Result.ok();

    }

    /**
     * url：localhost:9001/sms/adminController/deleteAdmin
     * 删除管理员
     */
    @ApiOperation("删除教师")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@ApiParam("获取管理员JSON对象的删除id集合") @RequestBody List<Integer> list) {

        adminService.removeByIds(list);
        return Result.ok();

    }

}
