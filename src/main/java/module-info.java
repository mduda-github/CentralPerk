module com.example.centralperk {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.centralperk to javafx.fxml;
    exports com.example.centralperk;
}