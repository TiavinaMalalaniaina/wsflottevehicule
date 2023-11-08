package mg.tiavina.wsflottevehicule.utils;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLConnection{
    private Connection connection;

    private static String url = "jdbc:postgresql://localhost:5432/wsflotte";
    private static String username = "postgres";
    private static String password = "malalaniaina";
    private static String driver = "org.postgresql.Driver";



    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load PostgreSQL JDBC driver");
        }
        return DriverManager.getConnection(url, username, password);
    }
}
