package com.xiaoyu.interview.question.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description 自己实现 AutoWired 注解的功能
 * author 小雨
 * createTime 2021年11月21日 12:40:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoWired {



}
