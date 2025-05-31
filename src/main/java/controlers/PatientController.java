/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
// custom classes
import models.*;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class PatientController implements Initializable {

    @FXML
    private HBox searchBarHBox;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<PatientPerson> patientsTable;
    @FXML
    private TableColumn<PatientPerson, String> idColumn;
    @FXML
    private TableColumn<PatientPerson, String> emailColumn;
    @FXML
    private TableColumn<PatientPerson, String> nameColumn;
    @FXML
    private TableColumn<PatientPerson, String> ageColumn;
    @FXML
    private TableColumn<PatientPerson, String> sexColumn;
    @FXML
    private TableColumn<PatientPerson, String> treatmentColumn;
    @FXML
    private TableColumn<PatientPerson, Void> actionsColumn;
    @FXML
    private VBox bodyVBox;
    @FXML
    private HBox searchFieldHBox;
    @FXML
    private Button searchButton;
    @FXML
    private HBox filterHBox;
    @FXML
    private Label filterLabel;
    @FXML
    private ChoiceBox<String> filterChoiceBox;
    @FXML
    private ScrollPane tableScrollPane;
    @FXML
    private Button addPatientButton;
    @FXML
    private Button deleteAllPatientsButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set the column resize policy to avoid extra columns
        patientsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Configure the actions column before loading data
        setupActionsColumn();

        // Load all patients into the table
        loadPatients();

        // Add filter choices
        filterChoiceBox.getItems().addAll(
            "None",
            "By Email",
            "By Full Name (Nom Complet)",
            "By Age",
            "By Sex",
            "By Traitements (Treatments)"
        );
        filterChoiceBox.getSelectionModel().selectFirst();

        // Set up search button action
        searchButton.setOnAction(this::Search);

        // Launch search on every text change in the search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> Search(null));

        // Set up add patient button action
        if (addPatientButton != null) {
            addPatientButton.setOnAction(e -> addPatient());
        }
        // Set up delete all patients button action
        if (deleteAllPatientsButton != null) {
            deleteAllPatientsButton.setOnAction(e -> deleteAllPatients());
        }
    }

    private void loadPatients() {
        try {
            models.PatientPersonDAO patientPersonDAO = new models.PatientPersonDAO();
            ObservableList<models.PatientPerson> patientPersons = FXCollections.observableArrayList(patientPersonDAO.getAllPatientPersons());
            patientsTable.setItems(patientPersons);
            idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
            emailColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
            nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFullName()));
            ageColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getAge())));
            sexColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSex()));
            treatmentColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMedicalConditions()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(new Callback<TableColumn<PatientPerson, Void>, TableCell<PatientPerson, Void>>() {
            @Override
            public TableCell<PatientPerson, Void> call(final TableColumn<PatientPerson, Void> param) {
                return new TableCell<PatientPerson, Void>() {
                    private final Button previewButton = new Button("Preview&Update");
                    private final Button deleteButton = new Button("Delete");
                    {
                        // Embedded styling for previewButton
                        previewButton.setStyle("-fx-background-color: #1b7895; -fx-text-fill: white; -fx-background-radius: 10; -fx-font-weight: bold; -fx-padding: 5 15 5 15; -fx-cursor: hand; -fx-font-size: 13px; -fx-border-color: #1b7895; -fx-border-radius: 10; -fx-border-width: 1;");
                        previewButton.setOnMouseEntered(e -> previewButton.setStyle("-fx-background-color: #15677d; -fx-text-fill: white; -fx-background-radius: 10; -fx-font-weight: bold; -fx-padding: 5 15 5 15; -fx-cursor: hand; -fx-font-size: 13px; -fx-border-color: #1b7895; -fx-border-radius: 10; -fx-border-width: 1;"));
                        previewButton.setOnMouseExited(e -> previewButton.setStyle("-fx-background-color: #1b7895; -fx-text-fill: white; -fx-background-radius: 10; -fx-font-weight: bold; -fx-padding: 5 15 5 15; -fx-cursor: hand; -fx-font-size: 13px; -fx-border-color: #1b7895; -fx-border-radius: 10; -fx-border-width: 1;"));
                        // Embedded styling for deleteButton
                        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 10; -fx-font-weight: bold; -fx-padding: 5 15 5 15; -fx-cursor: hand; -fx-font-size: 13px; -fx-border-color: #e74c3c; -fx-border-radius: 10; -fx-border-width: 1;");
                        deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-background-radius: 10; -fx-font-weight: bold; -fx-padding: 5 15 5 15; -fx-cursor: hand; -fx-font-size: 13px; -fx-border-color: #e74c3c; -fx-border-radius: 10; -fx-border-width: 1;"));
                        deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 10; -fx-font-weight: bold; -fx-padding: 5 15 5 15; -fx-cursor: hand; -fx-font-size: 13px; -fx-border-color: #e74c3c; -fx-border-radius: 10; -fx-border-width: 1;"));
                        previewButton.setOnAction(event -> {
                            PatientPerson patientPerson = getTableView().getItems().get(getIndex());
                            previewPatient(patientPerson);
                        });
                        deleteButton.setOnAction(event -> {
                            PatientPerson patientPerson = getTableView().getItems().get(getIndex());
                            deletePatientPerson(patientPerson);
                        });
                    }
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(5, previewButton, deleteButton);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
    }

    private void previewPatient(PatientPerson patientPerson) {
        try {
            PatientDAO patientDAO = new PatientDAO();
            Patient patient = patientDAO.getPatientById(patientPerson.getId());
            FXMLLoader editLoader = new FXMLLoader(getClass().getResource("/views/EditPatientView.fxml"));
            Parent editRoot = editLoader.load();
            EditPatientController editController = editLoader.getController();
            editController.setPatient(patient);
            // Show EditPatientView in a new window
            Stage editStage = new Stage();
            editStage.setTitle("Edit Patient");
            editStage.setScene(new Scene(editRoot));
            editStage.initOwner(patientsTable.getScene().getWindow());
            editStage.showAndWait();
            loadPatients(); // to refresh view
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deletePatientPerson(PatientPerson patientPerson) {
        try {
            PatientPersonDAO dao = new PatientPersonDAO();
            boolean success = dao.deletePatientPerson(patientPerson);
            if (success) {
                loadPatients();
            } else {
                System.out.println("Failed to delete patient and person.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Search(ActionEvent event) {
        String searchText = searchField.getText().trim().toLowerCase();
        String filter = (String) filterChoiceBox.getSelectionModel().getSelectedItem();
        try {
            models.PatientPersonDAO patientPersonDAO = new models.PatientPersonDAO();
            ObservableList<models.PatientPerson> allPatients = FXCollections.observableArrayList(patientPersonDAO.getAllPatientPersons());
            ObservableList<models.PatientPerson> filtered = FXCollections.observableArrayList();
            for (models.PatientPerson p : allPatients) {
                switch (filter) {
                    case "By Email":
                        if (p.getEmail() != null && p.getEmail().toLowerCase().contains(searchText)) filtered.add(p);
                        break;
                    case "By Full Name (Nom Complet)":
                        if (p.getFullName() != null && p.getFullName().toLowerCase().contains(searchText)) filtered.add(p);
                        break;
                    case "By Age":
                        if (String.valueOf(p.getAge()).contains(searchText)) filtered.add(p);
                        break;
                    case "By Sex":
                        if (p.getSex() != null && p.getSex().toLowerCase().contains(searchText)) filtered.add(p);
                        break;
                    case "By Traitements (Treatments)":
                        if (p.getMedicalConditions() != null && p.getMedicalConditions().toLowerCase().contains(searchText)) filtered.add(p);
                        break;
                    default:
                        filtered.add(p);
                }
            }
            patientsTable.setItems(filtered);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPatient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddPatientView.fxml"));
            Parent root = loader.load();
            Stage addStage = new Stage();
            addStage.setTitle("Ajouter un Patient");
            addStage.setScene(new Scene(root));
            addStage.initOwner(patientsTable.getScene().getWindow());
            addStage.showAndWait();
            loadPatients(); // Refresh table after adding
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteAllPatients() {
        try {
            PatientPersonDAO dao = new PatientPersonDAO();
            boolean success = dao.deleteAllPatientPersons();
            if (success) {
                loadPatients();
            } else {
                System.out.println("Failed to delete all patients.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
