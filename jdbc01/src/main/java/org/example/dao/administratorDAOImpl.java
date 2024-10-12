package org.example.dao;

import org.example.entity.administrator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class administratorDAOImpl implements administratorDAO {
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/web?useSSL=false&serverTimezone=UTC", "root", "123456");
    }

    @Override
    public Boolean CheckTheUser(String username, String password) {
        String sql = "SELECT password FROM administrator WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    // 这里建议使用密码哈希比较
                    return storedPassword.equals(password); // 简单密码比较，不推荐用于生产环境
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void add(administrator admin) {
        String sql = "INSERT INTO administrator (username, password, email) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword()); // 请考虑使用哈希密码
            ps.setString(3, admin.getEmail()); // 添加邮箱字段
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public administrator findById(String id) {
        String sql = "SELECT * FROM administrator WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapToAdministrator(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<administrator> findAll() {
        List<administrator> admins = new ArrayList<>();
        String sql = "SELECT * FROM administrator";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                admins.add(mapToAdministrator(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    @Override
    public void update(administrator admin) {
        String sql = "UPDATE administrator SET password = ?, email = ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, admin.getPassword()); // 请考虑使用哈希密码
            ps.setString(2, admin.getEmail());
            ps.setString(3, admin.getUsername());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM administrator WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private administrator mapToAdministrator(ResultSet rs) throws SQLException {
        administrator admin = new administrator();
        admin.setUsername(rs.getString("username"));
        admin.setPassword(rs.getString("password")); // 请注意密码的处理
        admin.setEmail(rs.getString("email"));
        return admin;
    }
}
