package ensaminiprojet.applicationsuivitraitementsmedicaux;

import credentials.*;
import controlers.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage theMainStage;
    TheAppMainViewController controller;
    // 
    private int currentDoctorID = -1;
    public void setCurrentDoctorID(int currentDoctorID) {
        if (currentDoctorID != -1) {
            controller.setCurrentDoctorID(currentDoctorID);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        theMainStage = stage;
        // Loading the Login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LogIn.fxml"));
        // Passing the Root App to the View Controller
        scene = new Scene(loader.load());
        LoginViewController signInController = loader.getController();
        signInController.setRootApp(this);
        ///views/TheAppMainView
        theMainStage.setTitle("CareLink");
        theMainStage.setMinHeight(600);
        theMainStage.setMinWidth(950);
        theMainStage.setScene(scene);
        
        theMainStage.show();
    }

    // General function to load a view and set the root app if the controller has setRootApp(App)
    private void loadViewAndSetRootApp(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene newScene = new Scene(loader.load());
            if (fxmlPath == "/views/TheAppMainView.fxml") {
                TheAppMainViewController controller = loader.getController();
                this.controller = controller;
                controller.setRootApp(this);
                // setting up the current doctor ID that have just loged in
                System.out.println("App : the current Doctor ID : " + currentDoctorID);
                // controller.setCurrentDoctorID(currentDoctorID);
                // controller.setDoctorID();
            } else if (fxmlPath == "/views/SignUpView.fxml") {
                SignUpControler controller = loader.getController();
                controller.setRootApp(this);
            }else if (fxmlPath == "/views/LogIn.fxml") {
                LoginViewController controller = loader.getController();
                controller.setRootApp(this);
            }
            theMainStage.setScene(newScene);
            theMainStage.show();
        } catch (IOException e) {
            System.err.println("App : Error loading view: " + fxmlPath);
            e.printStackTrace();
        }
    }

    // load the 'TheAppMainView.fxml'
    public void continueToTheAppMainView(){
        loadViewAndSetRootApp("/views/TheAppMainView.fxml");
    }
    // load the 'SignUpView.fxml'
    public void continueToTheSignUpView(){
        loadViewAndSetRootApp("/views/SignUpView.fxml");
    }
    // load the 'LogIn.fxml'
    public void continueToSignInView(){
        loadViewAndSetRootApp("/views/LogIn.fxml");
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
            return fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("App : Error loading : " + fxml);
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Initialize DBCredentials Class and its credentials
        DBCredentials.getCredentials().setDbName("ApplicationDeSuiviDesTraitementsMedicaux");
        DBCredentials.getCredentials().setSubProtocol(SupportedDBTypes.MySQL);
        launch();
    }

}