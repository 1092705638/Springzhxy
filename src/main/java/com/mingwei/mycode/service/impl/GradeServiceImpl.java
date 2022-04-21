package com.mingwei.mycode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingwei.mycode.mapper.GradeMapper;
import com.mingwei.mycode.pojo.Grade;
import com.mingwei.mycode.service.GradeService;
import com.mingwei.mycode.util.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author Jacob
 * @create 2022-04-19 15:42
 */
@Service("gradeServiceImpl")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    /**
     * 实现查询功能
     * @param page1
     * @param gradeName
     * @return
     */
    @Override
    public IPage<Grade> getGradeByopr(Page<Grade> page1, String gradeName) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        //判断是否为空
        if (!StringUtils.isEmpty(gradeName)) {
            queryWrapper.like("name", gradeName);
        }
        //id排序-->逆序
        queryWrapper.orderByDesc("id");

        Page<Grade> selectPage = baseMapper.selectPage(page1, queryWrapper);
        return selectPage;
    }
}
