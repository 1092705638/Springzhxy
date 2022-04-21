package com.mingwei.mycode.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mingwei.mycode.pojo.Admin;
import com.mingwei.mycode.pojo.LoginForm;
import com.mingwei.mycode.pojo.Student;
import com.mingwei.mycode.pojo.Teacher;
import com.mingwei.mycode.service.AdminService;
import com.mingwei.mycode.service.StudentService;
import com.mingwei.mycode.service.TeacherService;
import com.mingwei.mycode.util.*;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Jacob
 * @create 2022-04-19 15:58
 */
@Api(tags = "系统控制器")
@RestController
@RequestMapping("/sms/system")
public class SystemController {


    /**
     * 注入实体类Admin，Student，Teacher
     */
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;


    /**
     * 修改用户密码
     * updatePwd/12356/admin
     */
    @ApiOperation("修改用户密码")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(
            @ApiParam("token口令") @RequestHeader("token") String token,
            @ApiParam("旧密码") @PathVariable("oldPwd") String oldPwd,
            @ApiParam("新密码") @PathVariable("newPwd") String newPwd
    ) {
        //判断token是否过期
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.fail().message("toke已过期，请刷新后重试");
        }

        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);

        switch (userType) {
            case 1:
                QueryWrapper<Admin> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("password", oldPwd);
                queryWrapper1.eq("id", userId.intValue());

                Admin admin = adminService.getOne(queryWrapper1);
                if (admin != null) {
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                } else {
                    return Result.fail().message("原密码错误！");
                }
                break;

            case 2:
                QueryWrapper<Student> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("password", oldPwd);
                queryWrapper2.eq("id", userId.intValue());

                Student student = studentService.getOne(queryWrapper2);
                if (student != null) {
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                } else {
                    return Result.fail().message("原密码错误！");
                }
                break;
            case 3:
                QueryWrapper<Teacher> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.eq("password", oldPwd);
                queryWrapper3.eq("id", userId.intValue());

                Teacher teacher = teacherService.getOne(queryWrapper3);
                if (teacher != null) {
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                } else {
                    return Result.fail().message("原密码错误！");
                }
                break;
        }
        return Result.ok();
    }


    /**
     * url:sms/system/headerImgUpload
     */
    @ApiOperation("文件统一上传入口")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(@ApiParam("头像文件") @RequestPart("multipartFile") MultipartFile multipartFile
    ) {
        //去重命名UUID
        String UUID = java.util.UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();//获取上传文件名
        int i = originalFilename.lastIndexOf(".");//获取索引
        String newFileName = UUID + originalFilename.substring(i);//剪切-拼接

        //保存文件
        String portraitPath = "D:\\soft\\mycode\\target\\classes\\public\\upload\\" + newFileName;
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //响应图片路径
        String path = "upload/" + newFileName;
        return Result.ok(path);
    }

    /**
     * 获取客户端getinfo请求发来的token,返回usertype和userObject
     */
    @GetMapping("/getInfo")
    public Result getInfo(@RequestHeader("token") String token) {
        boolean expiration = JwtHelper.isExpiration(token);
        //判断token有没有过期
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        Integer userType = JwtHelper.getUserType(token);
        Long userId = JwtHelper.getUserId(token);

        Map<String, Object> map = new LinkedHashMap<>();
        switch (userType) {
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType", 1);
                map.put("user", admin);
                break;
            case 2:
                Student student = studentService.getStudentById(userId);
                map.put("userType", 2);
                map.put("user", student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType", 3);
                map.put("user", teacher);
                break;
        }
        return Result.ok(map);
    }

    /**
     * 登录校验
     *
     * @param loginForm
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        //1.验证码校验
        //1.1获取sesiong域中的验证码
        HttpSession session = request.getSession();
        String verifiCode = (String) session.getAttribute("VerifiCode");
        //1.2获取请求体发送过来的验证码
        String verifiCode1 = loginForm.getVerifiCode();
        //1.3判断输入不能为空且错误提示
        if ("".equalsIgnoreCase(verifiCode) || null == verifiCode) {
            return Result.fail().message("验证码不能为空，刷新重试！");
        }
        if (!verifiCode.equalsIgnoreCase(verifiCode1)) {
            return Result.fail().message("验证码错误，刷新重试！");
        }
        //1.4移除sesion的共享数据
        session.removeAttribute("VerifiCode");


        //准备一个Map存放响应数据
        Map<String, Object> map = new LinkedHashMap<>();
        switch (loginForm.getUserType()) {
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if (null != admin) {
                        //用户的类型和用户的id转换为一个密文，以token的形式给客户端发送
                        String token = JwtHelper.createToken(admin.getId().longValue(), 1);
                        map.put("token", token);
                    } else {
                        throw new RuntimeException("用户名或者密码错误，请重新输入！");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }

            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if (null != student) {
                        //用户的类型和用户的id转换为一个密文，以token的形式给客户端发送
                        String token = JwtHelper.createToken(student.getId().longValue(), 2);
                        map.put("token", token);
                    } else {
                        throw new RuntimeException("用户名或者密码错误，请重新输入！");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if (null != teacher) {
                        //用户的类型和用户的id转换为一个密文，以token的形式给客户端发送
                        String token = JwtHelper.createToken(teacher.getId().longValue(), 3);
                        map.put("token", token);
                    } else {
                        throw new RuntimeException("用户名或者密码错误，请重新输入！");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }


        return Result.fail().message("查无此用户");


    }

    /**
     * 验证码实现方法
     *
     * @param request
     * @param response
     */
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        //1.获取验证码图片
        BufferedImage image = CreateVerifiCodeImage.getVerifiCodeImage();
        //2.获取验证码
        String s = new String(CreateVerifiCodeImage.getVerifiCode());
        //3.把验证码放入sesion域中
        HttpSession session = request.getSession();
        session.setAttribute("VerifiCode", s);
        //4.把图片通过响应报文响应给浏览器
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            //Imal工具输出流(图片，图片格式，输出流)
            ImageIO.write(image, "JPEG", outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
