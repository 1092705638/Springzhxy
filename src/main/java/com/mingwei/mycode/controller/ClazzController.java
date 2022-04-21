package com.mingwei.mycode.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingwei.mycode.pojo.Clazz;
import com.mingwei.mycode.service.ClazzService;
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
@Api(tags = "班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {


    /**
     * 自动注入clazzService对象
     */
    @Autowired
    private ClazzService clazzService;

    /**
     * 获取学生全部信息
     * url：ms/clazzController/getClazzs
     */
    @ApiOperation("获取学生全部信息")
    @GetMapping("/getClazzs")
    public Result getClazzs() {
        List<Clazz> list = clazzService.list();
        return Result.ok(list);
    }

    /**
     * 删除学生信息
     * url：ms/clazzController/deleteClazz
     */
    @ApiOperation("删除学生信息")
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@ApiParam("获取JSON对象的删除id集合") @RequestBody List<Integer> list) {
        clazzService.removeByIds(list);
        return Result.ok();
    }


    /**
     * 添加或者修改学生信息
     * URL:sms/clazzController/saveOrUpdateClazz
     */

    @ApiOperation("添加或者修改学生信息")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@ApiParam("添加的具体信息") @RequestBody Clazz clazz) {
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    /**
     * URL:sms/clazzController/getClazzsByOpr/1/3?gradeName=&name
     * 分页多条件查询
     */
    @ApiOperation("分页多条件查询")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(
            @ApiParam("分页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("查询条件") Clazz clazz
    ) {
        //调用服务层完成分页多查询操作
        Page<Clazz> page = new Page<Clazz>(pageNo, pageSize);
        IPage iPage = clazzService.getClazzContion(page, clazz);
        return Result.ok(iPage);
    }
}
