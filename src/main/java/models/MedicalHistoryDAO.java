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
public class MedicalHistoryDAO {
    private Connection connectionToDB;
    public MedicalHistoryDAO() {
        connectionToDB = DBCredentials.getCredentials().getConnection();
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
