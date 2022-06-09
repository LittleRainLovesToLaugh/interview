package com.xiaoyu.interview.question.annotation;


import com.xiaoyu.interview.question.annotation.AutoWired;
import com.xiaoyu.interview.question.controller.UserController;

import java.util.stream.Stream;

/**
 * Description AutoWired 注解的功能实现
 *
 * @author 小雨
 * createTime 2021年11月21日 19:45:00
 */
public class AutoWiredTest {

    public static void main(String[] args) {
        UserController userController = new UserController();
        Class<? extends UserController> clazz = userController.getClass();
        //  获取所有的属性值
        Stream.of(clazz.getDeclaredFields()).forEach(field -> {
            // 如果UserController的字段带有AutoWired注解，就把对应的对象new出来并set进去
            AutoWired annotation = field.getAnnotation(AutoWired.class);
            if (annotation != null) {
                field.setAccessible(true);
                try {
                    // Object o2 = type.newInstance();
                    // Object o =  UserService.class.getDeclaredConstructor().newInstance();
                    // 创建对象
                    Object o = field.getType().newInstance();
                    // 对象赋值
                    field.set(userController, o);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(userController.getUserService());


    }

}
