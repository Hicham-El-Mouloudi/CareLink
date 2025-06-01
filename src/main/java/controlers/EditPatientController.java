package controlers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Patient;
import models.PatientDAO;
import models.Person;
import models.PersonDAO;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Traitement;
import models.TraitementDAO;
import models.Appointment;
import models.AppointmentDAO;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditPatientController implements Initializable {
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
    private TableColumn<Traitement, Void> actionsColumn;
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

    private Patient patient;
    private Person person;
    private PatientDAO patientDAO = new PatientDAO();
    private PersonDAO personDAO = new PersonDAO();
    // 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        traitementTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        rendezVousTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setupActionsColumn();
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
        System.out.println("EditPatientController : The selected Patient : " + patient.getId());
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
                    if ("Male".equalsIgnoreCase(person.getGender())) {
                        hommeRadio.setSelected(true);
                        femmeRadio.setSelected(false);
                    } else if ("Female".equalsIgnoreCase(person.getGender())) {
                        hommeRadio.setSelected(false);
                        femmeRadio.setSelected(true);
                    } else {
                        hommeRadio.setSelected(false);
                        femmeRadio.setSelected(false);
                    }
                    // Simple age calculation
                    if (ageField != null && person.getDateOfBirth() != null) {
                        long ageInMillis = System.currentTimeMillis() - person.getDateOfBirth().getTime();
                        int age = (int) (ageInMillis / (1000L * 60 * 60 * 24 * 365));
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
            traitementListLabel.setText("List des Traitements : (" + traitements.size() + ")");
            // Set up columns if not already set
            if (traitementIdColumn != null) traitementIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            if (traitementDescColumn != null) traitementDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            if (traitementStartColumn != null) traitementStartColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            if (traitementStatus != null) traitementStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            if (traitementTableView != null) traitementTableView.setItems(traitements);
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

    /**
     * Calculate date of birth from age
     * @param age The age in years
     * @return Date object representing the date of birth
     */
    private java.util.Date calculateDateOfBirth(int age) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.add(java.util.Calendar.YEAR, -age);  // Subtract age from current date
        return calendar.getTime();
    }    @FXML
    private void handleSave() {
        if (patient != null && person != null) {
            person.setFullName(fullNameField.getText());
            System.out.println("EditPatientController : saving person name : " + fullNameField.getText());
            person.setEmail(emailField.getText());
            System.out.println("EditPatientController : saving person email : " + emailField.getText());
            person.setGender(hommeRadio.isSelected() ? "Male" : (femmeRadio.isSelected() ? "Female" : ""));
            System.out.println("EditPatientController : saving person sex  : " + (hommeRadio.isSelected() ? "Male" : (femmeRadio.isSelected() ? "Female" : "")));
              // Save age
            try {
                int age = Integer.parseInt(ageField.getText().trim());
                java.util.Date dateOfBirth = calculateDateOfBirth(age);
                person.setDateOfBirth(dateOfBirth);
                System.out.println("EditPatientController : saving person age : " + age);
            } catch (NumberFormatException e) {
                System.out.println("EditPatientController : invalid age format");
                Alert invalidAge = new Alert(AlertType.INFORMATION);
                invalidAge.setTitle("Erreur de saisie");
                invalidAge.setHeaderText(null);
                invalidAge.setContentText("L'âge saisi est invalide. Veuillez entrer une valeur correcte.");
                invalidAge.showAndWait();
            }
            
            patient.setMedicalConditions(medicalRemarksField.getText());
            System.out.println("EditPatientController : saving patient medical conditions  : " + medicalRemarksField.getText());
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

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(column -> new TableCell<Traitement, Void>() {
            private final Button modifyButton = new Button("Modify");

            {
                // Default: teal gradient (shades of #2a9d8f)
                modifyButton.setStyle(""
                    + "-fx-background-color: linear-gradient(to right, #2a9d8f, #21867a);"
                    + "-fx-text-fill: #fff;"
                    + "-fx-font-weight: bold;"
                    + "-fx-background-radius: 8;"
                    + "-fx-border-radius: 8;"
                    + "-fx-padding: 6 18;"
                    + "-fx-cursor: hand;"
                    + "-fx-font-size: 14px;"
                    + "-fx-effect: dropshadow(gaussian, #2a9d8f, 3, 0.15, 0, 1);"
                    + "-fx-border-color: #21867a;"
                    + "-fx-border-width: 1;"
                );
                // Hover: darker teal gradient (shades of #2a9d8f)
                modifyButton.setOnMouseEntered(e -> modifyButton.setStyle(""
                    + "-fx-background-color: linear-gradient(to right, #21867a, #176055);"
                    + "-fx-text-fill: #fff;"
                    + "-fx-font-weight: bold;"
                    + "-fx-background-radius: 8;"
                    + "-fx-border-radius: 8;"
                    + "-fx-padding: 6 18;"
                    + "-fx-cursor: hand;"
                    + "-fx-font-size: 14px;"
                    + "-fx-effect: dropshadow(gaussian, #176055, 3, 0.15, 0, 1);"
                    + "-fx-border-color: #176055;"
                    + "-fx-border-width: 1;"
                ));
                modifyButton.setOnMouseExited(e -> modifyButton.setStyle(""
                    + "-fx-background-color: linear-gradient(to right, #2a9d8f, #21867a);"
                    + "-fx-text-fill: #fff;"
                    + "-fx-font-weight: bold;"
                    + "-fx-background-radius: 8;"
                    + "-fx-border-radius: 8;"
                    + "-fx-padding: 6 18;"
                    + "-fx-cursor: hand;"
                    + "-fx-font-size: 14px;"
                    + "-fx-effect: dropshadow(gaussian, #2a9d8f, 3, 0.15, 0, 1);"
                    + "-fx-border-color: #21867a;"
                    + "-fx-border-width: 1;"
                ));
                modifyButton.setOnAction(event -> {
                    Traitement traitement = getTableView().getItems().get(getIndex());
                    openModifyTraitement(traitement);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(modifyButton);
                }
            }
        });
    }

    private void openModifyTraitement(Traitement traitement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditTraitementView.fxml"));
            Parent root = loader.load();
            
            EditTraitementController controller = loader.getController();
            controller.setSelectedTraitement(traitement);
            
            Stage stage = new Stage();
            stage.setMinHeight(700);
            stage.setMinWidth(500);
            stage.setTitle("Modifier Traitement");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadTraitementsForPatient(patient.getId());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("EditPatientController : Error loading  /views/EditTraitementView.fxml");
        }
    }
}
