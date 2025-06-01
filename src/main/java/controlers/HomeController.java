/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import models.*;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class HomeController implements Initializable {

    @FXML
    private Label numberOfPatients;
    @FXML
    private Label numberOfTraitementsEnCours;
    @FXML
    private Label numberOfAppointementsForToday;
     @FXML
    private Label currentDoctor;

    PatientDAO patientDAO = new PatientDAO();
    TraitementDAO traitementDAO = new TraitementDAO();
    AppointmentDAO appointmentDAO = new AppointmentDAO();

    // The connected doctor
    private int currentDoctorID;
    public void setCurrentDoctorID(int currentDoctorID) {
        this.currentDoctorID = currentDoctorID;
        Doctor doctor = DoctorDAO.getDoctorById(this.currentDoctorID);
        if (doctor == null) {
            currentDoctor.setText("Connecté en tant que: Inconnu (ID: " + this.currentDoctorID + ")");
            System.err.println("HomeController: No doctor found with ID: " + this.currentDoctorID);
            return;
        }
        currentDoctor.setText("Connecté en tant que: " + doctor.getPersonalInfo().getFullName());
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // initialize the labels with the number of patients, treatments and appointments
        try{
            numberOfPatients.setText(String.valueOf(patientDAO.getNumberOfPatients()));
            numberOfTraitementsEnCours.setText(String.valueOf(traitementDAO.getNumberOfActiveTraitements()));
            numberOfAppointementsForToday.setText(String.valueOf(appointmentDAO.getNumberOfAppointmentsForToday()));
        }catch(Exception e){
            System.out.println("HomeController : Error While initializing : " + e.getMessage());
        }
    }    
    
}
