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
public class PatientDAO {
    private String url;
    private String user;
    private String password;
    private Connection connectionToDB;
    public PatientDAO() {
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
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM Patient";
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
    public void insertPatient(Patient p) {
        String query = "INSERT INTO Patient(EmergencyContact, MedicalConditions, State, PersonID) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connectionToDB.prepareStatement(query);
            ps.setString(1, p.getEmergencyContact());
            ps.setString(2, p.getMedicalConditions());
            ps.setString(3, p.getState());
            ps.setInt(4, p.getPersonId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getNumberOfPatients() {
        String query = "SELECT COUNT(*) AS count FROM Patient";
        try {
            Statement statement = connectionToDB.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updatePatient(Patient p) {
        String query = "UPDATE Patient SET EmergencyContact=?, MedicalConditions=?, State=?, PersonID=? WHERE Id=?";
        try {
            PreparedStatement ps = connectionToDB.prepareStatement(query);
            ps.setString(1, p.getEmergencyContact());
            ps.setString(2, p.getMedicalConditions());
            ps.setString(3, p.getState());
            ps.setInt(4, p.getPersonId());
            ps.setInt(5, p.getId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePatient(int patientId) {
        String query = "DELETE FROM Patient WHERE Id=?";
        try {
            PreparedStatement ps = connectionToDB.prepareStatement(query);
            ps.setInt(1, patientId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Patient getPatientById(int id) {
        String query = "SELECT * FROM Patient WHERE Id=?";
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
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
