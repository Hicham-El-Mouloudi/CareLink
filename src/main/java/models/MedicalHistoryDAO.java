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
public class MedicalHistoryDAO {
    private String url;
    private String user;
    private String password;
    private Connection connectionToDB;
    public MedicalHistoryDAO() {
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
    public List<MedicalHistory> getAllMedicalHistories() throws SQLException {
        List<MedicalHistory> list = new ArrayList<>();
        String query = "SELECT * FROM MedicalHistory";
        Statement statement = connectionToDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            list.add(new MedicalHistory(
                rs.getInt("Id"),
                rs.getDate("Date"),
                rs.getString("Diagnosis"),
                rs.getString("Attachments"),
                rs.getInt("PatientID")
            ));
        }
        return list;
    }
    public void insertMedicalHistory(MedicalHistory mh) throws SQLException {
        String query = "INSERT INTO MedicalHistory(Date, Diagnosis, Attachments, PatientID) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setDate(1, new java.sql.Date(mh.getDate().getTime()));
        ps.setString(2, mh.getDiagnosis());
        ps.setString(3, mh.getAttachments());
        ps.setInt(4, mh.getPatientId());
        ps.executeUpdate();
    }
}
