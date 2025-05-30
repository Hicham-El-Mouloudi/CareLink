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
        url = "jdbc:mysql://localhost:3306/javafxdb";
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
    /**
     * @author amine
     * @param year 
     * @param month
     * @param day
     * @return a list of appointments of a given date
     * @throws SQLException
     */
    public List<Appointment> getAppointmentsByDate(String year, String month, String day) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();

        // Format 
        String dateStr = String.format("%s-%02d-%02d", year, Integer.parseInt(month), Integer.parseInt(day));

        String query = "SELECT * FROM Appointment WHERE DATE(DateTime) = ?";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setString(1, dateStr);

        ResultSet rs = ps.executeQuery();
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
    public void updateAppointment(Appointment appointment) throws SQLException {
        String query = "UPDATE Appointment SET DateTime = ?, ReasonToVisit = ?, Status = ?, PatientID = ?, DoctorID = ? WHERE Id = ?";

        try (PreparedStatement ps = connectionToDB.prepareStatement(query)) {
            ps.setTimestamp(1, new java.sql.Timestamp(appointment.getDateTime().getTime()));
            ps.setString(2, appointment.getReasonToVisit());
            ps.setString(3, appointment.getStatus());
            ps.setInt(4, appointment.getPatientId());
            ps.setInt(5, appointment.getDoctorId());
            ps.setInt(6, appointment.getId());

            ps.executeUpdate();
        }
    }


}
