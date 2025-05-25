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
    

    PatientDAO patientDAO = new PatientDAO();
    TraitementDAO traitementDAO = new TraitementDAO();
    AppointmentDAO appointmentDAO = new AppointmentDAO();
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
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }    
    
}
