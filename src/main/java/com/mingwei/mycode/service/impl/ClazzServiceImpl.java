package com.mingwei.mycode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingwei.mycode.mapper.ClazzMapper;
import com.mingwei.mycode.pojo.Clazz;
import com.mingwei.mycode.service.ClazzService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author Jacob
 * @create 2022-04-19 15:40
 */
@Service("clazzServiceImpl")
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Override
    public IPage getClazzContion(Page<Clazz> page, Clazz clazz) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        String gradeName = clazz.getGrade_name();
        if (!StringUtils.isEmpty(gradeName)) {
            queryWrapper.like("grade_name", gradeName);
        }

        String name = clazz.getName();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        queryWrapper.orderByDesc("id");
        Page<Clazz> selectPage = baseMapper.selectPage(page, queryWrapper);

        return selectPage;

    }
}
