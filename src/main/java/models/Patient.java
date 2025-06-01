/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * This is a class containning the columns of the table : 'Patient' in the 'ApplicationDeSuiviDesTraitementsMedicaux' database
 */
public class Patient {
    private int id;
    private String emergencyContact;
    private String medicalConditions;
    private String state;
    private int personId;
    public Patient(){
        
    }
    public Patient(int id, String emergencyContact, String medicalConditions, String state, int personId) {
        this.id = id;
        this.emergencyContact = emergencyContact;
        this.medicalConditions = medicalConditions;
        this.state = state;
        this.personId = personId;
    }
    public Patient(String emergencyContact, String medicalConditions, String state, int personId) {
        this.emergencyContact = emergencyContact;
        this.medicalConditions = medicalConditions;
        this.state = state;
        this.personId = personId;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    public String getMedicalConditions() { return medicalConditions; }
    public void setMedicalConditions(String medicalConditions) { this.medicalConditions = medicalConditions; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public int getPersonId() { return personId; }
    public void setPersonId(int personId) { this.personId = personId; }
}
