package com.example.leaguemanager2.controller;

import com.example.leaguemanager2.dao.TeamDAO;
import com.example.leaguemanager2.modelDomain.Player;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TeamsSceneController implements Initializable {
    @FXML
    private Button backBtn;
    @FXML
    private Button closeButton;
    @FXML
    private TableView<Team> table;
    @FXML
    private Button deleteBtn;
    @FXML
    private TableColumn<Team,Integer> idColumn;
    @FXML
    private TableColumn<Team, String> teamNameColumn;
    @FXML
    private TableColumn<Team, String> teamAbbColumn;
    @FXML
    private TableColumn<Team, Image> teamIconColumn;
    public ObservableList<Team> teamObservableList;

    TeamDAO teamDAO = new TeamDAO();

    /*
    Button that regret to the main scene
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teamObservableList = FXCollections.observableArrayList();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("team_id"));
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        teamAbbColumn.setCellValueFactory(new PropertyValueFactory<>("abbreviation"));
        table.setItems(teamObservableList);
        loadTeams();
    }
    @FXML
    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/leaguemanager2/views/mainScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();


        MainSceneController controller = loader.getController();

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        stage.setOnCloseRequest(e -> controller.closeWindows());
        Stage myStage = (Stage) this.backBtn.getScene().getWindow();
        myStage.close();

    }

    public void closeWindows() {
    }

    @FXML
    private void handleCloseButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void loadTeams()  {
        List<Team> teams = null;
        try {
            teams = teamDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Team> teamList = FXCollections.observableArrayList(teams);
        table.setItems(teamList);
    }

    /*
    function to add a team of modal view to create it
     */
    @FXML
    private void addTeam(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/leaguemanager2/views/addTeam.fxml"));
        try {
            Parent root = loader.load();

            AddTeamController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.showAndWait();

            Team t = controller.getTeam();
            if (t != null) {
                teamDAO.save(t);
                teamObservableList.add(t);
                loadTeams(); // call loadTeams to set allteams to table
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        Team t = this.table.getSelectionModel().getSelectedItem();

        if(t==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Selecciona un equipo para borrar");
            alert.showAndWait();
        }else{
            teamObservableList.remove(t);
            try {
                teamDAO.delete(t);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.table.refresh();
            loadTeams();
        }
    }

}


