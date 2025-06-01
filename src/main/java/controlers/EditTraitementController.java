/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlers;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.*;
/**
 * FXML Controller class
 *
 * @author Hicham El Mouloudi
 */
public class EditTraitementController implements Initializable {

    @FXML
    private DatePicker datePicker;    
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextArea notesField;
    @FXML
    private TextField raisonField;
    @FXML
    private RadioButton followUpYesRadio;
    @FXML
    private ToggleGroup followUpGroup;
    @FXML
    private RadioButton followUpNoRadio;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Traitement selectedTraitement;
    @FXML
    private Label titleLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label notesLabel;
    @FXML
    private Label raisonLabel;
    @FXML
    private Label followUpLabel;
    @FXML
    private HBox followUpContainer;
    @FXML
    private HBox buttonContainer;

    private PatientPersonDAO patientPersonDAO = new PatientPersonDAO();
      public void setSelectedTraitement(Traitement traitement) {
        this.selectedTraitement = traitement;
        
        if (traitement != null) {
        // Set Title
        titleLabel.setText("Modification Traitement Pour : "
                + patientPersonDAO.getPatientPersonByID(traitement.getPatientId()).getFullName());
        // Set date
        datePicker.setValue(traitement.getDate());

        // Set type and status in choice boxes
        typeChoiceBox.setValue(traitement.getType());
        statusChoiceBox.setValue(traitement.getStatus());

        // Set text fields
        descriptionField.setText(traitement.getDescription());
        notesField.setText(traitement.getNotesObservation());
        raisonField.setText(traitement.getRaisonForTraitement());

        // Set follow up radio buttons
        if (traitement.isFollowUpRequired()) {
            followUpYesRadio.setSelected(true);
        } else {
            followUpNoRadio.setSelected(true);
            }
        }
    }
    
    /**
     * Initializes the controller class.
     */    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize type choices
        typeChoiceBox.getItems().addAll(
            "Consultation",
            "Chirurgie",
            "Thérapie",
            "Suivi",
            "Autre"
        );
        
        // Initialize status choices
        statusChoiceBox.getItems().addAll("En Cours", "Finie", "arrêté");
    }    

    @FXML
    private void handleSave(ActionEvent event) {
        System.out.println("EditTraitementController : Saving Traitement");
        
        if (selectedTraitement != null) {
            // Update the Traitement object with values from form fields
            selectedTraitement.setDate(datePicker.getValue());
            selectedTraitement.setType(typeChoiceBox.getValue());
            selectedTraitement.setStatus(statusChoiceBox.getValue());
            selectedTraitement.setDescription(descriptionField.getText());
            selectedTraitement.setNotesObservation(notesField.getText());
            selectedTraitement.setRaisonForTraitement(raisonField.getText());
            selectedTraitement.setFollowUpRequired(followUpYesRadio.isSelected());

            // Save changes to database
            TraitementDAO traitementDAO = new TraitementDAO();
            boolean success = traitementDAO.updateTraitement(selectedTraitement);
            
            if (success) {
                // Show success alert
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Le traitement a été modifié avec succès.");
                successAlert.showAndWait();
                
                // Close the window
                close();
            } else {
                // Show error alert
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Une erreur s'est produite lors de la modification du traitement.");
                errorAlert.showAndWait();
            }
        }
    }
    
    @FXML
    private void handleCancel(ActionEvent event) {
        System.out.println("EditTraitementController : Closing The window");
        close();
    }

    private void close() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
}
