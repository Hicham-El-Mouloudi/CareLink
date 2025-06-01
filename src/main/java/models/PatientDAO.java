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
 * @author Hicham El Mouloudi
 */
public class PatientDAO {
    private Connection connectionToDB;
    public PatientDAO() {
        connectionToDB = DBCredentials.getCredentials().getConnection();
    }    public List<Patient> getAllPatients() throws SQLException {
        System.out.println("PatientDAO : Getting all patients from database");
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM Patient";
        Statement statement = connectionToDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            Patient patient = new Patient(
                rs.getInt("Id"),
                rs.getString("EmergencyContact"),
                rs.getString("MedicalConditions"),
                rs.getString("State"),
                rs.getInt("PersonID")
            );
            patients.add(patient);
            System.out.println("PatientDAO : Retrieved patient with ID : " + patient.getId());
        }
        System.out.println("PatientDAO : Total patients retrieved : " + patients.size());
        return patients;
    }    public void insertPatient(Patient p) throws SQLException {
        System.out.println("PatientDAO : Inserting new patient with PersonID : " + p.getPersonId());
        String query = "INSERT INTO Patient(EmergencyContact, MedicalConditions, State, PersonID) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setString(1, p.getEmergencyContact());
        ps.setString(2, p.getMedicalConditions());
        ps.setString(3, p.getState());
        ps.setInt(4, p.getPersonId());
        ps.executeUpdate();
        System.out.println("PatientDAO : Successfully inserted patient with medical conditions : " + p.getMedicalConditions());
    }
    public int getNumberOfPatients() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM Patient";
        Statement statement = connectionToDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            return rs.getInt("count");
        }
        return 0;
    }    public boolean updatePatient(Patient p) throws SQLException {
        System.out.println("PatientDAO : Updating patient with ID : " + p.getId());
        String query = "UPDATE Patient SET EmergencyContact=?, MedicalConditions=?, State=?, PersonID=? WHERE Id=?";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setString(1, p.getEmergencyContact());
        ps.setString(2, p.getMedicalConditions());
        ps.setString(3, p.getState());
        ps.setInt(4, p.getPersonId());
        ps.setInt(5, p.getId());
        int rows = ps.executeUpdate();
        System.out.println("PatientDAO : Update " + (rows > 0 ? "successful" : "failed") + " for patient ID : " + p.getId());
        return rows > 0;
    }    public boolean deletePatient(int patientId) throws SQLException {
        System.out.println("PatientDAO : Attempting to delete patient with ID : " + patientId);
        String query = "DELETE FROM Patient WHERE Id=?";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setInt(1, patientId);
        int rows = ps.executeUpdate();
        System.out.println("PatientDAO : Patient deletion " + (rows > 0 ? "successful" : "failed") + " for ID : " + patientId);
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
