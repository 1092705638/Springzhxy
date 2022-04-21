package com.mingwei.mycode.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jacob
 * @create 2022-04-19 14:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_clazz")
public class Clazz {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    private String name;
    private int number;
    private String introducation;
    private String headmaster;
    private String email;
    private String telephone;
    private String grade_name;

}
