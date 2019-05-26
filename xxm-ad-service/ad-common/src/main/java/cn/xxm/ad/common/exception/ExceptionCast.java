package cn.xxm.ad.common.exception;


import cn.xxm.ad.common.response.ResultCode;

/**
 * @author xxm
 * @create 2019-03-04 23:05
 */
public class ExceptionCast {
    //使用此静态方法抛出自定义异常
    public static void cast(ResultCode resultCode) {
        throw new AdException(resultCode);
    }
}