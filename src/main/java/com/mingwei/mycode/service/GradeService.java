package com.mingwei.mycode.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mingwei.mycode.pojo.Grade;

/**
 * @author Jacob
 * @create 2022-04-19 15:38
 */
public interface GradeService extends IService<Grade> {
    IPage<Grade> getGradeByopr(Page<Grade> page1, String gradeName);
}
