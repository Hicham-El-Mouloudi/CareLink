package controlers;
import ensaminiprojet.applicationsuivitraitementsmedicaux.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserPopupController {

    @FXML private Label userLabel;
    @FXML private Button disconnectButton;

    // Parent Controller
	App rootApp;
	// Setter for rootApp called from App when loading 'This View'
	public void setRootApp(App rootApp) {
		this.rootApp = rootApp;
	}

    public void initialize() {
        // Optional logic
        disconnectButton.setOnAction(e -> {
            System.out.println("User disconnected.");
            rootApp.continueToSignInView();
        });
        String baseStyle = "-fx-text-fill: white; -fx-background-radius: 6; -fx-font-weight: bold;";

        disconnectButton.setStyle("-fx-background-color: #D9534F; " + baseStyle);

        disconnectButton.setOnMousePressed(e -> 
            disconnectButton.setStyle("-fx-background-color: rgb(79, 34, 33); " + baseStyle));

        disconnectButton.setOnMouseReleased(e -> 
            disconnectButton.setStyle("-fx-background-color: #D9534F; " + baseStyle));
    }

    public void setUserName(String name) {
        userLabel.setText("Dr. " + name);
    }
}

