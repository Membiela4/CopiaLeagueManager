package com.example.leaguemanager2.test;

import com.example.leaguemanager2.dao.PlayerDAO;
import com.example.leaguemanager2.dao.TeamDAO;
import com.example.leaguemanager2.modelDomain.Player;
import com.example.leaguemanager2.modelDomain.Team;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import static org.testng.AssertJUnit.assertEquals;

public class Test1 {


    //Testing PlayerDAO
        static PlayerDAO playerDAO;
        static TeamDAO teamDAO;
        @BeforeClass
        public static void setUpClass() {
            playerDAO = new PlayerDAO();
        }

        @Test
        void testGetAll(){
            assertEquals(true, getAll());
        }
        @Test
         void testCreatePlayer(){
            try {
                assertEquals(true, createPlayer());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        List<Player> allPlayers;

        public boolean getAll() {
            try {
                allPlayers = playerDAO.findAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            for (Player p:
                    allPlayers) {
                System.out.println(p);
            }

            if(allPlayers!=null)
                return true;
            else
                return false;

        }

        //TEST CREAR JUGADOR

    public boolean createPlayer() throws SQLException {
            teamDAO = new TeamDAO();
        Player p = new Player();
        p.setName("Federico");
        p.setLastName("Valverde");
        p.setAlias("Valverde");
        p.setDorsal(15);
        p.setTeam(teamDAO.findByName("Madrid"));
        playerDAO.save(p);

        Player p2 = new Player();
        p2.setName("Robert");
        p2.setLastName("Lewandoski");
        p2.setAlias("Lewandoski");
        p2.setDorsal(9);
        p2.setTeam(teamDAO.findByName("Barca"));
        playerDAO.save(p2);

        if(p!=null && p2!=null){
            return true;
        }else {
            return false;
        }

    }


}

