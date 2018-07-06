package cn.wind.common.exception;


import cn.wind.common.res.ApiRes;
import cn.wind.common.utils.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.util.Set;

/**
 *
 * @author xukk
 * @date 2018/5/4
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @SneakyThrows
    public void handleResourceNotFoundException(HttpServletRequest request, HttpServletResponse response,ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage());
            break;
        }
        if (isJson(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(JsonUtil.toJson(ApiRes.Str().failure(HttpStatus.BAD_REQUEST.value(), ConstraintViolationException.class.getSimpleName(), strBuilder.toString()).path(request.getServletPath())));
        } else {
            request.setAttribute("error", strBuilder.toString());
            request.getRequestDispatcher("/error/400").forward(request, response);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    @SneakyThrows
    public void handleBindException(HttpServletRequest request, HttpServletResponse response,BindException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String message = String.format("%s:%s", field, error.getDefaultMessage());
        if (isJson(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(JsonUtil.toJson(ApiRes.Str().failure(HttpStatus.BAD_REQUEST.value(), BindException.class.getSimpleName(),message).path(request.getServletPath())));
        } else {
            request.setAttribute("error", message);
            request.getRequestDispatcher("/error/400").forward(request, response);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @SneakyThrows
    public void handleMissingServletRequestParameterException(HttpServletRequest request, HttpServletResponse response,MissingServletRequestParameterException e) {
        if (isJson(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(JsonUtil.toJson(ApiRes.Str().failure(HttpStatus.BAD_REQUEST.value(), BindException.class.getSimpleName(),e.getMessage()).path(request.getServletPath())));
        } else {
            request.setAttribute("error", "缺少请求参数,"+e.getMessage());
            request.getRequestDispatcher("/error/400").forward(request, response);
        }
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    @SneakyThrows
    public void handleValidationException(HttpServletRequest request, HttpServletResponse response, ValidationException e) {
        if (isJson(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(JsonUtil.toJson(ApiRes.Str().failure(HttpStatus.BAD_REQUEST.value(), ValidationException.class.getSimpleName(), e.getMessage()).path(request.getServletPath())));
        } else {
            request.setAttribute("error", "参数验证失败," + e.getMessage());
            request.getRequestDispatcher("/error/400").forward(request, response);
        }

    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @SneakyThrows
    public void handleHttpMessageNotReadableException(HttpServletRequest request, HttpServletResponse response, HttpMessageNotReadableException e) {

        if (isJson(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(JsonUtil.toJson(ApiRes.Str().failure(HttpStatus.BAD_REQUEST.value(), HttpMessageNotReadableException.class.getSimpleName(), e.getMessage()).path(request.getServletPath())));
        } else {
            request.setAttribute("error", "参数解析失败," + e.getMessage());
            request.getRequestDispatcher("/error/400").forward(request, response);
        }
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @SneakyThrows
    public void handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpServletResponse response, HttpRequestMethodNotSupportedException e) {
        if (isJson(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(JsonUtil.toJson(ApiRes.Str().failure(HttpStatus.METHOD_NOT_ALLOWED.value(), HttpRequestMethodNotSupportedException.class.getSimpleName(), e.getMessage()).path(request.getServletPath())));
        } else {
            request.setAttribute("error", "不支持当前请求方法," + e.getMessage());
            request.getRequestDispatcher("/error/405").forward(request, response);
        }
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @SneakyThrows
    public void handleHttpMediaTypeNotSupportedException(HttpServletRequest request, HttpServletResponse response, HttpMediaTypeNotSupportedException e) {
        if (isJson(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(JsonUtil.toJson(ApiRes.Str().failure(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), HttpMediaTypeNotSupportedException.class.getSimpleName(), e.getMessage()).path(request.getServletPath())));
        } else {
            request.setAttribute("error", "不支持当前媒体类型," + e.getMessage());
            request.getRequestDispatcher("/error/415").forward(request, response);
        }
    }

    /**
     * 404 - Not Found
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @SneakyThrows
    public void handleNoHandlerFoundException(HttpServletRequest request, HttpServletResponse response, NoHandlerFoundException e) {
        if (isJson(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(JsonUtil.toJson(ApiRes.Str().failure(HttpStatus.NOT_FOUND.value(), NoHandlerFoundException.class.getSimpleName(), e.getMessage()).path(request.getServletPath())));
        } else {
            request.setAttribute("error", "资源不存在," + e.getMessage());
            request.getRequestDispatcher("/error/404").forward(request, response);
        }

    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    @SneakyThrows
    public void handleNullPointerException(HttpServletRequest request, HttpServletResponse response, NullPointerException e) {
        log.error(e.getMessage(),e);
        if (isJson(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            ApiRes apiRes=ApiRes.Str().
                    failure(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            NullPointerException.class.getSimpleName(),e.getMessage()).path(request.getServletPath());
            response.getWriter().write(JsonUtil.toJson(apiRes));
        } else {
            request.setAttribute("error", "空指针异常," + e.getMessage());
            request.getRequestDispatcher("/error/500").forward(request, response);
        }
    }


    public boolean isJson(HttpServletRequest request) {
        //json格式的ajax请求
        if (request.getHeader("accept").toLowerCase().contains("application/json")
                || (request.getHeader("X-Requested-With") != null && request
                .getHeader("X-Requested-With")
                .contains("XMLHttpRequest"))) {
            return true;
        } else {
            return false;
        }
    }

}
