package controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ConfirmDeleteAppointmentController {

    private boolean deleteConfirmed = false;

    // Cette méthode est appelée lorsque le bouton "Annuler" est cliqué
    @FXML
    private void handleAnnuler(ActionEvent event) {
        deleteConfirmed = false;
        fermerDialogue(event);
    }

    // Cette méthode est appelée lorsque le bouton "Supprimer" est cliqué
    @FXML
    private void handleSupprimer(ActionEvent event) {
        deleteConfirmed = true;
        fermerDialogue(event);
    }

    // Méthode utilitaire pour fermer la fenêtre de dialogue
    private void fermerDialogue(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }

    // Méthode pour vérifier si la suppression a été confirmée
    public boolean isdeleteConfirmed() {
        return deleteConfirmed;
    }
}