package net.ltbk.music.common;

import lombok.extern.slf4j.Slf4j;
import net.ltbk.music.bean.User;
import net.ltbk.music.common.exception.ServiceException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Program: music
 * @ClassName LoginIntercepter
 * @Author: liutao
 * @Description:
 * @Create: 2023-10-18 23:04
 * @Version 1.0
 **/

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user= (User) session.getAttribute("user");
        String uri = request.getRequestURI();
        log.info("请求地址：{}",uri);
        log.info("登录用户：{}",user);
        if (user != null) {
            return true;
        }else {
            throw new ServiceException(Constants.CODE_NOT_FORBIDDEN,"用户已下线");
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
