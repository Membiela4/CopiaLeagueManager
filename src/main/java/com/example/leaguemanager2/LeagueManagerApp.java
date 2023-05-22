package com.example.leaguemanager2;

import com.example.leaguemanager2.controller.MainSceneController;
import com.example.leaguemanager2.dao.PlayerDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.leaguemanager2.controller.LoadingPageController;
import javafx.stage.StageStyle;


import java.io.IOException;

public class LeagueManagerApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/loadingPage.fxml"));
        Parent root = loader.load();


        LoadingPageController controller = loader.getController();
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
