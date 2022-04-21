package com.mingwei.mycode.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jacob
 * @create 2022-04-19 15:10
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_teacher")
public class Teacher {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String tno;
    private String name;
    private String gender;
    private String password;
    private String email;
    private String telephone;
    private String address;
    private String portraitPath;
    private String clazzName;

}