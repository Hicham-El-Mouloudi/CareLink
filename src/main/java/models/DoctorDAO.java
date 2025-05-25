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
public class DoctorDAO {
    private String url;
    private String user;
    private String password;
    private Connection connectionToDB;
    public DoctorDAO() {
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
