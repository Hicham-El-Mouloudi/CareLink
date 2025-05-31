package models;

import java.sql.*;
import java.util.*;

import credentials.DBCredentials;

public class Prescription_MedicationDAO {
    private Connection connectionToDB;
    public Prescription_MedicationDAO() {
        connectionToDB = DBCredentials.getCredentials().getConnection();
    }
    public List<Prescription_Medication> getAllPrescriptionMedications() throws SQLException {
        List<Prescription_Medication> list = new ArrayList<>();
        String query = "SELECT * FROM Prescription_Medication";
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
        return list;
    }
    public void insertPrescriptionMedication(Prescription_Medication pm) throws SQLException {
        String query = "INSERT INTO Prescription_Medication(DosageInstructions, Duration, PrescriptionID, MedicationID) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connectionToDB.prepareStatement(query);
        ps.setString(1, pm.getDosageInstructions());
        ps.setString(2, pm.getDuration());
        ps.setInt(3, pm.getPrescriptionId());
        ps.setInt(4, pm.getMedicationId());
        ps.executeUpdate();
    }
}