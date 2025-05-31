package controlers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.PatientPerson;
import javafx.embed.swing.SwingFXUtils;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import export.Exporter;
import export.extractData.PrintablePatient;

import org.apache.pdfbox.rendering.ImageType;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * show preview of a pdf that will be created and take confirmation from user
 * <p>the main role of this stage is to show preview because the user can change his 
 * mind later while choosing the path of saving .
 * @author amine
 */
public class PatientPDFPreviewController {

    @FXML private ImageView imageView;

    private PatientPerson patient;
    private File tempPdfFile;
    private boolean extracted = false;

    public void setPatient(PatientPerson patient) {
        this.patient = patient;
    }
    /**
     * 
     * @return the pdf creation confirmation
     */
    public boolean wasExtracted() {
        return extracted;
    }
    /**
     * create a temprary pdf file and load it as an imageview using the main pdf
     * creator {@link export.Exporter}. and {@link javafx.embed.swing.SwingFXUtils}
     * 
     */
    public void updateImage() {
        try {
            // Generate the PDF to a temporary file
            tempPdfFile = File.createTempFile("preview_", ".pdf");
            tempPdfFile.deleteOnExit(); // Just in case
            PrintablePatient p = new PrintablePatient(patient);
            Exporter.exportPatientPDF(p, tempPdfFile.getAbsolutePath());

            // Convert first page to image
            PDDocument doc = Loader.loadPDF(tempPdfFile);
            PDFRenderer renderer = new PDFRenderer(doc);
            BufferedImage image = renderer.renderImageWithDPI(0, 150, ImageType.RGB);
            doc.close();

            Image fxImage = SwingFXUtils.toFXImage(image, null);
            imageView.setImage(fxImage);

        } catch (IOException e) {
            e.printStackTrace(); // Or use a logger
        }
    }
    @FXML
    private void onExtract() {
        extracted = true;
        closeWindow();
    }

    @FXML
    private void onCancel() {
        if (tempPdfFile != null && tempPdfFile.exists()) {
            tempPdfFile.delete();
        }
        extracted = false;
        closeWindow();
    }


    private void closeWindow() {
        Stage stage = (Stage) imageView.getScene().getWindow();
        stage.close();
    }
}
