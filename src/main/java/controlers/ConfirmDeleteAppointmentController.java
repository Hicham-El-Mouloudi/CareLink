package controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * confirm the delete request through a mini window
 * @author amine
 */
public class ConfirmDeleteAppointmentController {
    
    // delete request supposed to be non confirmed
     
    private boolean deleteConfirmed = false;
    /**
    *  this methode is called when annuler is clicked 
    */
    @FXML
    private void handleAnnuler(ActionEvent event) {
        deleteConfirmed = false;
        fermerDialogue(event);
    }
    /**
     * this methode is called when confirmer - supprimer is clicked 
     * @param event
     */
    @FXML
    private void handleSupprimer(ActionEvent event) {
        deleteConfirmed = true;
        fermerDialogue(event);
    }
    /**
     * close confirmation dialogue
     * @param event the stage will be extracted from this object, and closed .
     */
    private void fermerDialogue(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }

    // get the confirmation value
    public boolean isdeleteConfirmed() {
        return deleteConfirmed;
    }
}