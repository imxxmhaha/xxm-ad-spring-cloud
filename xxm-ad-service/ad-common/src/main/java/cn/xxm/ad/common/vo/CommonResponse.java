package cn.xxm.ad.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xxm
 * @create 2019-05-26 21:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {
    private static final long serialVersionUID = -8469420879227883186L;

    private String code = "001000";
    private String message;
    private T data;

    public CommonResponse(String code,String message){
        this.code = code;
        this.message = message;
    }

    public static <T> CommonResponse ok(T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setMessage("success");
        commonResponse.setData(data);
        return commonResponse;
    }

    public static <T> CommonResponse ok() {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setMessage("success");
        return commonResponse;
    }

    public static <T> CommonResponse error(String msg) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setMessage(msg);
        commonResponse.setCode("9999");
        return commonResponse;
    }

}
