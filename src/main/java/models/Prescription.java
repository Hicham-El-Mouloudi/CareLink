/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author lenovo
 */
public class Prescription {
    private int id;
    private java.util.Date date;
    private int doctorId;

    public Prescription(int id, java.util.Date date, int doctorId) {
        this.id = id;
        this.date = date;
        this.doctorId = doctorId;
    }
    public Prescription(java.util.Date date, int doctorId) {
        this.date = date;
        this.doctorId = doctorId;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public java.util.Date getDate() { return date; }
    public void setDate(java.util.Date date) { this.date = date; }
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
}
