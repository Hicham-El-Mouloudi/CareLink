package controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import models.Doctor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController  {

	
	@FXML private TextField txtfUserName;
	@FXML private PasswordField txtfPassWord;
	@FXML private Button btnSignUP;
	@FXML private Button btnLogIn;
	@FXML private CheckBox chbRememberMe;

	
	
	
	@FXML void HandleLoginAction() {
		
			String username = txtfUserName.getText().trim();
	     	String password = txtfPassWord.getText().trim();

	        if (username.isEmpty() || password.isEmpty()) {
	            showAlert("Error", "Please enter username and password.");
	            return;
	        }

	        
	        
	    
	        Doctor doctor = Doctor.find(username, password);
	        if (doctor != null) {
	            showAlert("Success", "Welcome Dr. " + doctor.getPersonalInfo().getFullName());
	            if(chbRememberMe.isSelected()) {
	            	//save username and password into the windows registry or some config file
	            	
	            	
	            }
	            
	            
	            // TODO: Load dashboard or next scene
	            
	            
	            
	            
	        } else {
	            showAlert("Login Failed", "Invalid username or password.");
	        }
		
		
	}
	
	private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
	@FXML private void HandleSignUp(ActionEvent event) {
	     
		//fo to sign up scene
		
        showAlert("", "Go to Sign Up Scene , link not implimented yet");
		
		
		
		
    }
	
	
	
	
	
}
