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
public class PrescriptionDAO {
    private Connection connectionToDB;
    public PrescriptionDAO() {
        connectionToDB = DBCredentials.getCredentials().getConnection();
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
