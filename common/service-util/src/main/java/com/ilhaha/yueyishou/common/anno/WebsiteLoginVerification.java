package com.ilhaha.yueyishou.common.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author ilhaha
 * @Create 2024/8/18 10:15
 * @Version 1.0
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebsiteLoginVerification {
}
