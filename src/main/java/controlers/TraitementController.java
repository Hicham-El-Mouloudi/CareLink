/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Date;
import models.*;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class TraitementController implements Initializable {
    // 
    @FXML
    private ScrollPane traitementScrollPane;
    @FXML
    private VBox traitementBodyVBox;
    @FXML
    private Label detailsLabel;
    @FXML
    private GridPane traitementGrid;
    @FXML
    private DatePicker datePicker;    
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextArea notesField;
    @FXML
    private TextField raisonField;
    @FXML
    private Button patientIdField;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private RadioButton followUpYesRadio;
    @FXML
    private RadioButton followUpNoRadio;
    @FXML
    private ToggleGroup followUpGroup;
    @FXML
    private Button enregistrerButton;
    @FXML
    private VBox prescriptionBodyVBox;
    @FXML
    private Label prescriptionDetailsLabel;
    @FXML
    private GridPane prescriptionTraitementGrid;
    @FXML
    private TextArea prescriptionDosageInstructionField;
    @FXML
    private TextField prescriptionDurationField;
    @FXML
    private TextField prescriptionNameField;
    @FXML
    private TextArea prescriptionNotesField;
    @FXML
    private BorderPane traitementsView;
    @FXML
    private Label patientSelectionLabel;
    @FXML
    private Label appointmentSelectionLabel;
    @FXML
    private Button appointmentIdField;

    // Initilizing DAOs
    TraitementDAO traitementDAO = new TraitementDAO();
    // This is the child controller of the patientSelection view in order ensure communication between both views
    PatientSelectionController childController;
    // The active doctor
    int doctorId;
    // This variavle is set by the 'PatientSelectionController' class to identify a patient
    int patientId = -1;
    String patientName;
    public void setPatientIdName(int id, String patientName){
        this.patientId = id;
        this.patientName = patientName;
        System.out.println("TraitementController : Selected Patient    Id : " + id + "\t Name : " + patientName);

        // Displaying the name selected
        patientSelectionLabel.setText(patientName);
        // Displaying the name selected
        patientIdField.setText("Changer Le Patient");
        // Closing the patient selection view 
        traitementsView.setRight(null);
    }

    // Set appointment info from AppointmentSelectionController
    private int nextAppointmentId;
    private java.util.Date appointmentDateTime;
    private String status;
    public void setNextAppointmentIdAndInfo(int id, java.util.Date dateTime, String status) {
        this.nextAppointmentId = id;
        this.appointmentDateTime = dateTime;
        this.status = status;
        System.out.println("TraitementController : Selected Appointment    Id : " + id + "\t DateTime : " + dateTime + "\t Status : " + status);
        appointmentSelectionLabel.setText("le " + appointmentDateTime + " " + status);
        // Close the appointment selection view
        traitementsView.setRight(null);
        // Changeing the Text of the button 'appointmentIdField'
        appointmentIdField.setText("Changer Le Rendez-Vous");
    }
    /**
     * Initializes the controller class.
     */    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
        if (statusChoiceBox != null) {
            statusChoiceBox.getItems().addAll("En Cours", "Finie", "arrêté");
            statusChoiceBox.getSelectionModel().selectFirst();
        }
        
        // Initialize type choices
        if (typeChoiceBox != null) {
            typeChoiceBox.getItems().addAll(
                "Consultation",
                "Chirurgie",
                "Thérapie",
                "Suivi",
                "Autre"
            );
            typeChoiceBox.getSelectionModel().selectFirst();
        }
        
        followUpGroup = new ToggleGroup();
        if (followUpYesRadio != null && followUpNoRadio != null) {
            followUpYesRadio.setToggleGroup(followUpGroup);
            followUpNoRadio.setToggleGroup(followUpGroup);
            followUpNoRadio.setSelected(true); // Default to 'Non'
        }
    }

    @FXML
    private void openPatientChoice(ActionEvent event) {
        try {
            // Checking if the Choice view is already open, if yes we close the view
            if (traitementsView.getRight() != null) {
                System.out.println("TraitementController : Already opened view 'patientSelectionView' -> close it");
                traitementsView.setRight(null);
                return;
            }
            // Trying loading the view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/patientSelectionView.fxml"));
            Parent patientChoiceView = loader.load() ;
            // Setting the link between the two views
            childController = (PatientSelectionController)loader.getController();
            childController.setParentController(this);
            // 
            traitementsView.setRight(patientChoiceView);
        }catch (IOException e) {
            System.err.println("TraitementController : Error laoding the patientSelectionView.fxml view ");
        }
    }

    @FXML
    private void openAppointmentChoice(ActionEvent event) {
        try {
            if(patientId == -1) {
                // Display a warnning 
                Alert needForSelectionOfPatientAlert = new Alert(AlertType.INFORMATION);
                needForSelectionOfPatientAlert.setTitle("Information requise");
                needForSelectionOfPatientAlert.setHeaderText(null);
                needForSelectionOfPatientAlert.setContentText("Veuillez d'abord sélectionner un patient.");
                needForSelectionOfPatientAlert.showAndWait();
                return;
            }
            // Checking if the Choice view is already open, if yes we close the view
            if (traitementsView.getRight() != null) {
                System.out.println("TraitementController : Already opened view 'AppointmentSelectionView' -> close it");
                traitementsView.setRight(null);
                return;
            }
            // Loading the appointement of the selected Patient in the 'patientId' and if it's null -> diplay Warnning
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AppointmentSelectionView.fxml"));
            Parent appointmentChoiceView = loader.load();
            AppointmentSelectionController childAppointmentController = loader.getController();
            childAppointmentController.setSelectedPatientID(patientId); // set The Patient Context
            childAppointmentController.setParentController(this);
            traitementsView.setRight(appointmentChoiceView);
        } catch (IOException e) {
            System.err.println("TraitementController : Error loading the AppointmentSelectionView.fxml view");
        }
    }

    @FXML
    private void enregistrerTraitement(ActionEvent event){
        // The User need to select a patient -> if not raise an alert
        if (patientSelectionLabel.getText().equals("Choisissez Le Patient Concerné")){
            Alert needForPatientAlert = new Alert(AlertType.WARNING);
            needForPatientAlert.setHeaderText(null);
            needForPatientAlert.setTitle("Patient non spécifié");
            needForPatientAlert.setContentText("Veuillez sélectionner un patient avant d’enregistrer le traitement.");
            needForPatientAlert.showAndWait();
            return;
        }

        // Initialize The New prescription
        Prescription newPrescription = new Prescription(Date.valueOf(datePicker.getValue()), doctorId);
        // Initialize the new medication
        Medication newMedication = new Medication(prescriptionNameField.getText(), prescriptionNotesField.getText());
        // Initialize the new prescriptionMedication ---------> Note the  'prescriptionID' and  'medicationID' are set inside the addNewPrescription func : 0 0 default
        Prescription_Medication prescription_Medication = new Prescription_Medication(prescriptionDosageInstructionField.getText(), prescriptionDurationField.getText(), 0, 0);
        // Saving 
        int prescriptionID = traitementDAO.addNewPrescription(newPrescription, newMedication, prescription_Medication);

        // Initializing the Traitement 
        Traitement newTraitement = new Traitement(
            datePicker.getValue(),
            descriptionField.getText(),
            notesField.getText(),
            raisonField.getText(),
            followUpYesRadio.isSelected(),
            nextAppointmentId,
            statusChoiceBox.getValue(),            
            typeChoiceBox.getValue(),
            patientId,
            0,
            prescriptionID
        );
        // Saving th Traitement
        int traitementID = traitementDAO.insertTraitement(newTraitement);
        System.err.println("TraitementController : Saving Traitement Successsfully id = " + traitementID);
        
        // Clearing The view
        successfullSave();
    }

    public void successfullSave() {
        // Displaying this alert util it's exited then we clear all fields
        Alert needForPatientAlert = new Alert(AlertType.INFORMATION);
        needForPatientAlert.setHeaderText(null);
        needForPatientAlert.setTitle("Traitement enregistré");
        needForPatientAlert.setContentText("Le traitement a été enregistré avec succès.");
        needForPatientAlert.showAndWait();
        // Clearing Fields        
        datePicker.setValue(null);
        typeChoiceBox.getSelectionModel().selectFirst();
        descriptionField.clear();
        notesField.clear();
        raisonField.clear();
        statusChoiceBox.getSelectionModel().selectFirst();
        followUpNoRadio.setSelected(true);
        patientSelectionLabel.setText("Choisissez Le Patient Concerné");
        patientIdField.setText("Choisir Un Patient");
        prescriptionDosageInstructionField.clear();
        prescriptionDurationField.clear();
        prescriptionNameField.clear();
        prescriptionNotesField.clear();
        appointmentSelectionLabel.setText("Choisissez Le Rendez-vous");
        appointmentIdField.setText("Choisir Un Rendez-vous");
        patientId = -1;
    }
}
