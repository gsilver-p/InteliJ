package com.example.board.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class SessionInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("utf-8");
        //localhost/board/boardlist?p=1&size10
        log.info("★preHandler call uri={}",request.getRequestURI());
        log.info("★preHandler call queryString={}",request.getQueryString());
        HttpSession session = request.getSession();
        if(session.getAttribute("member") == null){
            log.info("인터셉트!!! 로그인 안했지롱");
            session.setAttribute("preUrl_login",request.getRequestURI()+'?'+request.getQueryString());
            response.sendRedirect("/member/login");        // redirect는 get만 요청이 가능해
            return false;  // Controller 진입을 막는거야
        }
        return true;  // Controller 계속 진행해~~
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        AsyncHandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//        System.out.println("view 직전에 호출");
//    }
}
