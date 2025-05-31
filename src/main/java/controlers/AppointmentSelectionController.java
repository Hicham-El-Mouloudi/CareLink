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

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

    TraitementController parentController;
    AppointmentDAO appointmentDAO = new AppointmentDAO();

    void setParentController(TraitementController parentController) {
        this.parentController = parentController;
        System.out.println("AppointmentSelectionController : TraitementController as parent set successfully");
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
        ObservableList<Appointment> appointments = FXCollections.observableArrayList(appointmentDAO.getAllAppointments());
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
                                parentController.setAppointmentIdAndInfo(appt.getId(), appt.getDateTime(), appt.getReasonToVisit());
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
        List<Appointment> allAppointments = appointmentDAO.getAllAppointments();
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
