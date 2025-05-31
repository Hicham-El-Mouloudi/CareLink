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
public class TraitementDAO {
    private String url;
    private String user;
    private String password;
    private Connection connectionToDB;
    public TraitementDAO() {
        url = "jdbc:mysql://localhost:3306/javafxdb";
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
    public List<Traitement> getAllTraitements() throws SQLException {
        List<Traitement> list = new ArrayList<>();
        String query = "SELECT * FROM Traitement";
        Statement statement = connectionToDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            list.add(new Traitement(
                rs.getInt("Id"),
                rs.getDate("Date"),
                rs.getString("Description"),
                rs.getString("NotesObservation"),
                rs.getString("RaisonForTraitement"),
                rs.getBoolean("FollowUpRequired"),
                rs.getInt("NextAppoinementID"),
                rs.getString("Status"),
                rs.getString("Type"),
                rs.getInt("PatientID"),
                rs.getInt("DoctorID"),
                rs.getInt("PrescriptionID"),
                rs.getInt("MedicalRecordID")
            ));
        }
        return list;
    }
    public void insertTraitement(Traitement t) throws SQLException {
        String query = "INSERT INTO Traitement(Date, Description, NotesObservation, RaisonForTraitement, FollowUpRequired, NextAppoinementID, Status, Type, PatientID, DoctorID, PrescriptionID, MedicalRecordID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setDate(1, new java.sql.Date(t.getDate().getTime()));
        ps.setString(2, t.getDescription());
        ps.setString(3, t.getNotesObservation());
        ps.setString(4, t.getRaisonForTraitement());
        ps.setBoolean(5, t.isFollowUpRequired());
        ps.setInt(6, t.getNextAppoinementId());
        ps.setString(7, t.getStatus());
        ps.setString(8, t.getType());
        ps.setInt(9, t.getPatientId());
        ps.setInt(10, t.getDoctorId());
        ps.setInt(11, t.getPrescriptionId());
        ps.setInt(12, t.getMedicalRecordId());
        ps.executeUpdate();
    }

    // This function return the number of traitement with status == "En cours" == "Active"
    public int getNumberOfActiveTraitements() throws SQLException {
        String query = "SELECT COUNT(*) FROM Traitement WHERE Status = 'Active'";
        Statement statement = connectionToDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
}
