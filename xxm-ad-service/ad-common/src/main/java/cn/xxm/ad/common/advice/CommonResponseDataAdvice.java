package cn.xxm.ad.common.advice;

import cn.xxm.ad.common.annotation.IgnoreResponseAdvice;
import cn.xxm.ad.common.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author xxm
 * @create 2019-05-26 22:04
 */
@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 响应是否拦截
     * 1.根据方法参数MethodParameter  判断
     * 2.根据类converterType  判断
     *
     * @param methodParameter
     * @param converterType
     * @return
     */
    @Override
    @SuppressWarnings("all")
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        // 如果类被IgnoreResponseAdvice注解声明,那么就不拦截
        if (methodParameter.getDeclaringClass().isAnnotationPresent(
                IgnoreResponseAdvice.class
        )) {
            return false;
        }
        // 如果方法被IgnoreResponseAdvice注解声明,那么就不拦截
        if (methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        return true;
    }

    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        CommonResponse<Object> commonResponse = new CommonResponse<>("001000","");
        if(null == body){
            return commonResponse;
        } else if (body instanceof CommonResponse){
            commonResponse = (CommonResponse<Object>) body;
        } else{
            commonResponse.setData(body);
        }

        return commonResponse;
    }
}
