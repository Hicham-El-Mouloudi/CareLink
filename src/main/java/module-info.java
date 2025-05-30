module ensaminiprojet.applicationsuivitraitementsmedicaux {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    // requires java.sql;
    requires transitive java.sql;
    requires javafx.graphics;
    requires javafx.base;

    exports ensaminiprojet.applicationsuivitraitementsmedicaux;
    exports controlers;
    exports models;
    
    opens ensaminiprojet.applicationsuivitraitementsmedicaux to javafx.fxml;
    opens controlers to javafx.fxml;

}
