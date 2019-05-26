package cn.xxm.ad.common.annotation;

import java.lang.annotation.*;

/**
 * @author xxm
 * @create 2019-05-26 22:07
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {
}
