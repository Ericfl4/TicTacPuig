module com.example.tictacpuig {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;
    requires org.jetbrains.annotations;


    opens com.example.tictacpuig to javafx.fxml;
    exports com.example.tictacpuig;
    exports com.example.tictacpuig.controller;
    opens com.example.tictacpuig.controller to javafx.fxml;
}