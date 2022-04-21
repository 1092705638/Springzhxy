package com.mingwei.mycode.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mingwei.mycode.pojo.Clazz;

/**
 * @author Jacob
 * @create 2022-04-19 15:37
 */
public interface ClazzService extends IService<Clazz> {
    IPage getClazzContion(Page<Clazz> page, Clazz clazz);
}
