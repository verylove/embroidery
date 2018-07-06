package cn.wind.auth.security.exception;

import cn.wind.common.res.ApiRes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: CustomAuthExceptionEntryPoint</p>
 * <p>Description: 自定义AuthExceptionEntryPoint用于tokan校验失败返回信息</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/7/2
 */
public class CustomAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), ApiRes.Custom().failure(401,authException.getMessage()).path(request.getServletPath()));
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}