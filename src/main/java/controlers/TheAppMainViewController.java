/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlers;

import java.net.URL;

import java.util.ResourceBundle;

import ensaminiprojet.applicationsuivitraitementsmedicaux.App;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.util.Duration;
import javafx.animation.ScaleTransition;
import javafx.fxml.*;
import javafx.geometry.Bounds;

import java.io.IOException;
import javafx.scene.layout.BorderPane;


import models.*;

/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class TheAppMainViewController implements Initializable {

    @FXML
    private Button AccueilButton;
    @FXML
    private Button PatientsButton;
    @FXML
    private Button RendezVousButton;
    @FXML
    private Button TraitementsButton;
    @FXML
    private Button AideButton;
    @FXML
    private Region menuBar;
    @FXML
    private Pane bodyContent;
    @FXML
    private BorderPane mainView;
    @FXML 
    private Button UserButton;
    // non Fxml 
    private Popup userPopup;

    // The connected doctor
    private int currentDoctorID = -1;
    HomeController controller;
    public void setCurrentDoctorID(int currentDoctorID) {
        if (currentDoctorID != -1) {
            this.currentDoctorID = currentDoctorID;
            System.out.println("TheAppMainViewController : the current Coctor ID : " + currentDoctorID);
            controller.setCurrentDoctorID(currentDoctorID);
        }
    }

    // 
    // Parent Controller
	App rootApp;
	// Setter for rootApp called from App when loading 'This View'
	public void setRootApp(App rootApp) {
		this.rootApp = rootApp;
	}
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // When the application starts, we load the home view
        navigateToHome();

        // navigating to the home view when the home button is clicked or on app start
        AccueilButton.setOnAction(event -> {
            navigateToHome();
        });
        // navigating to the patients view when the patients button is clicked
        PatientsButton.setOnAction(event -> {
            navigateToPatients();
        });
        // navigating to the traitements view when the traitements button is clicked
        TraitementsButton.setOnAction(event -> {
            navigateToTraitements();
        });
        // navigating to the rendez-vous view when the rendez-vous button is clicked
        RendezVousButton.setOnAction(event -> {
            navigateToRendezVous();
        });
        // navigating to the aide view when the aide button is clicked
        AideButton.setOnAction(event -> {
            navigateToAide();
        });
        UserButton.setOnAction(e -> showUserPopup());

    }
    // ------------------------------------------------------------------------------------------------------
    // ---------------------------------------- NAVIGATION FUNCTIONS ----------------------------------------
    // ------------------------------------------------------------------------------------------------------
    // navigate to home
    private void navigateToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/HomeView.fxml"));
            Parent homeView = loader.load();
            System.out.println("TheAppMainViewController : loading the '/views/HomeView.fxml' ");
            HomeController homeController = loader.getController();
            this.controller = homeController;
            // homeController.setCurrentDoctorID(currentDoctorID);
            mainView.setCenter(homeView);
        } catch (IOException e) {
            System.err.println("Error loading home view: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
    // navigate to patients
    private void navigateToPatients() {
        try {
            Parent patientsView = FXMLLoader.load(getClass().getResource("/views/PatientView.fxml"));
            mainView.setCenter(patientsView);
        } catch (IOException e) {
            System.err.println("Error loading patients view: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
    // navigate to traitements
    private void navigateToTraitements() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TraitementView.fxml"));
            Parent traitementsView = loader.load();
            TraitementController traitementController = loader.getController();
            traitementController.setCurrentDoctorID(currentDoctorID);
            
            mainView.setCenter(traitementsView);
        } catch (IOException e) {
            System.err.println("Error loading traitements view: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
    // navigate to rendez-vous
    private void navigateToRendezVous() {
        try {
            Parent rendezVousView = FXMLLoader.load(getClass().getResource("/views/RendezVous.fxml"));
            mainView.setCenter(rendezVousView);
        } catch (IOException e) {
            System.err.println("Error loading rendez-vous view: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
    // navigate to aide
    private void navigateToAide() {
        try {
            Parent aideView = FXMLLoader.load(getClass().getResource("/views/AideView.fxml"));
            mainView.setCenter(aideView);
        } catch (IOException e) {
            System.err.println("Error loading aide view: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
    // navigate to edit patient view
    public void navigateToEditPatientView(Patient patient) {
        try {
            FXMLLoader editLoader = new FXMLLoader(getClass().getResource("/views/EditPatientView.fxml"));
            Parent editRoot = editLoader.load();
            controlers.EditPatientController editController = editLoader.getController();
            editController.setPatient(patient);
            // Optionally set parent controller if needed:
            // editController.setParentController(this);
            setBodyContent((Pane) editRoot);
        } catch (IOException e) {
            System.err.println("Error loading edit patient view: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    public void setBodyContent(@SuppressWarnings("exports") Pane bodyContent) {
        this.bodyContent = bodyContent;
    }
    //
    private void showUserPopup() {
        if (userPopup != null && userPopup.isShowing()) {
            userPopup.hide();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserPopup.fxml"));
            VBox popupContent = loader.load();
            UserPopupController controller = loader.getController();
            // 
            controller.setRootApp(rootApp);
            // john doe should be replaced by the real name of the current loged in doctor 
            controller.setUserName(DoctorDAO.getDoctorById(currentDoctorID).getPersonalInfo().getFullName());

            userPopup = new Popup();
            userPopup.getContent().add(popupContent);
            userPopup.setAutoHide(true);

            Bounds buttonBounds = UserButton.localToScreen(UserButton.getBoundsInLocal());

            // measure popup width accurately
            popupContent.applyCss();
            popupContent.layout();
            double popupWidth = popupContent.prefWidth(-1);

            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();

            double x = buttonBounds.getMaxX() - popupWidth;
            if (x + popupWidth > screenWidth) {
                x = screenWidth - popupWidth - 10;
            }
            if (x < 0) {
                x = 10;
            }

            double y = buttonBounds.getMaxY() + 5;

            popupContent.setScaleX(0.8);
            popupContent.setScaleY(0.8);
            userPopup.show(UserButton.getScene().getWindow(), x, y);

            ScaleTransition scale = new ScaleTransition(Duration.millis(180), popupContent);
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.play();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
