module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.apache.logging.log4j;

    opens com.example.client to javafx.fxml;
    exports com.example.client;

    opens com.example.model.client to javafx.fxml;
    exports com.example.model.client;

    opens com.example.model.tour to javafx.fxml;
    exports com.example.model.tour;

    opens com.example.client.tour to javafx.fxml;
    exports com.example.client.tour;

    opens com.example.model.order to javafx.fxml;
    exports com.example.model.order;

    opens com.example.model.ticket to javafx.fxml;
    exports com.example.model.ticket;
}