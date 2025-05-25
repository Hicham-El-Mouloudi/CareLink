/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author lenovo
 */
public class Medication {
    private int id;
    private String name;
    private String notes;

    public Medication(int id, String name, String notes) {
        this.id = id;
        this.name = name;
        this.notes = notes;
    }
    public Medication(String name, String notes) {
        this.name = name;
        this.notes = notes;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
