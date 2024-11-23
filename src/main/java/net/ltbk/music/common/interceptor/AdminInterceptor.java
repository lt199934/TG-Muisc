package net.ltbk.music.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import net.ltbk.music.bean.User;
import net.ltbk.music.common.Constants;
import net.ltbk.music.common.exception.ServiceException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Program: TG-Music
 * @ClassName AdminInterceptor
 * @Author: liutao
 * @Description: 管理员登录校验
 * @Create: 2024-03-13 16:39
 * @Version 1.0
 **/
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String uri = request.getRequestURI();
        log.info("请求地址：{}", uri);
        log.info("登录用户：{}", user);
        if ("管理员".equals(user.getType())) {
            log.info("通过");
            return true;
        } else {
            throw new ServiceException(Constants.CODE_NOT_AUTHORIZED, "无权限访问！！");
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
