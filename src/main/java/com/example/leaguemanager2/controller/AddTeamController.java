package com.example.leaguemanager2.controller;

import com.example.leaguemanager2.dao.TeamDAO;
import com.example.leaguemanager2.modelDomain.Player;
import com.example.leaguemanager2.modelDomain.Team;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class AddTeamController implements Initializable {

    public static Logger logger;

    @FXML
    private TextField nameField;

    @FXML
    private TextField abbreviationField;
    @FXML
    private TextField teamIdField;

    @FXML
    private Image teamIconColumn;

    @FXML
    private Button saveBtn;
    @FXML
    private Button backBtn;

    private Team team;
    @FXML
    private ObservableList<Team> teamsObservableList;
    TeamDAO teamDAO = new TeamDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initAtributtes(ObservableList<Player> players) {
        this.teamsObservableList = teamsObservableList;
    }

    /*
    Function to save a team which be regreted to the previus controller
     */
    @FXML
    public void saveTeam(ActionEvent event) {
        if(this.nameField!=null) {
            String name = this.nameField.getText();
            String abbreviation = this.abbreviationField.getText();


            Team t = new Team(name, abbreviation);
            List<Team> teams = null;
            try {
                teams = teamDAO.findAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (!teams.contains(t)) {
                this.team = t;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFO");
                alert.setContentText("Añadido Correctamente");
                alert.showAndWait();

                Stage stage = (Stage) this.saveBtn.getScene().getWindow();
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR");
                alert.setContentText("Equipo ya existente");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Introduce un nombre de equipo");
            alert.showAndWait();
        }
    }

    @FXML
    private void back(ActionEvent event){
        this.team=null;
        Stage stage = (Stage) this.saveBtn.getScene().getWindow();
        stage.close();

    }

    public Team getTeam() {
        return team;
    }
}
