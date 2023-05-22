package com.example.leaguemanager2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {


    @FXML
    private ToggleButton btnPlayers;
    @FXML
    private ToggleButton btnTeams;
    @FXML
    private ToggleButton btnLeague;
    @FXML
    private Button closeButton;

    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();;
        currentStage = (Stage) closeButton.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private Pane pane;
    /*
    Method asigned to a Button that change the view to the players manager
     */
    @FXML
    public void playersScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/leaguemanager2/views/playersScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();;
            currentStage.setResizable(false);

            PlayersSceneController controller = loader.getController();

            currentStage.setScene(scene);
            currentStage.show();

            currentStage.setOnCloseRequest(e -> controller.closeWindows());
            currentStage = (Stage) this.btnPlayers.getScene().getWindow();
            if (currentStage != null) {
                currentStage.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /*
    Method that change the view to the teams manager
     */
    @FXML
    public void teamsScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/leaguemanager2/views/teamsScene.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();;
            currentStage.setResizable(false);

            TeamsSceneController controller = loader.getController();

            currentStage.setScene(scene);
            currentStage.show();

            currentStage.setOnCloseRequest(e -> controller.closeWindows());
            currentStage = (Stage) this.btnTeams.getScene().getWindow();
            if (currentStage != null) {
                currentStage.close();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /*
   Method that change the view to the teams manager
    */
    @FXML
    public void leagueView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/leaguemanager2/views/leagueView.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();;
            currentStage.setResizable(false);

            LeagueViewController controller = loader.getController();

            currentStage.setScene(scene);
            currentStage.show();

            currentStage.setOnCloseRequest(e -> controller.closeWindows());
            currentStage = (Stage) this.btnTeams.getScene().getWindow();
            if (currentStage != null) {
                currentStage.close();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void closeWindows() {
    }
}
