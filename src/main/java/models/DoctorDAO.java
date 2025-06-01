/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.*;

import java.util.*;

import credentials.DBCredentials;
import models.DataAccessSettings;
import models.Doctor;

/**
 *
 * @author lenovo
 */
public class DoctorDAO {
    private Connection connectionToDB;
    public DoctorDAO() {
        connectionToDB = DBCredentials.getCredentials().getConnection();
    }

    public static boolean addNewDoctor(Doctor doctor) {
        String sql = "INSERT INTO Doctor (Specialisation, CertificationCredentials, Schedule, Department, PersonID,username,password) VALUES (?, ?, ?, ?, ?,?,?)";

        //this is a try-with-resources statement, it closes all object that implement AutoClosable
        try (Connection conn = DataAccessSettings.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

        	//we will need to validate the data here first
        	
            stmt.setString(1, doctor.getSpecialisation());
            stmt.setString(2, doctor.getCertification());
            stmt.setString(3, doctor.getSchedule());
            stmt.setString(4, doctor.getDepartment());
            stmt.setInt(5, doctor.getPersonalInfo().getId());
            stmt.setString(6, doctor.getUsername());

            stmt.setString(7, doctor.getPassword());

            
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
        
    }

    public static boolean updateDoctor(Doctor doctor) {
        String sql = "UPDATE Doctor SET Specialisation = ?, CertificationCredentials = ?, Schedule = ?, Department = ?, PersonID = ?,password = ?  WHERE id = ?";

        try (Connection conn = DataAccessSettings.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, doctor.getSpecialisation());
            stmt.setString(2, doctor.getCertification());
            stmt.setString(3, doctor.getSchedule());
            stmt.setString(4, doctor.getDepartment());
            stmt.setInt(5, doctor.getPersonalInfo().getId());

            stmt.setString(6, doctor.getPassword());
            
            stmt.setInt(7, doctor.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteDoctor(int id) {
        String sql = "DELETE FROM Doctor WHERE id = ?";

        try (Connection conn = DataAccessSettings.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Doctor getDoctorById(int id) {
        String sql = "SELECT * FROM Doctor WHERE id = ?";

        try (Connection conn = DataAccessSettings.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Doctor(
                    rs.getInt("id"),
                    rs.getString("Specialisation"),
                    rs.getString("CertificationCredentials"),
                    rs.getString("Schedule"),
                    rs.getString("Department"),
                    rs.getInt("PersonID")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    
    
    // Find by ID
    public static Doctor findById(int id) {
        String sql = "SELECT * FROM Doctor WHERE id = ?";

        try (Connection conn = DataAccessSettings.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Doctor(
                        rs.getInt("id"),
                        rs.getString("Specialisation"),
                        rs.getString("CertificationCredentials"),
                        rs.getString("Schedule"),
                        rs.getString("Department"),
                        rs.getInt("PersonID")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Find by username and password (used for login)
    public static Doctor findByUsernameAndPassword(String username, String password) {
    	String sql = "SELECT * FROM Doctor WHERE username = ? AND password = ?";

        try (Connection conn = DataAccessSettings.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Doctor(
                        rs.getInt("id"),
                        rs.getString("Specialisation"),
                        rs.getString("CertificationCredentials"),
                        rs.getString("Schedule"),
                        rs.getString("Department"),
                        rs.getInt("PersonID") // optional if you keep this field
                      
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Check if doctor exists by ID
    public static boolean isExist(int id) {
        String sql = "SELECT 1 FROM Doctor WHERE id = ?";

        try (Connection conn = DataAccessSettings.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // returns true if a row is found
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    
    
    
    
    public static List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM Doctor";

        try (Connection conn = DataAccessSettings.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Doctor doc = new Doctor(
                    rs.getInt("id"),
                    rs.getString("Specialisation"),
                    rs.getString("CertificationCredentials"),
                    rs.getString("Schedule"),
                    rs.getString("Department"),
                    rs.getInt("PersonID")
                );
                doctors.add(doc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }
}
