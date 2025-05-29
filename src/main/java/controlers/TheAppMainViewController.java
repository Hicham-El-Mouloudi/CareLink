/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.fxml.*;
import java.io.IOException;
import javafx.scene.layout.BorderPane;


import models.*;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class TheAppMainViewController implements Initializable {

    @FXML
    private Button AccueilButton;
    @FXML
    private Button PatientsButton;
    @FXML
    private Button RendezVousButton;
    @FXML
    private Button TraitementsButton;
    @FXML
    private Button AideButton;
    @FXML
    private Region menuBar;
    @FXML
    private Pane bodyContent;
    @FXML
    private BorderPane mainView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // When the application starts, we load the home view
        navigateToHome();

        // navigating to the home view when the home button is clicked or on app start
        AccueilButton.setOnAction(event -> {
            navigateToHome();
        });
        // navigating to the patients view when the patients button is clicked
        PatientsButton.setOnAction(event -> {
            navigateToPatients();
        });
        // navigating to the traitements view when the traitements button is clicked
        TraitementsButton.setOnAction(event -> {
            navigateToTraitements();
        });
        // navigating to the rendez-vous view when the rendez-vous button is clicked
        RendezVousButton.setOnAction(event -> {
            navigateToRendezVous();
        });
        // navigating to the aide view when the aide button is clicked
        AideButton.setOnAction(event -> {
            navigateToAide();
        });
    }
    // ------------------------------------------------------------------------------------------------------
    // ---------------------------------------- NAVIGATION FUNCTIONS ----------------------------------------
    // ------------------------------------------------------------------------------------------------------
    // navigate to home
    private void navigateToHome() {
        try {
            Parent homeView = FXMLLoader.load(getClass().getResource("/views/HomeView.fxml"));
            mainView.setCenter(homeView);
        } catch (IOException e) {
            System.err.println("Error loading home view: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
    // navigate to patients
    private void navigateToPatients() {
        try {
            Parent patientsView = FXMLLoader.load(getClass().getResource("/views/PatientView.fxml"));
            mainView.setCenter(patientsView);
        } catch (IOException e) {
            System.err.println("Error loading patients view: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
    // navigate to traitements
    private void navigateToTraitements() {
        try {
            Parent traitementsView = FXMLLoader.load(getClass().getResource("/views/TraitementView.fxml"));
            mainView.setCenter(traitementsView);
        } catch (IOException e) {
            System.err.println("Error loading traitements view: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
    // navigate to rendez-vous
    private void navigateToRendezVous() {
        try {
            Parent rendezVousView = FXMLLoader.load(getClass().getResource("/views/RendezVous.fxml"));
            mainView.setCenter(rendezVousView);
        } catch (IOException e) {
            System.err.println("Error loading rendez-vous view: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
    // navigate to aide
    private void navigateToAide() {
        try {
            Parent aideView = FXMLLoader.load(getClass().getResource("/views/AideView.fxml"));
            mainView.setCenter(aideView);
        } catch (IOException e) {
            System.err.println("Error loading aide view: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
    // navigate to edit patient view
    public void navigateToEditPatientView(Patient patient) {
        try {
            FXMLLoader editLoader = new FXMLLoader(getClass().getResource("/views/EditPatientView.fxml"));
            Parent editRoot = editLoader.load();
            controlers.EditPatientController editController = editLoader.getController();
            editController.setPatient(patient);
            // Optionally set parent controller if needed:
            // editController.setParentController(this);
            setBodyContent((Pane) editRoot);
        } catch (IOException e) {
            System.err.println("Error loading edit patient view: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    public void setBodyContent(Pane bodyContent) {
        this.bodyContent = bodyContent;
    }
}
