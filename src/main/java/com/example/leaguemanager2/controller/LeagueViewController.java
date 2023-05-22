package com.example.leaguemanager2.controller;

import com.example.leaguemanager2.dao.MatchDAO;
import com.example.leaguemanager2.dao.TeamDAO;
import com.example.leaguemanager2.modelDomain.Calendar;
import com.example.leaguemanager2.modelDomain.Match;
import com.example.leaguemanager2.modelDomain.Player;
import com.example.leaguemanager2.modelDomain.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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



public class LeagueViewController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button setResultBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TableView<Match> table;
    @FXML
    private Button createCalendar;
    @FXML
    public Button addTeamsToCalendar;
    private List<Team> teams;
    private List<Match> matchs;
    TeamDAO teamDAO = new TeamDAO();
    MatchDAO matchDAO = new MatchDAO();

    @FXML
    TableColumn<Match, String> localNameColumn;
    @FXML
    TableColumn<Match, Integer> localResultColumn;
    @FXML
    TableColumn<Match, String> visitorNameColumn;
    @FXML
    TableColumn<Match, Integer> visitorResultColumn;
    @FXML
    TableColumn<Match, Integer> matchweekColumn;

    public ObservableList<Match> matchObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        matchObservableList = FXCollections.observableArrayList();
        localNameColumn.setCellValueFactory(new PropertyValueFactory<>("local"));
        localResultColumn.setCellValueFactory(new PropertyValueFactory<>("local_result"));
        visitorNameColumn.setCellValueFactory(new PropertyValueFactory<>("visitor"));
        visitorResultColumn.setCellValueFactory(new PropertyValueFactory<>("visitor_result"));
        matchweekColumn.setCellValueFactory(new PropertyValueFactory<>("matchweek"));

        loadMatchs();
    }

    /*
    set calendar to table(if it is created)
     */
    public void loadMatchs() {
        try {
            matchs = matchDAO.findAll();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Match> matchList = FXCollections.observableArrayList(matchs);
        table.setItems(matchList);
    }

    public void closeWindows() {
    }

    public void back() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/leaguemanager2/views/mainScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        MainSceneController controller = loader.getController();

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        stage.setOnCloseRequest(e -> controller.closeWindows());
        Stage myStage = (Stage) this.backButton.getScene().getWindow();
        myStage.close();

    }
    /*
   this function open a view to select teams and create the league calendar
    */
    @FXML
    private List<Team> addTeamsToCalendar() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/leaguemanager2/views/createCalendar.fxml"));
        try {
            Parent root = loader.load();

            CreateCalendarController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.showAndWait();


            return controller.getTeams();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void createCalendar(ActionEvent event) throws SQLException {

        Calendar calendar = new Calendar();
        List<Team> teams = addTeamsToCalendar();

        List<Match> calendarMatchs = new ArrayList<>(calendar.createCalendar(teams));


        for (Match m : calendarMatchs) {
            matchDAO.save(m);
        }
        loadMatchs();
        this.table.refresh();

    }
    /*
    function that remove calendar
     */

    @FXML
    public void cleanTable(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("VAS A ELIMINAR EL CALENDARIO");
        alert.setContentText("¿Estas seguro de que deseas eliminar todo el calendario?");
        alert.showAndWait();
        for (Match m : matchs) {
            try {
                matchDAO.delete(m);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            loadMatchs();
            this.table.refresh();
        }

    }


    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage = (Stage) closeButton.getScene().getWindow();
        currentStage.close();
    }

    /*
    this function open a view to set the match´s result
     */
    @FXML
    private void setResult(ActionEvent event) {
        Match selectedMatch = table.getSelectionModel().getSelectedItem();

        if (selectedMatch == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Selecciona un equipo");
            alert.show();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/leaguemanager2/views/setResultScene.fxml"));
            try {
                Parent root = loader.load();

                SetResultController controller = loader.getController();
                controller.setMatch(selectedMatch);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.showAndWait();

                table.refresh();
                loadMatchs();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
