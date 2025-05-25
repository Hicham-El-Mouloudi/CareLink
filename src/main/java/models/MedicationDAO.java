/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.*;
import java.util.*;

/**
 *
 * @author lenovo
 */
public class MedicationDAO {
    private String url;
    private String user;
    private String password;
    private Connection connectionToDB;
    public MedicationDAO() {
        url = "jdbc:mysql://localhost:3306/ApplicationDeSuiviDesTraitementsMedicaux";
        user = "root";
        password = "";
        try {
            connectionToDB = DriverManager.getConnection(url, user, password);
            if (connectionToDB == null) {
                System.out.println("Connection to DB returned null!");
            }
        } catch (SQLException e) {
            System.out.println("Error in connection to DB: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public List<Medication> getAllMedications() throws SQLException {
        List<Medication> list = new ArrayList<>();
        String query = "SELECT * FROM Medication";
        Statement statement = connectionToDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            list.add(new Medication(
                rs.getInt("Id"),
                rs.getString("Name"),
                rs.getString("Notes")
            ));
        }
        return list;
    }
    public void insertMedication(Medication m) throws SQLException {
        String query = "INSERT INTO Medication(Name, Notes) VALUES (?, ?)";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setString(1, m.getName());
        ps.setString(2, m.getNotes());
        ps.executeUpdate();
    }
}
