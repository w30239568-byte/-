package com.example.activitydemo.basecommon;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.List;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return AjaxResult.failed(ErrorEnum.PARAMS_VALID_ERROR.getCode(), firstFieldError(ex.getBindingResult().getFieldErrors(), "参数校验失败"));
    }

    @ExceptionHandler(BindException.class)
    public AjaxResult<Object> handleBindException(BindException ex) {
        return AjaxResult.failed(ErrorEnum.PARAMS_VALID_ERROR.getCode(), firstFieldError(ex.getBindingResult().getFieldErrors(), "参数校验失败"));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public AjaxResult<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Iterator<ConstraintViolation<?>> iterator = ex.getConstraintViolations().iterator();
        String msg = iterator.hasNext() ? iterator.next().getMessage() : "参数校验失败";
        return AjaxResult.failed(ErrorEnum.PARAMS_VALID_ERROR.getCode(), msg);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public AjaxResult<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String msg = ex.getName() + "参数类型错误";
        return AjaxResult.failed(ErrorEnum.PARAMS_TYPE_ERROR.getCode(), msg);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public AjaxResult<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        String msg = ex.getParameterName() + "不能为空";
        return AjaxResult.failed(ErrorEnum.PARAMS_TYPE_ERROR.getCode(), msg);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public AjaxResult<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return AjaxResult.failed(ErrorEnum.PARAMS_TYPE_ERROR.getCode(), "请求体格式错误");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public AjaxResult<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        if ("重复提交".equals(ex.getMessage())) {
            return AjaxResult.failed(ErrorEnum.REPEAT_COMMIT.getCode(), ErrorEnum.REPEAT_COMMIT.getMsg());
        }
        return AjaxResult.failed(ErrorEnum.PARAMS_VALID_ERROR.getCode(), ex.getMessage());
    }

    private String firstFieldError(List<FieldError> fieldErrors, String defaultMsg) {
        return (fieldErrors == null || fieldErrors.isEmpty()) ? defaultMsg : fieldErrors.get(0).getDefaultMessage();
    }
}
