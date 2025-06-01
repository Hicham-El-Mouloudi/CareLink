package controlers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import models.Doctor;
import models.Person;
import models.PersonDAO;
import ensaminiprojet.applicationsuivitraitementsmedicaux.App;
import javafx.event.ActionEvent;

public class SignUpControler {

	
	
	@FXML
    private TextField txtFullName;

    @FXML
    private TextField txtEmail;

    @FXML
    private RadioButton maleRadio;

    @FXML
    private RadioButton femaleRadio;

    @FXML
    private ToggleGroup genderToggleGroup;

    @FXML
    private DatePicker dpDOB;

    @FXML
    private TextField txtSpecialisation;

    @FXML
    private TextField txtSchedule;

    @FXML
    private TextField txtDepartment;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSignUp;

    @FXML
    private Button btnCancel;


    	// Parent Controller
	App rootApp;
	// Setter for rootApp called from App when loading 'This View'
	public void setRootApp(App rootApp) {
		this.rootApp = rootApp;
	}

    // Called by FXMLLoader after the fields are injected
    @FXML
    private void initialize() {
        
    	//to Initialize any Control (optional)
        genderToggleGroup = new ToggleGroup();
        maleRadio.setToggleGroup(genderToggleGroup);
        femaleRadio.setToggleGroup(genderToggleGroup);
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        // Example of accessing field values
        String fullName = txtFullName.getText();
        String email = txtEmail.getText();
        String gender = null;
        Toggle selectedToggle = genderToggleGroup.getSelectedToggle();
        if (selectedToggle != null) {
            RadioButton selectedRadio = (RadioButton) selectedToggle;
            gender = selectedRadio.getText();
        }
        
        //date of birth
        java.util.Date dob = java.sql.Date.valueOf(dpDOB.getValue());

        String specialisation = txtSpecialisation.getText();
        String schedule = txtSchedule.getText();
        String department = txtDepartment.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // For now, just print the info 
        System.out.println("Sign Up Info:");
        System.out.println("Full Name: " + fullName);
        System.out.println("Email: " + email);
        System.out.println("Gender: " + gender);
        System.out.println("DOB: " + dob.toString());
        System.out.println("Specialisation: " + specialisation);
        System.out.println("Schedule: " + schedule);
        System.out.println("Department: " + department);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        
        
        
        
        // TODO: Add sign-up logic 
        
        //1)fill the doctor obj ect with the information 
        //2)save it to DB
        
        try {
        	
        	
        	//creat the person object
        	// Fill Person object
            Person person = new Person();
            person.setFullName(fullName);
            person.setEmail(email);
            person.setGender(gender);
            person.setDateOfBirth(dob);
            person.setInsuranceDetails(""); // if needed, extend FXML for this
        	
            //creat the model DAO that Gonna Save the person Object in DB
            
            PersonDAO RepoWriter=new PersonDAO();
            
            //save the person object to db and get the generated id of the inserted row
            person.setId(RepoWriter.insertPerson(person));
            
        	
        	
        	
        	
            // 2. Create the doctor object
        	// Fill Doctor object
            Doctor doctor = new Doctor(); // add new mode
            
            doctor.setPersonalInfo(person);
            doctor.setSpecialisation(txtSpecialisation.getText());
            doctor.setSchedule(txtSchedule.getText());
            doctor.setDepartment(txtDepartment.getText());
            doctor.setUsername(username); // set username
            doctor.setPassword(password); // set password

            // 2. Save to database
            boolean success = doctor.save();
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Doctor registered successfully.");
                rootApp.continueToSignInView();
                ClearAll(); // Clear fields
              
                
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to register doctor.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
        }
        
        
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void ClearAll() {
    	// Clear all fields
        txtFullName.clear();
        txtEmail.clear();
        genderToggleGroup.selectToggle(null);
        dpDOB.setValue(null);
        txtSpecialisation.clear();
        txtSchedule.clear();
        txtDepartment.clear();
        txtUsername.clear();
        txtPassword.clear();
    	
    	
    }
    
	@FXML
    private void handleCancel(ActionEvent event) {
     
		//return to LogIn Scene
        rootApp.continueToSignInView();
		
		ClearAll();
		
		
    }

	
	
}
