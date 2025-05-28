/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class TraitementController implements Initializable {

    @FXML
    private ScrollPane traitementScrollPane;
    @FXML
    private VBox traitementBodyVBox;
    @FXML
    private Label detailsLabel;
    @FXML
    private GridPane traitementGrid;
    @FXML
    private TextField idField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField typeField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField notesField;
    @FXML
    private TextField raisonField;
    @FXML
    private TextField nextAppointmentField;
    @FXML
    private TextField patientIdField;
    @FXML
    private TextField doctorIdField;
    @FXML
    private TextField prescriptionIdField;
    @FXML
    private TextField medicalRecordIdField;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private RadioButton followUpYesRadio;
    @FXML
    private RadioButton followUpNoRadio;

    private ToggleGroup followUpGroup;
    @FXML
    private Button enregistrerButton;
    @FXML
    private VBox choosePatientPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (statusChoiceBox != null) {
            statusChoiceBox.getItems().addAll("En Cours", "Finie", "arrêté");
            statusChoiceBox.getSelectionModel().selectFirst();
        }
        followUpGroup = new ToggleGroup();
        if (followUpYesRadio != null && followUpNoRadio != null) {
            followUpYesRadio.setToggleGroup(followUpGroup);
            followUpNoRadio.setToggleGroup(followUpGroup);
            followUpNoRadio.setSelected(true); // Default to 'Non'
        }
        // TODO
    }

}
