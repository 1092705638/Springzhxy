package com.mingwei.mycode.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jacob
 * @create 2022-04-19 14:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_grade")
public class Grade {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    private String name;
    private String manager;
    private String email;
    private String telephone;
    private String introducation;

}
