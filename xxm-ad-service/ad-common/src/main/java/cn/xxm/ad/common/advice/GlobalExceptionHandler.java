package cn.xxm.ad.common.advice;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import cn.xxm.ad.common.exception.AdException;
import cn.xxm.ad.common.response.ExceptionCodeEum;

import cn.xxm.ad.common.utils.StringUtil;
import cn.xxm.ad.common.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler
	@ResponseBody
	public Object handleException(Exception e,HttpServletRequest req){
		CommonResponse returnMsg = new CommonResponse();
		// 业务异常
		if(e instanceof AdException){
			returnMsg.setCode(((AdException) e).getCode());
			returnMsg.setMessage(((AdException) e).getMsg());
		}else{// 系统异常
			returnMsg.setCode(ExceptionCodeEum.SYSTEM_EXCEPTION.getCode());
			returnMsg.setMessage(ExceptionCodeEum.SYSTEM_EXCEPTION.getMsg());
		}

		// 判断响应类型
		String contentTypeHeader = req.getHeader("Content-Type");
		String acceptHeader = req.getHeader("Accept");
		String xRequestedWith = req.getHeader("X-Requested-With");
		if ((contentTypeHeader != null && contentTypeHeader.contains("application/json"))
				|| (acceptHeader != null && acceptHeader.contains("application/json"))
				|| "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {
			log.error("====GlobalExceptionHandler.handleException===ajax异常====msg:"+e.getMessage());
			log.error("====GlobalExceptionHandler.handleException===ajax异常====url:"+getCompleteUrl(req));
			log.error("",e);
			return returnMsg;
		} else {
			log.error("====GlobalExceptionHandler.handleException=======msg:"+ e.getMessage());
			log.error("====GlobalExceptionHandler.handleException=======url:"+getCompleteUrl(req));
			log.error("",e);
			// 对于系统异常直接抛出404错误页面
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("common/404");
			return modelAndView;
		}
	}

	private String getCompleteUrl(HttpServletRequest request) {
		String returnUrl = request.getRequestURL().toString();
		// 请求参数
		String queryString = request.getQueryString();
		returnUrl = StringUtil.isEmpty(queryString)?returnUrl:(returnUrl+"?"+queryString);
		return returnUrl;
	}
	

}