/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author lenovo
 */
public class Traitement {
    private int id;
    private java.util.Date date;
    private String description;
    private String notesObservation;
    private String raisonForTraitement;
    private boolean followUpRequired;
    private int nextAppoinementId;
    private String status;
    private String type;
    private int patientId;
    private int doctorId;
    private int prescriptionId;
    private int medicalRecordId;

    public Traitement(int id, java.util.Date date, String description, String notesObservation, String raisonForTraitement, boolean followUpRequired, int nextAppoinementId, String status, String type, int patientId, int doctorId, int prescriptionId, int medicalRecordId) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.notesObservation = notesObservation;
        this.raisonForTraitement = raisonForTraitement;
        this.followUpRequired = followUpRequired;
        this.nextAppoinementId = nextAppoinementId;
        this.status = status;
        this.type = type;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.prescriptionId = prescriptionId;
        this.medicalRecordId = medicalRecordId;
    }
    public Traitement(java.util.Date date, String description, String notesObservation, String raisonForTraitement, boolean followUpRequired, int nextAppoinementId, String status, String type, int patientId, int doctorId, int prescriptionId, int medicalRecordId) {
        this.date = date;
        this.description = description;
        this.notesObservation = notesObservation;
        this.raisonForTraitement = raisonForTraitement;
        this.followUpRequired = followUpRequired;
        this.nextAppoinementId = nextAppoinementId;
        this.status = status;
        this.type = type;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.prescriptionId = prescriptionId;
        this.medicalRecordId = medicalRecordId;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public java.util.Date getDate() { return date; }
    public void setDate(java.util.Date date) { this.date = date; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getNotesObservation() { return notesObservation; }
    public void setNotesObservation(String notesObservation) { this.notesObservation = notesObservation; }
    public String getRaisonForTraitement() { return raisonForTraitement; }
    public void setRaisonForTraitement(String raisonForTraitement) { this.raisonForTraitement = raisonForTraitement; }
    public boolean isFollowUpRequired() { return followUpRequired; }
    public void setFollowUpRequired(boolean followUpRequired) { this.followUpRequired = followUpRequired; }
    public int getNextAppoinementId() { return nextAppoinementId; }
    public void setNextAppoinementId(int nextAppoinementId) { this.nextAppoinementId = nextAppoinementId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public int getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(int prescriptionId) { this.prescriptionId = prescriptionId; }
    public int getMedicalRecordId() { return medicalRecordId; }
    public void setMedicalRecordId(int medicalRecordId) { this.medicalRecordId = medicalRecordId; }
}
