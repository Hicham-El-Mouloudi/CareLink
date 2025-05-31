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
public class AppointmentDAO {
    private String url;
    private String user;
    private String password;
    private Connection connectionToDB;
    public AppointmentDAO() {
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
    public List<Appointment> getAllAppointments() {
        try{
            List<Appointment> appointments = new ArrayList<>();
            String query = "SELECT * FROM Appointment";
            Statement statement = connectionToDB.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                appointments.add(new Appointment(
                    rs.getInt("Id"),
                    rs.getTimestamp("DateTime"),
                    rs.getString("ReasonToVisit"),
                    rs.getString("Status"),
                    rs.getInt("PatientID"),
                    rs.getInt("DoctorID")
                ));
            }
            return appointments;
        } catch (SQLException e) {
            System.err.println("AppointmentDAO : Error while calling 'getAllAppointments' ");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public void insertAppointment(Appointment a) {
        try {
            String query = "INSERT INTO Appointment(DateTime, ReasonToVisit, Status, PatientID, DoctorID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connectionToDB.prepareStatement(query);
            ps.setTimestamp(1, new java.sql.Timestamp(a.getDateTime().getTime()));
            ps.setString(2, a.getReasonToVisit());
            ps.setString(3, a.getStatus());
            ps.setInt(4, a.getPatientId());
            ps.setInt(5, a.getDoctorId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("AppointmentDAO : Error while calling 'getAllAppointments' ");
            e.printStackTrace();
        }
    }
    // This function is used to get the number of 'rendez-vous' for the current day
    public int getNumberOfAppointmentsForToday() {
        try {
            String query = "SELECT COUNT(*) FROM Appointment WHERE DATE(DateTime) = (SELECT CURDATE())";
            Statement statement = connectionToDB.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return rs.getInt(1);
            }else {
                throw new SQLException("AppointmentDAO : Error while calling 'getNumberOfAppointmentsForToday' ");
            }
            
        } catch (SQLException e) {
            System.err.println("AppointmentDAO : Error while calling 'getNumberOfAppointmentsForToday' ");
            e.printStackTrace();
            return 0;
        }
    }
}
