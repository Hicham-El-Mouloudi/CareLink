package models;

import java.sql.*;
import java.util.*;

public class Prescription_MedicationDAO {
    private String url;
    private String user;
    private String password;
    private Connection connectionToDB;
    public Prescription_MedicationDAO() {
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
    public List<Prescription_Medication> getAllPrescriptionMedications() {
        List<Prescription_Medication> list = new ArrayList<>();
        String query = "SELECT * FROM Prescription_Medication";
        try {
            Statement statement = connectionToDB.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                list.add(new Prescription_Medication(
                    rs.getInt("Id"),
                    rs.getString("DosageInstructions"),
                    rs.getString("Duration"),
                    rs.getInt("PrescriptionID"),
                    rs.getInt("MedicationID")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Prescription_MedicationDAO : SQLException occured while calling 'getAllPrescriptionMedications'");
            e.printStackTrace();
        }
        return list;
    }
    public int insertPrescriptionMedication(Prescription_Medication pm) {
        String query = "INSERT INTO Prescription_Medication(DosageInstructions, Duration, PrescriptionID, MedicationID) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connectionToDB.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pm.getDosageInstructions());
            ps.setString(2, pm.getDuration());
            ps.setInt(3, pm.getPrescriptionId());
            ps.setInt(4, pm.getMedicationId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Prescription_MedicationDAO : Unable to generate ID Error");
            }
        } catch (SQLException e) {
            System.err.println("Prescription_MedicationDAO : SQLException occured while calling 'insertPrescriptionMedication'");
            e.printStackTrace();
            return -1;
        }
    }
}