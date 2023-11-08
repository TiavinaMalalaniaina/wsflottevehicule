package mg.tiavina.wsflottevehicule.models;

import mg.tiavina.wsflottevehicule.utils.PostgreSQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    int id;
    String username;
    String password;
    String role;

    public static User log(String username, String password) throws Exception {
        try {
            User user = find(username, null);
            if (user == null) throw new Exception("Utilisateur inconnue");
            if (!user.getPassword().equals(password)) throw new Exception("Mot de passe erroné");
            return user;
        } catch (SQLException e) {
            throw e;
        }

    }

    public static User find(String username, Connection connection) throws SQLException {
        User model = null;
        boolean wasConnected = true;
        if (connection == null) {
            wasConnected = false;
            connection = PostgreSQLConnection.getConnection();
        }
        String sql = "SELECT * FROM users WHERE username=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                model = new User();
                model.setId(rs.getInt("id"));
                model.setUsername(rs.getString("username"));
                model.setPassword(rs.getString("password"));
                model.setRole(rs.getString("role"));
            }
        }
        if (!wasConnected) {
            connection.close();
        }
        return model;
    }

    public User() {}

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
