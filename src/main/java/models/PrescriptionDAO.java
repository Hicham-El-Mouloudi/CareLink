/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.*;
import java.sql.Date;
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
    public int insertPrescription(Prescription p){
        try {
            String query = "INSERT INTO Prescription(Date, DoctorID) VALUES (?, ?)";
            PreparedStatement ps = connectionToDB.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, new java.sql.Date(p.getDate().getTime()));
            ps.setInt(2, p.getDoctorId());
            ps.executeUpdate();
            ResultSet result = ps.getGeneratedKeys();
            // Getting the ID of the prescription just saved
            if (result.next()) {
                return result.getInt(1);
            }
            else {
                throw new SQLException("PrescriptionDAO : Inserting prescription failed, no ID obtained.");
            }
        }catch(SQLException e) {
            // 
            System.err.println("PrescriptionDAO : Error executing insertPrescription function");
            e.printStackTrace();
            return -1;
        }
    }
}
