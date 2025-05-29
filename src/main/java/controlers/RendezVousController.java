
package controlers;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import models.Appointment;
import models.AppointmentDAO;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

public class RendezVousController implements Initializable {

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private VBox calendarContainer; // 
    // partie des rendez-vous
    @FXML private TableView<Appointment> appointmentTableView;
    @FXML private TableColumn<Appointment, Integer> iDColumn;
    @FXML private TableColumn<Appointment, String> personColumn;
    @FXML private TableColumn<Appointment, String> dateColumn;
    @FXML private TableColumn<Appointment, String> statusColumn;

    

    private models.CalendarModel model;
    private GridPane calendarGridPane;
    private models.AppointmentDAO appointmentDAO = new AppointmentDAO();

    public void initialize(URL url,ResourceBundle rb) {
        model = new models.CalendarModel();

        // Populate combo boxes
        monthComboBox.setItems(model.getMonths());
        yearComboBox.setItems(model.getYears());

        // Set initial values for combo boxes
        monthComboBox.setValue(model.getMonths().get(LocalDate.now().getMonthValue() - 1));
        yearComboBox.setValue(LocalDate.now().getYear());

        // Bind combo box selections to the model
        monthComboBox.setOnAction(event -> model.setMonth(monthComboBox.getSelectionModel().getSelectedIndex() + 1));
        yearComboBox.setOnAction(event -> model.setYear(yearComboBox.getValue()));

        // Initialize and display the calendar
        createCalendarGrid();
        updateCalendar();

        // Listen for changes in month and year to update the calendar
        model.monthProperty().addListener((obs, oldMonth, newMonth) -> updateCalendar());
        model.yearProperty().addListener((obs, oldYear, newYear) -> updateCalendar());
    }
    /**
     * initialize and set the style for the gridpane 
     */
    private void createCalendarGrid() {
        calendarGridPane = new GridPane();
        // Basic styling for the grid (you can customize this in CSS as well)
        calendarGridPane.setStyle("-fx-padding: 10; -fx-hgap: 5; -fx-vgap: 5;");
        // 
        ColumnConstraints col = new ColumnConstraints();
        RowConstraints row = new RowConstraints();
        
        // same proportion of height and width 
        for (int i = 0; i < 7; i++) {
            col.setPercentWidth(100.0 / 7); // divide 100% evenly
            col.setHgrow(Priority.ALWAYS);
            calendarGridPane.getColumnConstraints().add(col);
        }
        // Set equal row constraints
        for (int i = 0; i < 6; i++) {
            row.setPercentHeight(100.0 / 6); // divide 100% evenly
            row.setVgrow(Priority.ALWAYS);
            calendarGridPane.getRowConstraints().add(row);
        }
        calendarContainer.getChildren().add(calendarGridPane);
    }
    /**
     * load - update the callendar data 
     */
    private void updateCalendar() {
        calendarGridPane.getChildren().clear(); // Clear previous calendar days
        //int year = model.getYear();
        //int month = model.getMonth();
        int daysInMonth = model.getNumberOfDaysInMonth();
        int firstDayOfWeek = model.getFirstDayOfWeek();

        String [] daysAbrv ={"M","T","W","TH","F","Sa","Su"};
        int headerCounter =0;
        for(String s :daysAbrv){
            Label dayabrv = new Label(s);
            dayabrv.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            dayabrv.setStyle("-fx-font-size : 11; -fx-text-fill :#1b7895;");
            GridPane.setHalignment(dayabrv, HPos.CENTER);
            calendarGridPane.add(dayabrv,headerCounter++,0);
        }
        int row = 1;
        int column =0;
        // Add empty cells for the leading days of the previous month
        for (int i = 0; i < firstDayOfWeek - 1; i++) {
            Label nonIncludedDay = new Label("");
            nonIncludedDay.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            calendarGridPane.add(nonIncludedDay, column++, row);
        }
        column = firstDayOfWeek - 1; // Adjust for 0-based grid

        // Add day labels for the current month
        for (int day = 1; day <= daysInMonth; day++) {
            Button dayButton = new Button(String.valueOf(day));
            dayButton.setStyle("-fx-padding: 8;-fx-border-radius:5; -fx-border-color: lightgray; -fx-alignment: center; -fx-background-radius: 5;");
            dayButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            dayButton.setOnAction(event-> updateAppointmentTable(dayButton.getText()));
            calendarGridPane.add(dayButton, column++, row);
            
            if (column > 6) { // Move to the next row after Sunday
                column = 0;
                row++;
            }
        }
        
    }
    /**
     * extract and display a day appointments 
     * <p> the year and month are already loaded in the calendar model
     * @param day the text value of the calendar button
     */
    public void updateAppointmentTable(String day){ 
        try {
            String year = String.valueOf(model.getYear());
            String month = String.valueOf(model.getMonth());

            List<Appointment> appointments = appointmentDAO.getAppointmentsByDate(year, month, day);
            appointmentTableView.getItems().setAll(appointments);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }
}