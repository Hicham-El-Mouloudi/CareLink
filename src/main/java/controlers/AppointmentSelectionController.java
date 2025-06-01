package controlers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.Appointment;
import models.AppointmentDAO;
import models.PatientPerson;
import models.PatientPersonDAO;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

public class AppointmentSelectionController implements Initializable {
    @FXML
    private VBox theAppointmentSelectionForTraitement;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, String> dateTimeColumn;
    @FXML
    private TableColumn<Appointment, String> reasonColumn;
    @FXML
    private TableColumn<Appointment, String> statusColumn;
    @FXML
    private TableColumn<Appointment, Void> selectColumn;
    @FXML
    private HBox searchFieldHBox;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Label thePatientNameLabel;

    // 
    TraitementController parentController;
    // The selected Patient to which select appointment. set by "setSelectedPatientID"
    int selectedPatientID = -1; // -1 is used in the phase of initialization and the 'loadAppointments' is called -> we just load all appointements untill the 'selectedPatientID' is set using 'setSelectedPatientID' where we reload
    // 
    PatientPersonDAO patientPersonDAO = new PatientPersonDAO();
    AppointmentDAO appointmentDAO = new AppointmentDAO();
    void setParentController(TraitementController parentController) {
        this.parentController = parentController;
        System.out.println("AppointmentSelectionController : TraitementController as parent set successfully");
    }
    // 
    public void setSelectedPatientID(int id) {
        this.selectedPatientID = id;
        loadAppointments(); // to filter to only the realated ones to the selectedPatient
        PatientPerson theSelectedPatient = patientPersonDAO.getPatientPersonByID(selectedPatientID);
        thePatientNameLabel.setText("Les Rendez-Vous Pour : " + theSelectedPatient.getFullName());
        System.out.println("AppointmentSelectionController : The concerned Patient for appointments has id =" + selectedPatientID);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        initializeSelectColumn();
        loadAppointments();
        searchButton.setOnAction(this::search);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> search(null));
    }

    void loadAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList(
            // Check if the 'selectedPatientID' is set or not if not yet load all entries
            (selectedPatientID != -1)?appointmentDAO.getAllAppointmentsOfPatient(selectedPatientID):appointmentDAO.getAllAppointments()
        );
        appointmentTableView.setItems(appointments);
        dateTimeColumn.setCellValueFactory(appt -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return new SimpleStringProperty(sdf.format(appt.getValue().getDateTime()));
        });
        reasonColumn.setCellValueFactory(appt -> new SimpleStringProperty(appt.getValue().getReasonToVisit()));
        statusColumn.setCellValueFactory(appt -> new SimpleStringProperty(appt.getValue().getStatus()));
    }

    private void initializeSelectColumn() {
        selectColumn.setCellFactory(new Callback<TableColumn<Appointment, Void>, TableCell<Appointment, Void>>() {
            @Override
            public TableCell<Appointment, Void> call(TableColumn<Appointment, Void> param) {
                return new TableCell<Appointment, Void>() {
                    private final Button selectButton = new Button("Sélectionner");
                    {
                        selectButton.setOnAction(event -> {
                            Appointment appt = getTableView().getItems().get(getIndex());
                            System.out.println("AppointmentSelectionController : Appointment selected: " + appt.getId());
                            if (parentController != null) {
                                parentController.setNextAppointmentIdAndInfo(appt.getId(), appt.getDateTime(), appt.getStatus());
                            }
                        });
                    }
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : selectButton);
                    }
                };
            }
        });
    }

    @FXML
    private void search(ActionEvent event) {
        List<Appointment> allAppointments = (selectedPatientID != -1)?appointmentDAO.getAllAppointmentsOfPatient(selectedPatientID):appointmentDAO.getAllAppointments();
        List<Appointment> filtered = new ArrayList<>();
        String searchText = searchField.getText().toLowerCase();
        for (Appointment appt : allAppointments) {
            if (appt.getReasonToVisit().toLowerCase().contains(searchText)) {
                filtered.add(appt);
            }
        }
        ObservableList<Appointment> filteredList = FXCollections.observableArrayList(filtered);
        appointmentTableView.setItems(filteredList);
    }
}
