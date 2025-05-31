/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.*;
import java.util.*;

import credentials.DBCredentials;

/**
 *
 * @author lenovo
 */
public class MedicationDAO {
    private Connection connectionToDB;
    public MedicationDAO() {
        connectionToDB = DBCredentials.getCredentials().getConnection();
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
