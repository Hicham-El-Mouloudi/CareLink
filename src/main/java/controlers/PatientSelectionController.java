/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

// 
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
// 
import models.*;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class PatientSelectionController implements Initializable {

    @FXML
    private VBox thePatientSelectionForTraitement;
    @FXML
    private TableView<PatientPerson> patientTableView;
    @FXML
    private TableColumn<PatientPerson, String> fullNameColumn;
    @FXML
    private TableColumn<PatientPerson, String> emailColumn;
    @FXML
    private TableColumn<PatientPerson, String> sexColumn;
    @FXML
    private TableColumn<PatientPerson, Void> selectionColumn;
    @FXML
    private HBox searchFieldHBox;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;

    //Initializing the parent controller 
    TraitementController parentController;
    // Initializing the DAO
    PatientPersonDAO patientPersonDAO = new PatientPersonDAO();

    // parentController setter
    void setParentController(TraitementController parentController) {
        this.parentController = parentController;
        System.out.println("PatientSelectionController : TraitementController as parent set successfully");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Making the table flexible
        patientTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Initializing the 'selectionner' column factory
        initializeSelectionColumn();
        // Loading the Patients
        loadPatients();

        // Search button event
        searchButton.setOnAction(this::search);
        // Listenner that trigers 'search' function each time the Text in the search field is modified
        searchField.textProperty().addListener((observable, oldValue, newValue) -> search(null));
    }
    
    // 
    void loadPatients(){
        ObservableList<PatientPerson> lesPatients = FXCollections.observableArrayList(patientPersonDAO.getAllPatientPersons());
        // Initializing the TableView columns
        patientTableView.setItems(lesPatients);
        fullNameColumn.setCellValueFactory(lePatient -> new SimpleStringProperty(lePatient.getValue().getFullName()));
        emailColumn.setCellValueFactory(lePatient -> new SimpleStringProperty(lePatient.getValue().getEmail()));
        sexColumn.setCellValueFactory(lePatient -> new SimpleStringProperty(lePatient.getValue().getSex()));
    }
    /*
     * @brief This function initialize the Selection column in the table
    */
    private void initializeSelectionColumn() {
        // 
        selectionColumn.setCellFactory( new Callback<TableColumn<PatientPerson,Void>,TableCell<PatientPerson,Void>> () {
            @Override
            public TableCell<PatientPerson,Void> call(TableColumn<PatientPerson,Void> param) {
                return new TableCell<PatientPerson,Void>() {
                    // 
                    private Button selectButton = new Button("Selectionner");
                    {
                        selectButton.getStyleClass().add("selection-button");
                        selectButton.setStyle(
                            "-fx-background-color: #0077b6;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: normal;" +
                            "-fx-background-radius: 6;" +
                            "-fx-padding: 4 12 4 12;" +
                            "-fx-cursor: hand;" +
                            "-fx-font-size: 12px;" +
                            "-fx-border-color: transparent;" +
                            "-fx-effect: none;"
                        );
                        selectButton.setOnMouseEntered(e -> selectButton.setStyle(
                            "-fx-background-color: #005f87;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: normal;" +
                            "-fx-background-radius: 6;" +
                            "-fx-padding: 4 12 4 12;" +
                            "-fx-cursor: hand;" +
                            "-fx-font-size: 12px;" +
                            "-fx-border-color: transparent;" +
                            "-fx-effect: none;"
                        ));
                        selectButton.setOnMouseExited(e -> selectButton.setStyle(
                            "-fx-background-color: #0077b6;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: normal;" +
                            "-fx-background-radius: 6;" +
                            "-fx-padding: 4 12 4 12;" +
                            "-fx-cursor: hand;" +
                            "-fx-font-size: 12px;" +
                            "-fx-border-color: transparent;" +
                            "-fx-effect: none;"
                        ));
                        selectButton.setOnAction(event -> {
                            int id = getTableView().getItems().get(getIndex()).getId();
                            String patientName = getTableView().getItems().get(getIndex()).getFullName();
                            System.out.println("PatientSelectionController : Le patient : " + patientName + " is Selected !");
                            parentController.setPatientIdName(id, patientName);
                        });
                    }
                    @Override
                    protected void updateItem(Void item, boolean empty){
                        // 
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        }else {
                            setGraphic(selectButton);
                        }
                    }
                };
            }
        });
    }

    @FXML
    private void search(ActionEvent event) {
        // 
        List<PatientPerson> allPatients = patientPersonDAO.getAllPatientPersons();
        List<PatientPerson> patientsFiltre = new ArrayList<PatientPerson>();
        for (PatientPerson patientPerson : allPatients) {
            if (patientPerson.getFullName().contains(searchField.getText())) {
                patientsFiltre.add(patientPerson);
            }
        }
        ObservableList lesPatientsFiltre = FXCollections.observableArrayList(patientsFiltre);
        patientTableView.setItems(lesPatientsFiltre);
    }
    
}
