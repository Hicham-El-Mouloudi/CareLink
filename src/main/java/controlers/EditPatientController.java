package controlers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Patient;
import models.PatientDAO;
import models.Person;
import models.PersonDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Traitement;
import models.TraitementDAO;
import models.Appointment;
import models.AppointmentDAO;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditPatientController {
    private Patient patient;
    private Person person;
    private PatientDAO patientDAO = new PatientDAO();
    private PersonDAO personDAO = new PersonDAO();
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    @FXML
    private VBox editPatientBodyVBox;
    @FXML
    private HBox headerHBox;
    @FXML
    private Label editTitleLabel;
    @FXML
    private HBox mainContentHBox;
    @FXML
    private VBox formVBox;
    @FXML
    private GridPane formGridPane;
    @FXML
    private Label fullNameLabel;
    @FXML
    private TextField fullNameField;
    @FXML
    private Label ageLabel;
    @FXML
    private TextField ageField;
    @FXML
    private Label sexLabel;
    @FXML
    private HBox sexHBox;
    @FXML
    private RadioButton hommeRadio;
    @FXML
    private RadioButton femmeRadio;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField emailField;
    @FXML
    private Label telephoneLabel;
    @FXML
    private TextField telephoneField;
    @FXML
    private Label medicalRemarksLabel;
    @FXML
    private TextField medicalRemarksField;
    @FXML
    private VBox tablesVBox;
    @FXML
    private Label traitementListLabel;
    @FXML
    private ScrollPane traitementScrollPane;
    @FXML
    private TableView<Traitement> traitementTableView;
    @FXML
    private TableColumn<Traitement, Integer> traitementIdColumn;
    @FXML
    private TableColumn<Traitement, String> traitementDescColumn;
    @FXML
    private TableColumn<Traitement, java.util.Date> traitementStartColumn;
    @FXML
    private TableColumn<Traitement, String> traitementStatus;
    @FXML
    private Label rendezVousLabel;
    @FXML
    private ScrollPane rendezVousScrollPane;
    @FXML
    private TableView<Appointment> rendezVousTableView;
    @FXML
    private TableColumn<Appointment, Integer> rendezVousIdColumn;
    @FXML
    private TableColumn<Appointment, java.util.Date> rendezVousDateColumn;
    @FXML
    private TableColumn<Appointment, String> rendezVousEtatColumn;
    @FXML
    private HBox footerHBox;

    public void setPatient(Patient patient) {
        this.patient = patient;
        if (patient != null) {
            try {
                // Load the associated Person
                this.person = personDAO.getPersonById(patient.getPersonId());
                if (editTitleLabel != null && person != null) {
                    editTitleLabel.setText("Modifier Patient : " + person.getFullName());
                }
                if (person != null) {
                    fullNameField.setText(person.getFullName());
                    emailField.setText(person.getEmail());
                    // Sex
                    if ("Homme".equalsIgnoreCase(person.getGender())) {
                        hommeRadio.setSelected(true);
                        femmeRadio.setSelected(false);
                    } else if ("Femme".equalsIgnoreCase(person.getGender())) {
                        hommeRadio.setSelected(false);
                        femmeRadio.setSelected(true);
                    } else {
                        hommeRadio.setSelected(false);
                        femmeRadio.setSelected(false);
                    }
                    if (telephoneField != null && person.getInsuranceDetails() != null) {
                        telephoneField.setText(person.getInsuranceDetails()); 
                    }
                    // Set age if available (date of birth to age conversion if needed)
                    if (ageField != null && person.getDateOfBirth() != null) {
                        java.util.Calendar dob = java.util.Calendar.getInstance();
                        dob.setTime(person.getDateOfBirth());
                        java.util.Calendar today = java.util.Calendar.getInstance();
                        int age = today.get(java.util.Calendar.YEAR) - dob.get(java.util.Calendar.YEAR);
                        if (today.get(java.util.Calendar.DAY_OF_YEAR) < dob.get(java.util.Calendar.DAY_OF_YEAR)) {
                            age--;
                        }
                        ageField.setText(String.valueOf(age));
                    }
                }
                if (medicalRemarksField != null) {
                    medicalRemarksField.setText(patient.getMedicalConditions());
                }
                loadTraitementsForPatient(patient.getId());
                loadAppointmentsForPatient(patient.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadTraitementsForPatient(int patientId) {
        try {
            TraitementDAO traitementDAO = new TraitementDAO();
            ObservableList<Traitement> traitements = FXCollections.observableArrayList();
            for (Traitement t : traitementDAO.getAllTraitements()) {
                if (t.getPatientId() == patientId) {
                    traitements.add(t);
                }
            }
            // Set up columns if not already set
            if (traitementIdColumn != null) traitementIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            if (traitementDescColumn != null) traitementDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            if (traitementStartColumn != null) traitementStartColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            if (traitementStatus != null) traitementStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            if (traitementTableView != null) traitementTableView.setItems(traitements);
            if (traitementListLabel != null) traitementListLabel.setText("List des Traitements : (" + traitements.size() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAppointmentsForPatient(int patientId) {
        try {
            AppointmentDAO appointmentDAO = new AppointmentDAO();
            ObservableList<Appointment> appointments = FXCollections.observableArrayList();
            for (Appointment a : appointmentDAO.getAllAppointments()) {
                if (a.getPatientId() == patientId) {
                    appointments.add(a);
                }
            }
            if (rendezVousIdColumn != null) rendezVousIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            if (rendezVousDateColumn != null) rendezVousDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
            if (rendezVousEtatColumn != null) rendezVousEtatColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            if (rendezVousTableView != null) rendezVousTableView.setItems(appointments);
            if (rendezVousLabel != null) rendezVousLabel.setText("List Des Rendez-Vous : (" + appointments.size() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSave() {
        if (patient != null && person != null) {
            person.setFullName(fullNameField.getText());
            person.setEmail(emailField.getText());
            person.setGender(hommeRadio.isSelected() ? "Homme" : (femmeRadio.isSelected() ? "Femme" : ""));
            if (telephoneField != null) {
                person.setInsuranceDetails(telephoneField.getText());
            }
            if (medicalRemarksField != null) {
                patient.setMedicalConditions(medicalRemarksField.getText());
            }
            try {
                patientDAO.updatePatient(patient);
                personDAO.updatePerson(person);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
