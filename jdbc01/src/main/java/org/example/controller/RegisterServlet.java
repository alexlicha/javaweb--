package org.example.controller;

import org.example.dao.administratorDAOImpl;
import org.example.entity.administrator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final administratorDAOImpl adminDAO = new administratorDAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        System.out.println(username + " " + password + " " + email);

        administrator admin = new administrator(username, password, email);
        adminDAO.add(admin);

        response.sendRedirect("login.html");
    }
}
