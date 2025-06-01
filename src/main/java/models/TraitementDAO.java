/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.time.*;

import credentials.DBCredentials;

/**
 *
 * @author lenovo
 */
public class TraitementDAO {
    // 
    PrescriptionDAO precriptionDAO = new PrescriptionDAO();
    MedicationDAO medicationDAO = new MedicationDAO();
    Prescription_MedicationDAO prescription_MedicationDAO = new Prescription_MedicationDAO();
    // 
    private Connection connectionToDB;
    
    public TraitementDAO() {
        connectionToDB = DBCredentials.getCredentials().getConnection();
    }
    public List<Traitement> getAllTraitements() {
        try {
            List<Traitement> list = new ArrayList<>();
            String query = "SELECT * FROM Traitement";
            Statement statement = connectionToDB.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                list.add(new Traitement(
                    rs.getInt("Id"),
                    rs.getDate("Date").toLocalDate(),
                    rs.getString("Description"),
                    rs.getString("NotesObservation"),
                    rs.getString("RaisonForTraitement"),
                    rs.getBoolean("FollowUpRequired"),
                    rs.getInt("NextAppoinementID"),
                    rs.getString("Status"),
                    rs.getString("Type"),
                    rs.getInt("PatientID"),
                    rs.getInt("DoctorID"),
                    rs.getInt("PrescriptionID")
                ));
            }
            return list;
        } catch(SQLException e) {
            System.err.println("TraitementDAO : SQLException occured whule calling 'getAllTraitements' ");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public int insertTraitement(Traitement t){
        String query = "INSERT INTO Traitement(Date, Description, NotesObservation, RaisonForTraitement, FollowUpRequired, NextAppoinementID, Status, Type, PatientID, DoctorID, PrescriptionID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connectionToDB.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(t.getDate()));
            ps.setString(2, t.getDescription());
            ps.setString(3, t.getNotesObservation());
            ps.setString(4, t.getRaisonForTraitement());
            ps.setBoolean(5, t.isFollowUpRequired());
            ps.setInt(6, t.getNextAppoinementId());
            ps.setString(7, t.getStatus());
            ps.setString(8, t.getType());
            ps.setInt(9, t.getPatientId());
            ps.setInt(10, t.getDoctorId());
            ps.setInt(11, t.getPrescriptionId());
            ps.executeUpdate();
            ResultSet result = ps.getGeneratedKeys();
            if (result.next()) {
                return result.getInt(1);
            } else {
                throw new SQLException("TraitementDAO : Inserting traitement failed, no ID obtained.");
            }
        }catch(SQLException e) {
            System.err.println("TraitementDAO : SQLException occured while inserting a new traitement ");
            e.printStackTrace();
            return -1;
        }
    }

    // This function return the number of traitement with status == "En cours"
    public int getNumberOfActiveTraitements(){
        try {
            // 
            String query = "SELECT COUNT(*) AS Count FROM Traitement WHERE Status = 'En cours'";
            Statement statement = connectionToDB.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("Count");
            }
            return 0;
        } catch(SQLException e) {
            // 
            System.err.println("TraitementDAO : Error SQL executing getNumberOfActiveTraitements ");
            e.printStackTrace();
            return -1;
        }
    }

    // 
    public int addNewPrescription(Prescription p , Medication m, Prescription_Medication pm) {
        // Saving the prescription first and get its id
        int prescriptionID = precriptionDAO.insertPrescription(p);
        System.err.println("TraitementController : Saving Prescription id = " + prescriptionID);
        // saving it
        int medicationID = medicationDAO.insertMedication(m);
        System.err.println("TraitementController : Saving Medication id = " + medicationID);
        // saving it
        pm.setPrescriptionId(prescriptionID);
        pm.setMedicationId(medicationID);
        int prescription_MedicationID = prescription_MedicationDAO.insertPrescriptionMedication(pm);
        System.err.println("TraitementController : Saving Prescription_Medication id = " + prescription_MedicationID);
        return prescriptionID;
    }
}
