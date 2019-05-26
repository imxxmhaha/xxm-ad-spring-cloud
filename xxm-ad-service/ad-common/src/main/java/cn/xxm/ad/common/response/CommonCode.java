package cn.xxm.ad.common.response;


import lombok.ToString;

/**
 * @Author: xxm.
 * @Description:
 * @Date:Created in 2019/3/27 13:33.
 * @Modified By:
 */

@ToString
public enum CommonCode implements ResultCode{
    SUCCESS(true,10000,"操作成功！"),
    FAIL(false,11111,"操作失败！"),
    UNAUTHENTICATED(false,10001,"此操作需要登陆系统！"),
    UNAUTHORISE(false,10002,"权限不足，无权操作！"),
    INVALID_PARAM(false,10003,"非法参数！"),
    QUERY_DATA_ERROR(false,10004,"查询数据异常"),
    URLENCODER_ERROR(false,10005,"URLEncoder.decode转换异常"),
    ILLEGAL_OPERATE_DELETE_SELT(false,10006,"您不能删除自己"),
    ILLEGAL_OPERATE_NOT_OWNER(false,10007,"您不是设备主人,非法操作"),
    ILLEGAL_OPERATE_TRANSFER_SELT(false,10008,"您不转移设备主人给自己"),
    ILLEGAL_APPLY_NOT_MEMBER(false,10009,"您不是设备成员,禁止访问"),
    RSA_ENCRYPT_ERROR(false,10010,"RSA加密异常"),
    USER_DATA_ERROR(false,10011,"用户数据异常"),
    SERVER_IMAGE_ADDRESS_ERROR(false,10012,"配置的fastDFS地址异常"),
    USERDEVICE_QUERY_DATA_ERROR(false,10013,"条件查询UserDevice异常"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！");
    //    private static ImmutableMap<Integer, CommonCode> codes ;
    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private CommonCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }
    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }


}