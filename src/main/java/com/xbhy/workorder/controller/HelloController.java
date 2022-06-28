package com.xbhy.workorder.controller;

import io.swagger.annotations.ApiOperation;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/lkq")
public class HelloController {

    @ApiOperation("测试接口")
    @GetMapping(value = "/hello")
    public String greet() {
        return "Hello lkq!";
    }

    @GetMapping("/test")
    public String test() {
        String password = "123456";
        // 加密
        String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(encodedPassword);

        // 使用正确密码验证密码是否正确
        boolean flag = BCrypt.checkpw(password, encodedPassword);
        System.out.println(flag);

        // 使用错误密码验证密码是否正确
        flag = BCrypt.checkpw("111222", encodedPassword);
        System.out.println(flag);

        System.out.println("-------------------------------------------");
        return "test success";
    }

}
