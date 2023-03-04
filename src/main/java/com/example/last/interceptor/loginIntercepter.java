package com.example.last.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class loginIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws ServletException, IOException {
//        Object session1=request.getSession().getAttribute("sessionid");
//        Cookie[] cookies = request.getCookies();
//        Cookie usercookie;
//        for(Cookie cookie : cookies){
//            if(cookie.getName().equals("my_session_id")){
//                usercookie=cookie;
//                return true;
//            }
//        }
        HttpSession session=request.getSession();
        Object loginUser=session.getAttribute("useremail");
        if(loginUser!=null){
            return true;
        }
        request.setAttribute("msg","请先登录");
        request.getRequestDispatcher("/").forward(request,response);
//        response.sendRedirect("/");
        return false;
    }
}
