package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import org.example.dao.administratorDAOImpl;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final administratorDAOImpl adminDao = new administratorDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        HttpSession session = req.getSession(false); // 使用 false 防止创建新会话

        // 首先判断是否是硬编码的管理员
        if ("admin".equals(username) && "admin".equals(password)) {
            if (session == null) {
                session = req.getSession(); // 创建会话
            }
            session.setAttribute("username", username);
            req.getRequestDispatcher("/index.html").forward(req, resp);
        } else {
            // 否则，通过数据库验证用户信息
            if (adminDao.CheckTheUser(username, password)) {
                if (session == null) {
                    session = req.getSession(); // 创建会话
                }
                session.setAttribute("username", username);
                req.getRequestDispatcher("/index.html").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/login-failure.html"); // 使用上下文路径重定向
            }
        }
    }
}
