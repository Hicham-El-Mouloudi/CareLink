package controlers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Patient;
import models.PatientDAO;

public class EditPatientController {
    @FXML
    private TextField emergencyContactField;
    @FXML
    private TextField medicalConditionsField;
    @FXML
    private TextField stateField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Patient patient;
    private PatientDAO patientDAO = new PatientDAO();

    public void setPatient(Patient patient) {
        this.patient = patient;
        if (patient != null) {
            emergencyContactField.setText(patient.getEmergencyContact());
            medicalConditionsField.setText(patient.getMedicalConditions());
            stateField.setText(patient.getState());
        }
    }

    @FXML
    private void handleSave() {
        if (patient != null) {
            patient.setEmergencyContact(emergencyContactField.getText());
            patient.setMedicalConditions(medicalConditionsField.getText());
            patient.setState(stateField.getText());
            try {
                patientDAO.updatePatient(patient);
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
