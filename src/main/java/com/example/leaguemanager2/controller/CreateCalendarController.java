package com.example.leaguemanager2.controller;

import com.example.leaguemanager2.dao.TeamDAO;
import com.example.leaguemanager2.modelDomain.Match;
import com.example.leaguemanager2.modelDomain.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateCalendarController implements Initializable {

    private Match match;
    private List<Team> teams;
    private TeamDAO teamDAO = new TeamDAO();

    @FXML
    private ComboBox<Team> comboBoxTeams;

    @FXML
    private Button closeButton;
    @FXML
    public Button addAllTeams;

    @FXML
    private TableView<Team> tableTeams;

    @FXML
    private TableColumn<Team, String> teamNameColumn;

    @FXML
    private TableColumn<Team, String> teamAbbColumn;

    @FXML
    private Button refreshBtn;

    private ObservableList<Team> teamObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Team> allTeams = teamDAO.findAll();
            teamObservableList = FXCollections.observableArrayList();
            teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            teamAbbColumn.setCellValueFactory(new PropertyValueFactory<>("abbreviation"));
            comboBoxTeams.setItems(FXCollections.observableArrayList(allTeams));
            tableTeams.setItems(teamObservableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void addTeam(ActionEvent event) {
        Team chosenTeam = comboBoxTeams.getValue();
            if (chosenTeam != null && !teamObservableList.contains(chosenTeam)) {
                teamObservableList.add(chosenTeam);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Escoge un equipo válido");
                alert.showAndWait();
            }
    }

    @FXML
    private void addAllTeams(ActionEvent event) {
        List<Team> allTeamsList = new ArrayList<>();
        try {
            allTeamsList = teamDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        teamObservableList.addAll(allTeamsList);
    }


    @FXML
    private void handleCloseButtonAction() {
        if(!teamObservableList.isEmpty() || ((teamObservableList.size() / 2) != 0)) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("El numero de equipos debe ser par");
            alert.showAndWait();
        }
    }

    @FXML
    private void refresh(ActionEvent event) {
        tableTeams.refresh();
    }
    public List<Team> getTeams(){

        if(teamObservableList.toArray().length / 2 !=0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("El numero de equipos debe ser par");
        }
        return (List<Team>)teamObservableList;
    }
}
