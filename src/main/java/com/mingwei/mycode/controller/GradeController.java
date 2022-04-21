package com.mingwei.mycode.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingwei.mycode.pojo.Grade;
import com.mingwei.mycode.service.GradeService;
import com.mingwei.mycode.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Api(tags = "年纪控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    /**
     * 注入GradeService
     */
    @Autowired
    private GradeService gradeService;

    /**
     * URL:/sms/gradeController/getGrades
     * 获取全部年纪信息
     */
    @ApiOperation("获取全部年纪信息")
    @GetMapping("/getGrades")
    public Result getGrades() {

        List<Grade> list = gradeService.list();
        return Result.ok(list);
    }

    /**
     * URL:/sms/gradeController/deleteGrade
     * 删除年纪
     */
    @ApiOperation("删除用户")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@ApiParam("删除的Json对象的grade集合") @RequestBody List<Integer> list) {
        //调用服务层完成删除操作
        gradeService.removeByIds(list);
        return Result.ok();
    }

    /**
     * 添加或者更新年纪信息
     * URL:sms/gradeController/saveOrUpdateGrade
     *
     * @return
     */
    @ApiOperation("添加或者修改用户")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam("添加或者修改的形参（实体类接收）") @RequestBody Grade grade) {//接收的JSON对象的参用注解解析
        //1.接收参数
        //2.调用服务层方法完成添加或者更新操作
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }


    /**
     * URL:http://localhost:9001/sms/gradeController/getGrades/1/3?gradeName=3
     * 分页-条件查询
     *
     * @param pageNo
     * @param pageSize
     * @param gradeName
     * @return
     */
    @ApiOperation("模糊查询-分页操作")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(
            @ApiParam("分页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("查询条件") @PathParam("gradeName") String gradeName
    ) {
        //分页 查询条件
        Page<Grade> page1 = new Page<>(pageNo, pageSize);
        //通过服务层完成封装
        IPage<Grade> IPage = gradeService.getGradeByopr(page1, gradeName);

        return Result.ok(IPage);
    }
}
