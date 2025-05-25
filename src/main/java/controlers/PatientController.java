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
import javafx.stage.Stage;
import models.Patient;
import models.PatientDAO;
import models.PersonDAO;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class PatientController implements Initializable {

    @FXML
    private HBox rootHBox;
    @FXML
    private VBox leftVBox;
    @FXML
    private HBox searchBarHBox;
    @FXML
    private TextField searchField;
    @FXML
    private Button clearSearchButton;
    @FXML
    private TableView<Patient> patientsTable;
    @FXML
    private TableColumn<Patient, String> nameColumn;
    @FXML
    private TableColumn<Patient, String> treatmentColumn;
    @FXML
    private TableColumn<Patient, Void> actionsColumn;
    @FXML
    private Button Search;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Show all patients in the table
        loadPatients();
        setupActionsColumn();
    }

    private void loadPatients() {
        try {
            PatientDAO patientDao = new PatientDAO();
            PersonDAO personDAO = new PersonDAO();
            ObservableList<Patient> patients = FXCollections.observableArrayList(patientDao.getAllPatients());
            patientsTable.setItems(patients);
            // Set cell value factories for patient columns using PersonDAO for name and age
            nameColumn.setCellValueFactory(cellData -> {
                try {
                    return new javafx.beans.property.SimpleStringProperty(
                        personDAO.getPersonById(cellData.getValue().getPersonId()).getFullName()
                    );
                } catch (Exception e) { // exception when no person is found
                    e.printStackTrace();
                    return new javafx.beans.property.SimpleStringProperty("");
                }
            });
            treatmentColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMedicalConditions())
            );

        } catch (Exception e) { // exception when no patients are found
            e.printStackTrace();
        }
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(new Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>>() {
            @Override
            public TableCell<Patient, Void> call(final TableColumn<Patient, Void> param) {
                return new TableCell<Patient, Void>() {
                    private final Button editButton = new Button("✎");
                    private final Button printButton = new Button("🖨");
                    private final Button deleteButton = new Button("✖");
                    {
                        editButton.setOnAction(event -> {
                            Patient patient = getTableView().getItems().get(getIndex());
                            openEditPatientDialog(patient);
                        });
                        printButton.setOnAction(event -> {
                            Patient patient = getTableView().getItems().get(getIndex());
                            printPatient(patient);
                        });
                        deleteButton.setOnAction(event -> {
                            Patient patient = getTableView().getItems().get(getIndex());
                            deletePatient(patient);
                        });
                    }
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(5, editButton, printButton, deleteButton);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
    }

    private void openEditPatientDialog(Patient patient) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditPatientView.fxml"));
            Parent root = loader.load();
            EditPatientController controller = loader.getController();
            controller.setPatient(patient);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Patient");
            stage.showAndWait();
            loadPatients(); // Refresh after edit
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printPatient(Patient patient) {
        // Implement print logic here
        System.out.println("Printing patient: " + patient.getId());
    }

    private void deletePatient(Patient patient) {
        try {
            PatientDAO patientDao = new PatientDAO();
            patientDao.deletePatient(patient.getId());
            loadPatients();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearSearch(ActionEvent event) {
        searchField.clear();
        loadPatients(); // Reload all patients
    }

    @FXML
    private void Search(ActionEvent event) {
        System.out.println("Searching for: " + searchField.getText());
    }
    
}
