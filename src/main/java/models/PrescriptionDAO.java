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
public class PrescriptionDAO {
    private String url;
    private String user;
    private String password;
    private Connection connectionToDB;
    public PrescriptionDAO() {
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
    public List<Prescription> getAllPrescriptions() throws SQLException {
        List<Prescription> list = new ArrayList<>();
        String query = "SELECT * FROM Prescription";
        Statement statement = connectionToDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            list.add(new Prescription(
                rs.getInt("Id"),
                rs.getDate("Date"),
                rs.getInt("DoctorID")
            ));
        }
        return list;
    }
    public void insertPrescription(Prescription p) throws SQLException {
        String query = "INSERT INTO Prescription(Date, DoctorID) VALUES (?, ?)";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setDate(1, new java.sql.Date(p.getDate().getTime()));
        ps.setInt(2, p.getDoctorId());
        ps.executeUpdate();
    }
}
