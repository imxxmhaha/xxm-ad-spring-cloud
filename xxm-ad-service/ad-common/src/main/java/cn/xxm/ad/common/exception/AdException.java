package cn.xxm.ad.common.exception;

import cn.xxm.ad.common.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xxm
 * @create 2019-05-26 22:47
 */
@Data
@NoArgsConstructor
public class AdException extends RuntimeException {
    private static final long serialVersionUID = 4061732202885069884L;
    /**
     * 异常码
     */
    private String code;
    /**
     * 异常描述
     */
    private String msg;

    public AdException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public AdException(ResultCode resultCode) {
        this.code=String.valueOf(resultCode.code());
        this.msg=resultCode.message();
    }


    public AdException(String message) {
        super(message);
    }

}
