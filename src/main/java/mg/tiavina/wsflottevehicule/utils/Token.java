package mg.tiavina.wsflottevehicule.utils;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import mg.tiavina.wsflottevehicule.models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class Token {
    int id;
    String value;
    long expired;
    int userId;

    private static int EXPIRY_DAYS=1;

    private JSONObject payload = new JSONObject();

    public static JWebToken createToken(User user) throws JSONException, SQLException {
        JSONArray audArray = new JSONArray();   audArray.put(user.getRole());
        long expires = LocalDateTime.now().plusDays(EXPIRY_DAYS).toEpochSecond(ZoneOffset.UTC);
        JWebToken token = new JWebToken(String.valueOf(user.getId()), audArray, expires);
        Token model = new Token();
        model.setValue(token.toString());
        model.setExpired(expires);
        model.setUserId(user.getId());
        model.save(null);
        return token;
    }

    public static boolean checkTokenString(String token) throws Exception {
        try {
            Token model = Token.find(token, null);
            if (model == null) throw new Exception("ERROR 415: Not authentificate");
        } catch (SQLException e) {
            throw new Exception("ERROR 415: Not authentificate");
        }
        return true;
    }

    public void save(Connection connection) throws SQLException {
        boolean wasConnected = true;
        if (connection == null) {
            wasConnected = false;
            connection = PostgreSQLConnection.getConnection();
        }
        String sql = "INSERT INTO tokens (id, \"value\", expired, user_id) VALUES (default,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, this.getValue());
            stmt.setLong(2, this.getExpired());
            stmt.setInt(3, this.getUserId());
            stmt.executeUpdate();
        }
        if (!wasConnected) {
            connection.close();
        }
    }

    public static void delete(String token, Connection connection) throws SQLException {
        boolean wasConnected = true;
        if (connection == null) {
            wasConnected = false;
            connection = PostgreSQLConnection.getConnection();
        }
        String sql = "DELETE FROM tokens WHERE \"value\"=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, token);
            System.out.println(stmt.toString());
            stmt.executeUpdate();
        }
        if (!wasConnected) {
            connection.close();
        }
    }

    public static Token find(String token, Connection connection) throws SQLException {
        Token model = null;
        boolean wasConnected = true;
        if (connection == null) {
            wasConnected = false;
            connection = PostgreSQLConnection.getConnection();
        }
        String sql = "SELECT * FROM tokens WHERE \"value\"=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                model = new Token();
                model.setId(rs.getInt("id"));
                model.setValue(rs.getString("value"));
                model.setExpired(rs.getLong("expired"));
                model.setUserId(rs.getInt("user_id"));
            }
        }
        if (!wasConnected) {
            connection.close();
        }
        return model;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getExpired() {
        return expired;
    }

    public void setExpired(long expired) {
        this.expired = expired;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public static JWebToken checkToken(ServletRequest request) throws Exception {
        try {
            String token = castToken(getToken(request));
            Token.checkTokenString(token);
            JWebToken jWebToken = new JWebToken(token);
            return jWebToken;
        } catch( Exception ex) {
            throw ex;
        }
    }

    private static String castToken(String token) {
        return token.substring(7);
    }

    private static String getToken(ServletRequest request) {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String token = httpRequest.getHeader("authorization");
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
