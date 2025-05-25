/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author lenovo
 */
public class Person {
    private int id;
    private String fullName;
    private String gender;
    private String email;
    private java.util.Date dateOfBirth;
    private String insuranceDetails;

    public Person(int id, String fullName, String gender, String email, java.util.Date dateOfBirth, String insuranceDetails) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.insuranceDetails = insuranceDetails;
    }
    public Person(String fullName, String gender, String email, java.util.Date dateOfBirth, String insuranceDetails) {
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.insuranceDetails = insuranceDetails;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public java.util.Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(java.util.Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getInsuranceDetails() { return insuranceDetails; }
    public void setInsuranceDetails(String insuranceDetails) { this.insuranceDetails = insuranceDetails; }
}
