/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.SQLException;
import java.util.List;

import models.DoctorDAO;
import models.Person;
import models.PersonDAO;


public class Doctor {
	 public enum enMode { addnew, update }

	    private int id;
	    private String specialisation;
	    private String certification;
	    private String schedule;
	    private String department;
	    private String username;
	    private String password;
	    private Person personalInfo;
	    private enMode mode;

	    // Constructor for new doctor
	    public Doctor() {
	    	
	    	personalInfo= new Person();
	    	
	        this.mode = enMode.addnew;
	    }

	    
	    // Constructor for existing doctor (update/Find/get all...)
	    public Doctor(int id, String specialisation, String certification,
	                     String schedule, String department, int personId) {
	        this.id = id;
	        this.specialisation = specialisation;
	        this.certification = certification;
	        this.schedule = schedule;
	        this.department = department;
	        
	        
	        //get personal information
	        PersonDAO pdao=new PersonDAO();
	        try {
				this.personalInfo = pdao.getPersonById(personId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
	        this.mode = enMode.update;
	    }

	    
	    // Getters and Setters
	    public int getId() { return id; }
	    public String getSpecialisation() { return specialisation; }
	    public String getCertification() { return certification; }
	    public String getSchedule() { return schedule; }
	    public String getDepartment() { return department; }
	    public Person getPersonalInfo() {return personalInfo;}
	    
	    public void setId(int id) { this.id = id; }
	    public void setSpecialisation(String specialisation) { this.specialisation = specialisation; }
	    public void setCertification(String certification) { this.certification = certification; }
	    public void setSchedule(String schedule) { this.schedule = schedule; }
	    public void setDepartment(String department) { this.department = department; }
	    public void setPersonalInfo(Person pInfo) { this.personalInfo = pInfo; }

	    
	    
	   // Static method: Find doctor by ID
	    public static Doctor find(int id) {
	        return DoctorDAO.findById(id);
	    }

	    // Static method: Find doctor by username and password
	    public static Doctor find(String username, String password) {
	    	
	        return DoctorDAO.findByUsernameAndPassword(username, password);
	    }

	    // Static method: Check if doctor exists
	    public static boolean isExist(int id) {
	        return DoctorDAO.isExist(id);
	    }
	    
	    //static method : get all doctors
	    
	    public static List<Doctor> GetAll() {
	    	
	    	return DoctorDAO.getAllDoctors();
	    	
	    }
	    
	    
	    
	    
	    // Save method
	    public boolean save() {
	    	
	        if (this.mode == enMode.addnew) {
	            return DoctorDAO.addNewDoctor(this);
	        } else {
	            return DoctorDAO.updateDoctor(this);
	        }
	    }


		public String getUsername() {
			return username;
		}


		public void setUsername(String username) {
			this.username = username;
		}


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}
}
