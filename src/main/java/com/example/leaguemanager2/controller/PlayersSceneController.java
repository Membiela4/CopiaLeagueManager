package com.example.leaguemanager2.controller;

import com.example.leaguemanager2.dao.PlayerDAO;
import com.example.leaguemanager2.dao.TeamDAO;
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
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;
import org.controlsfx.control.action.Action;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlayersSceneController implements Initializable {

    PlayerDAO playerDAO = new PlayerDAO();

    @FXML
    private Button backBtn;

    @FXML
    private Button closeButton;
    @FXML
    private Button addPlayerBtn;
    @FXML
    private Button deletePlayerBtn;
    @FXML
    private TableView<Player> table;
    @FXML
    private TableColumn<Player, Integer> idColumn;
    @FXML
    private TableColumn<Player, String> nameColumn;
    @FXML
    private TableColumn<Player, String> aliasColumn;
    @FXML
    private TableColumn<Player, Integer> dorsalColumn;
    @FXML
    private TableColumn<Player, Team> teamColumn;
    @FXML
    private Button refreshBtn;
    @FXML
    public TextField nameTextField;
    @FXML
    private TextField aliasTextField;
    @FXML
    private TextField dorsalTextField;
    @FXML
    private ChoiceBox teamChoiceField;

    private ObservableList<Player> playersList;
    public void closeWindows() {
    }
     private static TeamDAO teamDAO;
    /*
    Method that close the actual scene
     */
    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();;
        currentStage = (Stage) closeButton.getScene().getWindow();
        currentStage.close();
    }

    /*
    Regret to the previous view
     */
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
        Stage myStage = (Stage) this.backBtn.getScene().getWindow();
        myStage.close();

    }

    /*
    When the view is started, the fields are setted and the players loaded
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        playersList = FXCollections.observableArrayList();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("player_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        aliasColumn.setCellValueFactory(new PropertyValueFactory<>("alias"));
        dorsalColumn.setCellValueFactory(new PropertyValueFactory<>("dorsal"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        teamDAO= new TeamDAO();
        try {
            List<Team> teamList = new ArrayList<>();
            teamList= teamDAO.findAll();
            ObservableList<Team> teamObservableList = FXCollections.observableArrayList(teamList);
            teamChoiceField.setItems(teamObservableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        loadPlayers();

    }

    /*
    Function to open a modal view that creates a new Player an insert it into database
     */
    @FXML
    private void addPlayer(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/leaguemanager2/views/addPlayer.fxml"));
        try {
            Parent root = loader.load();

            AddPlayerController controller = loader.getController();
            Scene scene =new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.showAndWait();
            
            Player p = controller.getPlayer();
            if (p != null) {
                playerDAO.save(p);
                playersList.add(p);
                loadPlayers();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Function to load players of database
     */
    public void loadPlayers()  {
        List<Player> players = null;
        try {
            players = playerDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        playersList.clear();

        playersList.addAll(players);

        table.setItems(playersList);
    }

    @FXML
    private void select() {
        Player p = this.table.getSelectionModel().getSelectedItem();
        nameTextField.setText(p.getName());
        aliasTextField.setText(p.getAlias());
        dorsalTextField.setText(String.valueOf(p.getDorsal()));
        teamChoiceField.setValue(p.getTeam());
    }
    /*
    Function to edit selected player it uses four fields in the view
     */
    @FXML
    private void modify(ActionEvent event) {
        Player p = this.table.getSelectionModel().getSelectedItem();
        if(p==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Selecciona un jugador");
            alert.showAndWait();
        }else{
            try {
                Player aux = new Player();
                aux.setPlayer_id(p.getPlayer_id());
                aux.setName(nameTextField.getText());
                aux.setAlias(aliasTextField.getText());
                aux.setDorsal(Integer.parseInt(dorsalTextField.getText()));
                aux.setTeam((Team) teamChoiceField.getValue());
                playerDAO.delete(p.getName());
                playerDAO.save(aux);
                this.table.refresh();
                loadPlayers();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /*
    Function to delete a selected player
     */
    @FXML
    private void delete(ActionEvent event) {
        Player p = this.table.getSelectionModel().getSelectedItem();

        if(p==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("no se ha encontrado jugador");
            alert.showAndWait();
        }else{
            playersList.remove(p);
            try {
                playerDAO.delete(p);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.table.refresh();
        }
    }
    @FXML
    private void refresh() {
        this.table.refresh();
    }
}




