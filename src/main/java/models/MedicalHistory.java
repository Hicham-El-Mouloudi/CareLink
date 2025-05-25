/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author lenovo
 */
public class MedicalHistory {
    private int id;
    private java.util.Date date;
    private String diagnosis;
    private String attachments;
    private int patientId;

    public MedicalHistory(int id, java.util.Date date, String diagnosis, String attachments, int patientId) {
        this.id = id;
        this.date = date;
        this.diagnosis = diagnosis;
        this.attachments = attachments;
        this.patientId = patientId;
    }
    public MedicalHistory(java.util.Date date, String diagnosis, String attachments, int patientId) {
        this.date = date;
        this.diagnosis = diagnosis;
        this.attachments = attachments;
        this.patientId = patientId;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public java.util.Date getDate() { return date; }
    public void setDate(java.util.Date date) { this.date = date; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getAttachments() { return attachments; }
    public void setAttachments(String attachments) { this.attachments = attachments; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
}
