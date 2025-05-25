/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author lenovo
 */
public class Appointment {
    private int id;
    private java.util.Date dateTime;
    private String reasonToVisit;
    private String status;
    private int patientId;
    private int doctorId;

    public Appointment(int id, java.util.Date dateTime, String reasonToVisit, String status, int patientId, int doctorId) {
        this.id = id;
        this.dateTime = dateTime;
        this.reasonToVisit = reasonToVisit;
        this.status = status;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }
    public Appointment(java.util.Date dateTime, String reasonToVisit, String status, int patientId, int doctorId) {
        this.dateTime = dateTime;
        this.reasonToVisit = reasonToVisit;
        this.status = status;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public java.util.Date getDateTime() { return dateTime; }
    public void setDateTime(java.util.Date dateTime) { this.dateTime = dateTime; }
    public String getReasonToVisit() { return reasonToVisit; }
    public void setReasonToVisit(String reasonToVisit) { this.reasonToVisit = reasonToVisit; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
}
