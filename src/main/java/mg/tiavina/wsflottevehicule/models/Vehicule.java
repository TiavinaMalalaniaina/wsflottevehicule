package mg.tiavina.wsflottevehicule.models;

import mg.tiavina.wsflottevehicule.utils.PostgreSQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Vehicule {
    String matricule;
    String marque;
    String name;

    public Vehicule() {}

    public Vehicule(String matricule, String marque, String name) {
        this.matricule = matricule;
        this.marque = marque;
        this.name = name;
    }

    public void save(Connection connection) throws SQLException {
        boolean wasConnected = true;
        if (connection == null) {
            wasConnected = false;
            connection = PostgreSQLConnection.getConnection();
        }
        String sql = "INSERT INTO vehicules(\"matricule\", \"marque\", \"name\") VALUES (?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, this.getMatricule());
            stmt.setString(2, this.getMarque());
            stmt.setString(3, this.getName());
            stmt.executeUpdate();
        }
        if (!wasConnected) {
            connection.close();
        }
    }
    public void update(Connection connection) throws SQLException {
        boolean wasConnected = true;
        if (connection == null) {
            wasConnected = false;
            connection = PostgreSQLConnection.getConnection();
        }
        String sql = "UPDATE vehicules SET \"marque\"=?, \"name\"=? WHERE \"matricule\"=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, this.getMarque());
            stmt.setString(2, this.getName());
            stmt.setString(3, this.getMatricule());
            stmt.executeUpdate();
        }
        if (!wasConnected) {
            connection.close();
        }
    }
    public static void delete(String matricule, Connection connection) throws SQLException {
        boolean wasConnected = true;
        if (connection == null) {
            wasConnected = false;
            connection = PostgreSQLConnection.getConnection();
        }
        String sql = "DELETE FROM vehicules WHERE matricule=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, matricule);
            stmt.executeUpdate();
        }
        if (!wasConnected) {
            connection.close();
        }
    }
    public static List<Vehicule> findAll(Connection connection) throws SQLException {
        List<Vehicule> models = new ArrayList<>();
        boolean wasConnected = true;
        if (connection == null) {
            wasConnected = false;
            connection = PostgreSQLConnection.getConnection();
        }
        String sql = "SELECT * FROM vehicules";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Vehicule model = new Vehicule();
                model.setMatricule(rs.getString("matricule"));
                model.setMarque(rs.getString("marque"));
                model.setName(rs.getString("name"));
                models.add(model);
            }
        }
        if (!wasConnected) {
            connection.close();
        }
        return models;
    }
    public static Vehicule findByMatricule(String matricule, Connection connection) throws SQLException {
        Vehicule model = null;
        boolean wasConnected = true;
        if (connection == null) {
            wasConnected = false;
            connection = PostgreSQLConnection.getConnection();
        }
        String sql = "SELECT * FROM vehicules";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                model = new Vehicule();
                model.setMatricule(rs.getString("matricule"));
                model.setMarque(rs.getString("marque"));
                model.setName(rs.getString("name"));
            }
        }
        if (!wasConnected) {
            connection.close();
        }
        return model;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
