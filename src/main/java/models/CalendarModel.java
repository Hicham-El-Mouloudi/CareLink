package models;

import java.time.LocalDate;
import java.time.YearMonth;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CalendarModel {

    private final IntegerProperty year = new SimpleIntegerProperty(LocalDate.now().getYear());
    private final IntegerProperty month = new SimpleIntegerProperty(LocalDate.now().getMonthValue());
    private final ObservableList<Integer> years = FXCollections.observableArrayList();
    private final ObservableList<String> months = FXCollections.observableArrayList(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    );

    public CalendarModel() {
        // Populate years for the combo box (e.g., current year +/- 50)
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 50; i <= currentYear + 50; i++) {
            years.add(i);
        }
    }

    public int getYear() {
        return year.get();
    }

    @SuppressWarnings("exports")
    public IntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public int getMonth() {
        return month.get();
    }

    @SuppressWarnings("exports")
    public IntegerProperty monthProperty() {
        return month;
    }

    public void setMonth(int month) {
        this.month.set(month);
    }

    public ObservableList<Integer> getYears() {
        return years;
    }

    public ObservableList<String> getMonths() {
        return months;
    }

    public int getNumberOfDaysInMonth() {
        YearMonth yearMonth = YearMonth.of(year.get(), month.get());
        return yearMonth.lengthOfMonth();
    }

    public int getFirstDayOfWeek() {
        LocalDate firstOfMonth = LocalDate.of(year.get(), month.get(), 1);
        return firstOfMonth.getDayOfWeek().getValue(); // Monday=1, ..., Sunday=7
    }
}