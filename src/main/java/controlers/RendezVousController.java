
package controlers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import java.time.LocalDate;

public class RendezVousController implements Initializable {

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private VBox calendarContainer; // You'll need to add this to your FXML

    private models.CalendarModel model;
    private GridPane calendarGridPane;

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

    private void createCalendarGrid() {
        calendarGridPane = new GridPane();
        // Basic styling for the grid (you can customize this in CSS as well)
        calendarGridPane.setStyle("-fx-padding: 10; -fx-hgap: 5; -fx-vgap: 5;");
        calendarGridPane.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        calendarContainer.getChildren().add(calendarGridPane);
    }

    private void updateCalendar() {
        calendarGridPane.getChildren().clear(); // Clear previous calendar days
        //int year = model.getYear();
        //int month = model.getMonth();
        int daysInMonth = model.getNumberOfDaysInMonth();
        int firstDayOfWeek = model.getFirstDayOfWeek();
        System.out.println("the first day of the week is "+ firstDayOfWeek);

        String [] daysAbrv ={"M","T","W","TH","F","Sa","Su"};
        int headerCounter =0;
        for(String s :daysAbrv){
            Label dayabrv = new Label(s);
            dayabrv.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
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
            dayButton.setStyle("-fx-padding: 8; -fx-border-color: lightgray; -fx-alignment: center; -fx-background-radius: 5;");
            dayButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            calendarGridPane.add(dayButton, column++, row);

            if (column > 6) { // Move to the next row after Sunday
                column = 0;
                row++;
            }
        }
        
    }
}