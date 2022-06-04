package com.xiaoyu.interview.question.controller;


import com.xiaoyu.interview.question.annotation.AutoWired;
import com.xiaoyu.interview.question.service.UserService;
/**
 * Description 自己实现 AutoWired 注解的功能
 *
 * @author 小雨
 * createTime 2021年11月21日 10:42:00
 */
public class UserController {

    @AutoWired
    private UserService userService;


    public UserService getUserService() {
        return userService;
    }


}
