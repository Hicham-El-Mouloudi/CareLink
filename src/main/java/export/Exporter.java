package export;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import export.extractData.PrintablePatient;
import models.Appointment;
import models.Patient;

import java.io.IOException;

/**
 * @author AMINE
 */
public class Exporter {
    private static final float MARGIN = 50;
    private static final float LINE_HEIGHT = 20;
    private static final float TABLE_ROW_HEIGHT = 15;
    /**
     * <strong> ecrire un fichier pdf qui affiche les donnees generale d'un patient</strong>
     *  <p>
     * this function calls:
     * <ul>
     *  <li>{@link PrintablePatient#getRendezVousList()}</li>
     *  <li>{@link PrintablePatient#getNomComplet()}</li>
     *  <li>{@link PrintablePatient#getAge()}</li>
     *  <li>{@link PrintablePatient#getSexe()}</li>
     *  <li>{@link PrintablePatient#getContacts()}</li>
     *  <li>{@link PrintablePatient#getEmail()}</li>
     *  <li>{@link PrintablePatient#getRemarquesMedicales()}</li>
     * </ul>
     * @param patient : Module donnees de patient 
     * @param outputPath 
  
     * @throws IOException
     */
    public static void print(PrintablePatient patient, String outputPath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // load fonts
            PDType1Font titleFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDType1Font labelFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDType1Font valueFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            PDType1Font tableHeaderFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDType1Font tableContentFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

            // Start position
            float yPosition = PDRectangle.A4.getHeight() - MARGIN;

            // Title
            contentStream.beginText();
            contentStream.setFont(titleFont, 16);
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Patient : " + patient.getNomComplet());
            contentStream.endText();
            // new line
            yPosition -= LINE_HEIGHT * 1.5f;

            // information personnelle
            yPosition = drawLabelValue(contentStream, labelFont, valueFont, "Nom Complet :", patient.getNomComplet(), MARGIN, yPosition);
            yPosition = drawLabelValue(contentStream, labelFont, valueFont, "Age :", String.valueOf(patient.getAge()), MARGIN, yPosition);
            yPosition = drawLabelValue(contentStream, labelFont, valueFont, "Sexe :", patient.getSexe(), MARGIN, yPosition);
            yPosition = drawLabelValue(contentStream, labelFont, valueFont, "Email :", patient.getEmail(), MARGIN, yPosition);
            // little margine pour les remarque
            yPosition -= LINE_HEIGHT * 0.5f;
            // remarque medicales
            yPosition = drawLabelValue(contentStream, labelFont, valueFont, "Remarques Médicales :", patient.getRemarquesMedicales(), MARGIN, yPosition);
            yPosition = drawLabelValue(contentStream, labelFont, valueFont, "Traitements en cours :", patient.getTraitementsEnCours(), MARGIN, yPosition);
            // little margine pour le tableau
            yPosition -= LINE_HEIGHT * 0.5f;

            // Rendez-vous table
            yPosition = drawTableHeader(contentStream, tableHeaderFont, MARGIN, yPosition);
            yPosition -= TABLE_ROW_HEIGHT;

            //  page
            for (Appointment rdv : patient.getRendezVousList()) {
                if (yPosition < MARGIN + TABLE_ROW_HEIGHT) {
                    contentStream.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);// create new page if needed 
                    // we can add a new page without returning the contentstream because this will be the last call 
                    yPosition = PDRectangle.A4.getHeight() - MARGIN;
                    yPosition = drawTableHeader(contentStream, tableHeaderFont, MARGIN, yPosition);
                    yPosition -= TABLE_ROW_HEIGHT;
                }

                yPosition = drawTableRow(contentStream, tableContentFont, rdv, MARGIN, yPosition);
                yPosition -= TABLE_ROW_HEIGHT;
            }

