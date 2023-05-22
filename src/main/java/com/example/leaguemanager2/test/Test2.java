package com.example.leaguemanager2.test;

import com.example.leaguemanager2.dao.MatchDAO;
import com.example.leaguemanager2.dao.TeamDAO;
import com.example.leaguemanager2.modelDomain.Match;
import com.example.leaguemanager2.modelDomain.Team;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.sql.SQLException;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class Test2 {
    static TeamDAO teamDAO;
    @BeforeClass
    public static void setUpDAO() {
        teamDAO = new TeamDAO();
    }

    @Test
    void testSave() throws SQLException {

        assertEquals(true, insertTeam());
    }

    @Test
    void testFindAll() throws SQLException {
        assertEquals(teamDAO.findAll(), findALlTeams());
    }


    @Test
     void removeTest(){
        try {
            assertEquals(true,removeTeam());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean removeTeam() throws SQLException {
        boolean result = false;
         Team t =teamDAO.findByid(99);
         if(t!=null){
             teamDAO.delete(t);
             result = true;
         }

        return result;
    }
    public static boolean insertTeam() throws SQLException {
        TeamDAO teamDAO = new TeamDAO();
        boolean flag = false;

        Team t = new Team();
        t.setName("Girona");
        t.setAbbreviation("GIR");
        t.setTeam_id(99);
        t.setPlayers(null);
        try {
            teamDAO.save(t);
            flag = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (flag) {
            return flag;
        } else {
            flag = false;
            return flag;
        }
    }

    public static List<Team> findALlTeams() throws SQLException {
        MatchDAO matchDAO = new MatchDAO();
        teamDAO = new TeamDAO();
        List<Team> teams = teamDAO.findAll();

        return teams;
    }


    public static void findTeamMatchs() throws SQLException {

            MatchDAO matchDAO = new MatchDAO();
            TeamDAO teamDAO = new TeamDAO();
            List<Match> m2 = matchDAO.findByTeam(teamDAO.findByName("Madrid").getTeam_id());

            for (Match m : m2) {
                System.out.println(m);

            }

        }
    }

