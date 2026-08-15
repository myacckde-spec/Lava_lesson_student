package org.itproger.project9windowadmin1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;

public class WinController {

    @FXML
    private TextField win_Email;

    @FXML
    private Button win_buton;

    @FXML
    private TextField win_login;

    @FXML
    private PasswordField win_pass;

    private int adminId = -1;

    @FXML
    public void initialize() {
        // При старте находим или создаём пользователя Admin, затем загружаем его данные в поля
        try {
            ensureAdminExists();
            loadUserByLogin("Admin");
        } catch (SQLException | ClassNotFoundException e) {
            showError("Ошибка БД при инициализации: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void ensureAdminExists() throws SQLException, ClassNotFoundException {
        try (Connection conn = DB.getDbConnection()) {
            // Создадим таблицу users если её нет (безопасно)
            try (Statement st = conn.createStatement()) {
                st.execute("CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, login VARCHAR(255) UNIQUE NOT NULL, Email VARCHAR(255), Password VARCHAR(255))");
            }

            String check = "SELECT id FROM users WHERE login = ?";
            try (PreparedStatement ps = conn.prepareStatement(check)) {
                ps.setString(1, "Admin");
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        String ins = "INSERT INTO users (login, Email, Password) VALUES (?, ?, ?)";
                        try (PreparedStatement pi = conn.prepareStatement(ins, Statement.RETURN_GENERATED_KEYS)) {
                            pi.setString(1, "Admin");
                            pi.setString(2, "Administrator");
                            pi.setString(3, "");
                            pi.executeUpdate();
                            try (ResultSet gk = pi.getGeneratedKeys()) {
                                if (gk.next()) adminId = gk.getInt(1);
                            }
                        }
                    }
                }
            }
        }
    }

    private void loadUserByLogin(String login) {
        try (Connection conn = DB.getDbConnection()) {
            String sql = "SELECT id, login, Email FROM users WHERE login = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, login);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        adminId = rs.getInt("id");
                        win_login.setText(rs.getString("login"));
                        win_Email.setText(rs.getString("Email"));
                        win_pass.clear();
                        win_buton.setText("Обновить данные");
                    } else {
                        win_buton.setText("Пользователь не найден");
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            showError("Ошибка при загрузке пользователя: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onSave(ActionEvent event) {
        String newLogin = win_login.getText().trim();
        String newEmail = win_Email.getText().trim();
        String newPassword = win_pass.getText();

        if (newLogin.isEmpty()) {
            showError("Логин не может быть пустым.");
            return;
        }

        try (Connection conn = DB.getDbConnection()) {
            // Проверка дубликата логина у другого пользователя
            String sqlCheck = "SELECT id FROM users WHERE login = ? AND id != ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlCheck)) {
                ps.setString(1, newLogin);
                ps.setInt(2, adminId <= 0 ? -1 : adminId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        showError("Пользователь с таким логином уже существует.");
                        return;
                    }
                }
            }

            if (adminId > 0) {
                String sqlUpd;
                if (newPassword != null && !newPassword.isEmpty()) {
                    sqlUpd = "UPDATE users SET login = ?, Email = ?, Password = ? WHERE id = ?";
                    try (PreparedStatement ps = conn.prepareStatement(sqlUpd)) {
                        ps.setString(1, newLogin);
                        ps.setString(2, newEmail);
                        ps.setString(3, md5String(newPassword));
                        ps.setInt(4, adminId);
                        ps.executeUpdate();
                    }
                } else {
                    sqlUpd = "UPDATE users SET login = ?, Email = ? WHERE id = ?";
                    try (PreparedStatement ps = conn.prepareStatement(sqlUpd)) {
                        ps.setString(1, newLogin);
                        ps.setString(2, newEmail);
                        ps.setInt(3, adminId);
                        ps.executeUpdate();
                    }
                }
            } else {
                // на всякий случай — вставка
                String sqlIns = "INSERT INTO users (login, Email, Password) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sqlIns, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, newLogin);
                    ps.setString(2, newEmail);
                    ps.setString(3, newPassword == null ? "" : md5String(newPassword));
                    ps.executeUpdate();
                    try (ResultSet gk = ps.getGeneratedKeys()) {
                        if (gk.next()) adminId = gk.getInt(1);
                    }
                }
            }

            // Очистка полей после установки данных
            clearFields();
            showInfo("Данные успешно сохранены.");

            // Загрузим обновлённого пользователя (если нужно показать снова)
            loadUserByLogin(newLogin);

        } catch (SQLException | ClassNotFoundException ex) {
            showError("Ошибка при сохранении: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private boolean isLoginExists(String login) {
        try (Connection conn = DB.getDbConnection()) {
            String sql = "SELECT id FROM users WHERE login = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, login);
                try (ResultSet rs = pstmt.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
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
        if (password == null) return "";
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Ошибка");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Информация");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
