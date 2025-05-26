/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Person;
import models.PersonDAO;
import models.Patient;
import models.PatientDAO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class AddPatientController implements Initializable {
    @FXML private TextField fullNameField;
    @FXML private TextField ageField;
    @FXML private RadioButton hommeRadio;
    @FXML private RadioButton femmeRadio;
    @FXML private TextField emailField;
    @FXML private TextField telephoneField;
    @FXML private TextField insuranceField;
    @FXML private TextField medicalRemarksField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    private ToggleGroup genderGroup;

    private PersonDAO personDAO = new PersonDAO();
    private PatientDAO patientDAO = new PatientDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genderGroup = new ToggleGroup();
        hommeRadio.setToggleGroup(genderGroup);
        femmeRadio.setToggleGroup(genderGroup);
    }    

    @FXML
    private void handleSave() {
        try {
            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String insurance = insuranceField.getText();
            String telephone = telephoneField.getText();
            String gender = hommeRadio.isSelected() ? "Homme" : femmeRadio.isSelected() ? "Femme" : "";
            String medicalRemarks = medicalRemarksField.getText();
            int age = 0;
            try { age = Integer.parseInt(ageField.getText()); } catch (Exception ignored) {}
            // Calculate date of birth from age (approximate, Jan 1)
            LocalDate dob = LocalDate.now().minusYears(age);
            Date dateOfBirth = Date.from(dob.atStartOfDay(ZoneId.systemDefault()).toInstant());
            // Create and save Person
            Person person = new Person(fullName, gender, email, dateOfBirth, insurance);
            personDAO.insertPerson(person);
            // Retrieve the inserted person (get by email)
            Person savedPerson = personDAO.getAllPersons().stream().filter(p -> p.getEmail().equals(email)).findFirst().orElse(null);
            if (savedPerson == null) throw new Exception("Person not saved");
            // Create and save Patient
            Patient patient = new Patient(telephone, medicalRemarks, "Actif", savedPerson.getId());
            patientDAO.insertPatient(patient);
            // Close window
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
