/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author lenovo
 */
public class Doctor {
    private int id;
    private String specialisation;
    private String certificationCredentials;
    private String schedule;
    private String department;
    private int personId;

    public Doctor(int id, String specialisation, String certificationCredentials, String schedule, String department, int personId) {
        this.id = id;
        this.specialisation = specialisation;
        this.certificationCredentials = certificationCredentials;
        this.schedule = schedule;
        this.department = department;
        this.personId = personId;
    }
    public Doctor(String specialisation, String certificationCredentials, String schedule, String department, int personId) {
        this.specialisation = specialisation;
        this.certificationCredentials = certificationCredentials;
        this.schedule = schedule;
        this.department = department;
        this.personId = personId;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSpecialisation() { return specialisation; }
    public void setSpecialisation(String specialisation) { this.specialisation = specialisation; }
    public String getCertificationCredentials() { return certificationCredentials; }
    public void setCertificationCredentials(String certificationCredentials) { this.certificationCredentials = certificationCredentials; }
    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public int getPersonId() { return personId; }
    public void setPersonId(int personId) { this.personId = personId; }
}
