package cn.tedu.csmall.passport.ex.handler;

import cn.tedu.csmall.passport.ex.ServiceException;
import cn.tedu.csmall.passport.web.JsonResult;
import cn.tedu.csmall.passport.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
        log.debug("创建统一处理异常的类：GlobalExceptionHandler");
    }

    @ExceptionHandler
    public JsonResult<Void> handleServiceException(ServiceException e) {
        log.debug("处理ServiceException：{}", e.getMessage());
        return JsonResult.fail(e);
    }

    @ExceptionHandler
    public JsonResult<Void> handleBindException(BindException e) {
        log.debug("处理BindException：{}", e.getMessage());

        StringBuilder stringBuilder = new StringBuilder();
        List<FieldError> fieldErrors = e.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String message = fieldError.getDefaultMessage();
            stringBuilder.append(message);
        }

        return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST, stringBuilder.toString());
    }

    @ExceptionHandler
    public JsonResult<Void> handleThrowable(Throwable e) {
        log.debug("处理Throwable");
        e.printStackTrace();
        String message = "程序运行过程中出现意外错误，请联系系统管理员！";
        return JsonResult.fail(ServiceCode.ERR_INTERNAL_SERVER_ERROR, message);
    }

}