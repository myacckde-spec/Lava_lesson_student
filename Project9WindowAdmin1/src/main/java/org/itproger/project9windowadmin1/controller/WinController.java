package org.itproger.project9windowadmin1.controller;


import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class WinController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField win_Email;

    @FXML
    private Button win_buton;


    @FXML
    private TextField win_login;

    @FXML
    private PasswordField win_pass;

    @FXML
    public void initialize() {

        DB db = new DB();
        loadUserByLogin("Admin");


        String Login = win_login.getCharacters().toString();
        String Email = win_Email.getCharacters().toString();
        String Password = win_pass.getCharacters().toString();

        if (Login.equals("Admin")) {
            win_buton.setText("Ошибка: Логин занят!");
            return;
        }

        String hashedPass = md5String(Password);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hibernate?user=root&password=root")) {
            String sql = "UPDATE users SET Login = ?, Email = ?, Password = ? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Login);
            pstmt.setString(2, Email);
            pstmt.setString(3, Password);
            pstmt.executeUpdate();

            win_buton.setText("Успешно!");
            clearFields();
            //win_login.clear();
          //  win_Email.clear();
            //win_pass.clear();

            loadUserByLogin(Login);
        } catch (SQLException e) {
            win_buton.setText("Ошибка БД!");
            e.printStackTrace();
        }
    }

    private void loadUserByLogin(String login) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hibernate?user=root&password=root")) {
            String sql = "SELECT Login, Email FROM users WHERE login = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {


                win_login.setText("Login");
                win_Email.setText(rs.getString("Email"));
                win_pass.clear();
                win_buton.setText("Обновить данные");
            } else {
                win_buton.setText("Пользователь не найден");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isLoginExists(String login) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hibernate?user=root&password=root")) {
            String sql = "SELECT id FROM users WHERE login = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void clearFields() {
        win_login.clear();
        win_Email.clear();
        win_pass.clear();
    }

    private String md5String(String password) {
        if (password == null || password.isEmpty()) return "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}



