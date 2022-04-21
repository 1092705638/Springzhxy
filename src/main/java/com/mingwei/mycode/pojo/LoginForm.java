package com.mingwei.mycode.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jacob
 * @create 2022-04-19 15:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;
}
