package controlers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.PatientPerson;
import models.PatientPersonDAO;

public class PatientSearchController {
    @FXML private TableView<models.PatientPerson> patientsTable;
    @FXML private TableColumn<models.PatientPerson, Integer> idColumn;
    @FXML private TableColumn<models.PatientPerson, String> fullNameColumn;
    @FXML private TableColumn<models.PatientPerson, String> emailColumn;
    @FXML private TableColumn<models.PatientPerson, Integer> ageColumn;
    @FXML private TableColumn<models.PatientPerson, String> sexColumn;
    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> filterChoiceBox;

    private int selectedPatientId = -1;

    public int getSelectedPatientId() {
        return selectedPatientId;
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));

        filterChoiceBox.setItems(FXCollections.observableArrayList("By Full Name (Nom Complet)", "By Email", "By Age", "By Sex"));
        filterChoiceBox.getSelectionModel().selectFirst();
        try {
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() throws Exception {
        PatientPersonDAO dao = new PatientPersonDAO();
        patientsTable.setItems(FXCollections.observableArrayList(dao.getAllPatientPersons()));
    }

    @FXML
    public void search() {
        // Use your existing search logic here
        // Or move it into a shared utility class if reused
    }

    @FXML
    public void selectPatient() {
        PatientPerson selected = patientsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selectedPatientId = selected.getId();
            closeWindow();
        }
    }

    @FXML
    public void closeWindow() {
        ((Stage) searchField.getScene().getWindow()).close();
    }
}

