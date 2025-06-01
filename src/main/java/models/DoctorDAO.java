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
public class DoctorDAO {
    private Connection connectionToDB;
    public DoctorDAO() {
        connectionToDB = DBCredentials.getCredentials().getConnection();
    }
    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT * FROM Doctor";
        Statement statement = connectionToDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            doctors.add(new Doctor(
                rs.getInt("Id"),
                rs.getString("Specialisation"),
                rs.getString("CertificationCredentials"),
                rs.getString("Schedule"),
                rs.getString("Department"),
                rs.getInt("PersonID")
            ));
        }
        return doctors;
    }
    public void insertDoctor(Doctor d) throws SQLException {
        String query = "INSERT INTO Doctor(Specialisation, CertificationCredentials, Schedule, Department, PersonID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setString(1, d.getSpecialisation());
        ps.setString(2, d.getCertificationCredentials());
        ps.setString(3, d.getSchedule());
        ps.setString(4, d.getDepartment());
        ps.setInt(5, d.getPersonId());
        ps.executeUpdate();
    }
}
