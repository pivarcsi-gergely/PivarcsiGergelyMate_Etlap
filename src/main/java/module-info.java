module hu.petrik.etlap {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens hu.petrik.etlap to javafx.fxml;
    exports hu.petrik.etlap;
}