package controlers;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class EditAppointmentController {

    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private TextField reasonField;
    @FXML private TextField statusField;
    @FXML private TextField patientIdField;
    @FXML private TextField doctorIdField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private models.Appointment appointment;
    private models.AppointmentDAO dao = new models.AppointmentDAO();
    /**
     * set an appointment to be displayed
     * @param appointment
     */
    public void setAppointment(models.Appointment appointment) {
        this.appointment = appointment;

        // Fill fields
        datePicker.setValue(appointment.getDateTime().toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate());
        timeField.setText(new SimpleDateFormat("HH:mm").format(appointment.getDateTime()));
        reasonField.setText(appointment.getReasonToVisit());
        statusField.setText(appointment.getStatus());
        patientIdField.setText(String.valueOf(appointment.getPatientId()));
        doctorIdField.setText(String.valueOf(appointment.getDoctorId()));
    }

    @FXML
    public void initialize() {
        cancelButton.setOnAction(e -> closeWindow());
        saveButton.setOnAction(e -> {
            try {
                // Combine date and time
                System.out.println("the controller is recongnized");
                LocalDate date = datePicker.getValue();
                LocalTime time = LocalTime.parse(timeField.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                Date newDate = java.util.Date.from(date.atTime(time).atZone(java.time.ZoneId.systemDefault()).toInstant());

                appointment.setDateTime(newDate);
                appointment.setReasonToVisit(reasonField.getText());
                appointment.setStatus(statusField.getText());
                appointment.setPatientId(Integer.parseInt(patientIdField.getText()));
                appointment.setDoctorId(Integer.parseInt(doctorIdField.getText()));
                
                dao.updateAppointment(appointment);
                closeWindow();
            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Invalid data").showAndWait();
            }
        });
    }
    @FXML
    public void openPatientSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PatientSearch.fxml"));
            Parent root = loader.load();

            PatientSearchController controller = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Search Patient");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            int selectedId = controller.getSelectedPatientId();
            if (selectedId != -1) {
                patientIdField.setText(String.valueOf(selectedId));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
