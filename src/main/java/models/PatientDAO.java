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
public class PatientDAO {
    private Connection connectionToDB;
    public PatientDAO() {
        connectionToDB = DBCredentials.getCredentials().getConnection();
    }
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM Patient";
        Statement statement = connectionToDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            patients.add(new Patient(
                rs.getInt("Id"),
                rs.getString("EmergencyContact"),
                rs.getString("MedicalConditions"),
                rs.getString("State"),
                rs.getInt("PersonID")
            ));
        }
        return patients;
    }
    public void insertPatient(Patient p) throws SQLException {
        String query = "INSERT INTO Patient(EmergencyContact, MedicalConditions, State, PersonID) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setString(1, p.getEmergencyContact());
        ps.setString(2, p.getMedicalConditions());
        ps.setString(3, p.getState());
        ps.setInt(4, p.getPersonId());
        ps.executeUpdate();
    }
    public int getNumberOfPatients() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM Patient";
        Statement statement = connectionToDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            return rs.getInt("count");
        }
        return 0;
    }

    public boolean updatePatient(Patient p) throws SQLException {
        String query = "UPDATE Patient SET EmergencyContact=?, MedicalConditions=?, State=?, PersonID=? WHERE Id=?";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setString(1, p.getEmergencyContact());
        ps.setString(2, p.getMedicalConditions());
        ps.setString(3, p.getState());
        ps.setInt(4, p.getPersonId());
        ps.setInt(5, p.getId());
        int rows = ps.executeUpdate();
        return rows > 0;
    }

    public boolean deletePatient(int patientId) throws SQLException {
        String query = "DELETE FROM Patient WHERE Id=?";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setInt(1, patientId);
        int rows = ps.executeUpdate();
        return rows > 0;
    }

    public Patient getPatientById(int id) throws SQLException {
        String query = "SELECT * FROM Patient WHERE Id=?";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Patient(
                rs.getInt("Id"),
                rs.getString("EmergencyContact"),
                rs.getString("MedicalConditions"),
                rs.getString("State"),
                rs.getInt("PersonID")
            );
        }
        return null;
    }

    /**
     * Deletes all patients from the Patient table.
     * @return true if successful, false otherwise
     */
    public boolean deleteAllPatients() {
        try {
            String query = "DELETE FROM Patient";
            Statement stmt = connectionToDB.createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
