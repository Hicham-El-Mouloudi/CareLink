package controlers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.PatientPerson;
import models.PatientPersonDAO;
/**
 * search a personPatient in a pop up and {@code return the ID} after the search with the 
 * methode {@link #getSelectedPatientId()}
 * <p> this class controles the stage that will pop up when the user want to search
 * a personPatient and get his id 
 * <p> called in {@link EditAppointmentController#openPatientSearch()}
 * <p> also called in {@link AddAppointmentController#openPatientSearch()}
 * @author amine 
 */
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
    /**
     * 
     * @return the ID of the patient selected 
     */
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
    /**
     * fill the table with all the patient 
     * @throws Exception
     */
    private void refreshTable() throws Exception {
        PatientPersonDAO dao = new PatientPersonDAO();
        patientsTable.setItems(FXCollections.observableArrayList(dao.getAllPatientPersons()));
    }

    @FXML
    /**
     * filtered search logic
     * @author hicham 
     */
    public void search() {
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

    @FXML
    /**
     * select a patient with right lick and click the select button
     */
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

