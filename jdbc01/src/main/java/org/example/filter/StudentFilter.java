//package org.example.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//
///*
//    保护学生管理系统
// */
//
//@WebFilter(urlPatterns = "/*")
//public class StudentFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // 初始化代码（如果需要）
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//            throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        HttpSession session = request.getSession(false);
//        String uri = request.getRequestURI();
//
//        if (uri.equals("/login.html") || uri.equals("/login") || uri.contains("static/") || uri.equals("/login-failure.html") || uri.equals("/register.html") || uri.equals("/register")) {
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }
//
//        if (session != null && session.getAttribute("username") != null) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else {
//            response.sendRedirect(request.getContextPath() + "/login.html");
//        }
//    }
//
//    @Override
//    public void destroy() {
//        // 清理代码（如果需要）
//    }
//}
