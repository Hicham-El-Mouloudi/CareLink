/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import credentials.*;

import java.sql.*;
import java.util.*;

/**
 *
 * @author lenovo
 */
public class AppointmentDAO {
    private Connection connectionToDB;
    public AppointmentDAO() {
        connectionToDB = DBCredentials.getCredentials().getConnection();
    }
    public List<Appointment> getAllAppointments() throws SQLException {
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
    }
    public void insertAppointment(Appointment a) throws SQLException {
        String query = "INSERT INTO Appointment(DateTime, ReasonToVisit, Status, PatientID, DoctorID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setTimestamp(1, new java.sql.Timestamp(a.getDateTime().getTime()));
        ps.setString(2, a.getReasonToVisit());
        ps.setString(3, a.getStatus());
        ps.setInt(4, a.getPatientId());
        ps.setInt(5, a.getDoctorId());
        ps.executeUpdate();
    }
    // This function is used to get the number of 'rendez-vous' for the current day
    public int getNumberOfAppointmentsForToday() throws SQLException {
        String query = "SELECT COUNT(*) FROM Appointment WHERE DATE(DateTime) = (SELECT CURDATE())";
        Statement statement = connectionToDB.createStatement();
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
}
