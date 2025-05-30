
package controlers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Appointment;
import models.AppointmentDAO;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class RendezVousController implements Initializable {

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private VBox calendarContainer; // 
    //
    @FXML private Button addAppointmentButton;
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
        // 
        addAppointmentButton.setOnAction(event->handleAddingAppointment());
        // Listen for changes in month and year to update the calendar
        model.monthProperty().addListener((obs, oldMonth, newMonth) -> updateCalendar());
        model.yearProperty().addListener((obs, oldYear, newYear) -> updateCalendar());
        // 
        iDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(cellData -> {
            // Format the java.util.Date to a readable string
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
            return new javafx.beans.property.SimpleStringProperty(sdf.format(cellData.getValue().getDateTime()));
        });
        personColumn.setCellValueFactory(cellData -> {
            // You can return something like "DoctorID:PatientID" if no names are available
            Appointment appt = cellData.getValue();
            String person = "Doctor " + appt.getDoctorId() + ", Patient " + appt.getPatientId();
            return new javafx.beans.property.SimpleStringProperty(person);
        });
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        // configure the right click 
        appointmentTableView.setRowFactory(tableView -> {
        TableRow<Appointment> row = new TableRow<>();

        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");

        // Actions
        editItem.setOnAction(event -> {
            Appointment selected = row.getItem();
            handleEdit(selected);
        });

        deleteItem.setOnAction(event -> {
            Appointment selected = row.getItem();
            handleDelete(selected);
        });

        contextMenu.getItems().addAll(editItem, deleteItem);

        // Only show menu for non-empty rows
        row.contextMenuProperty().bind(
            javafx.beans.binding.Bindings.when(row.emptyProperty())
                .then((ContextMenu) null)
                .otherwise(contextMenu)
        );

        return row;
    });

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
        for (int i = 0; i < 7; i++) {
            row.setPercentHeight(100.0 / 7); // divide 100% evenly
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
            dayabrv.setStyle("-fx-text-fill :#1b7895;");
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
            model.setDay(Integer.valueOf(day));
            String year = String.valueOf(model.getYear());
            String month = String.valueOf(model.getMonth());

            List<Appointment> appointments = appointmentDAO.getAppointmentsByDate(year, month, day);
            appointmentTableView.getItems().setAll(appointments);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }
    /**
     * handle delete from the right click
     */
    private void handleEdit(Appointment appointment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/editAppointment.fxml"));
            Parent root = loader.load();
            if (root == null){
                System.out.println("[error] fxml not loaded");
                return;
            }
            EditAppointmentController controller = loader.getController();
            controller.setAppointment(appointment);

            Stage stage = new Stage();
            stage.setTitle("Edit Appointment");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // blocks interaction with main window
            stage.setResizable(false);
            stage.showAndWait();

            // Optionally: refresh table after editing
            updateAppointmentTable(String.valueOf(model.getDay())); // if you track the day
            System.out.println("view updated succesfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void handleDelete(Appointment appointment) {
        try{
            appointmentDAO.deleteAppointment(appointment.getId());
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * load mini window to add new appointment
     */
    private void handleAddingAppointment(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/addAppointment.fxml"));
            Parent root = loader.load();
            if (root == null){
                System.out.println("[error] fxml not loaded");
                return;
            }
            Stage stage = new Stage();
            stage.setTitle("Ajouter Rendez-vous");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // blocks interaction with main window
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}