            contentStream.close();
            document.save(outputPath);
        }
    }
    /**
     * draw label/ value : ({@code les informations personnelles de patient}) 
     * this function is set to be called by {@link #print(Patient, String)}
     * @param contentStream 
     * @param labelFont :
     * @param valueFont :
     * @param label : example {@code nom :} 
     * @param value example {@code hmida} 
     * @param x draw position horizontally
     * @param y draw position vertically (line)
     * @return thif function handles multiple lines , and return the new vertical position (new line)
     * @throws IOException
     */
    private static float drawLabelValue(PDPageContentStream contentStream,
                                    PDType1Font labelFont, PDType1Font valueFont,
                                    String label, String value,
                                    float x, float y) throws IOException {
    // Draw label
    contentStream.beginText();
    contentStream.setFont(labelFont, 12);
    contentStream.newLineAtOffset(x, y);
    contentStream.showText(label);
    contentStream.endText();

    // handling multiple lines 
    if (value == null) value = "";
    String[] lines = value.split("\\r?\\n"); // split on \n or \r\n
    // iterate over all the lines 
    for (String line : lines) {
        contentStream.beginText();
        contentStream.setFont(valueFont, 12);
        contentStream.newLineAtOffset(x + 150, y);
        contentStream.showText(line);
        contentStream.endText();
        y -= LINE_HEIGHT; 
    }

    return y; // new line
}

    /**
     * dessiner les titres de tableau
     * <p>
     * les titre : {@code "ID", "Type", "Date Début", "Date Fin", "Status", "Raison"}</P>
     *      
     * this function is set to be called by {@link #print(Patient, String)}
     * @param contentStream 
     * @param font 
     * @param x horizontal position
     * @param y vertical position
     * @return the new vertical position
     * @throws IOException
     */
    private static float drawTableHeader(PDPageContentStream contentStream,
                                         PDType1Font font,
                                         float x, float y) throws IOException {
        float[] columnWidths = {50, 100, 100, 100};
        String[] headers = {"ID", "Date Début","Status", "Raison"};

        contentStream.setLineWidth(1f);
        contentStream.moveTo(x, y); // line from 
        contentStream.lineTo(x + 510, y);// to
        contentStream.stroke();

        float currentX = x;
        contentStream.setFont(font, 10);
        for (int i = 0; i < headers.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(currentX + 2, y - 12);
            contentStream.showText(headers[i]);
            contentStream.endText();

            contentStream.moveTo(currentX + columnWidths[i], y);
            contentStream.lineTo(currentX + columnWidths[i], y - TABLE_ROW_HEIGHT);
            contentStream.stroke();

            currentX += columnWidths[i];
        }

        contentStream.moveTo(x, y - TABLE_ROW_HEIGHT);
        contentStream.lineTo(x + 510, y - TABLE_ROW_HEIGHT);
        contentStream.stroke();

        return y;
    }
    /**
     * draw the table row (rendez-vous infors)
     * this function is set to be called by {@link #print(Patient, String)}
     * @param contentStream
     * @param font
     * @param rdv
     * @param x horizontal position
     * @param y vertical position
     * @return the new vertical position
     * @throws IOException
     */
    private static float drawTableRow(PDPageContentStream contentStream,
                                      PDType1Font font,
                                      Appointment rdv,
                                      float x, float y) throws IOException {
        float[] columnWidths = {50, 80, 100, 100, 80, 100};
        //DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //String formattedDate = today.format(customFormatter);
        String[] values = {
                String.valueOf(rdv.getId()),
                rdv.getDateTime().toString(),
                rdv.getStatus(),
                rdv.getReasonToVisit() 
        };

        float currentX = x;
        contentStream.moveTo(currentX, y);
        contentStream.lineTo(currentX , y - TABLE_ROW_HEIGHT);
        contentStream.stroke();

        contentStream.setFont(font, 10);
        for (int i = 0; i < values.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(currentX + 2, y - 12);
            contentStream.showText(values[i] != null ? values[i] : "");
            contentStream.endText();

            contentStream.moveTo(currentX + columnWidths[i], y);
            contentStream.lineTo(currentX + columnWidths[i], y - TABLE_ROW_HEIGHT);
            contentStream.stroke();

            currentX += columnWidths[i];
        }

        contentStream.moveTo(x, y - TABLE_ROW_HEIGHT);
        contentStream.lineTo(x + 510, y - TABLE_ROW_HEIGHT);
        contentStream.stroke();

        return y;
    }
}
