module com.example.leaguemanager2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires jaxb.api.osgi;
    requires java.desktop;
    requires org.testng;

    opens com.example.leaguemanager2 to javafx.fxml;
    exports com.example.leaguemanager2;
    opens com.example.leaguemanager2.utils;
    exports com.example.leaguemanager2.controller;
    opens com.example.leaguemanager2.controller to javafx.fxml;
    opens com.example.leaguemanager2.modelDomain;

}