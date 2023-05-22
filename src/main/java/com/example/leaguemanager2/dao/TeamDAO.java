package com.example.leaguemanager2.dao;

import com.example.leaguemanager2.modelDomain.Player;
import com.example.leaguemanager2.modelDomain.Team;
import com.example.leaguemanager2.utils.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO implements DAO<Team> {
    private final static String FINDALL = "select * from team";
    private final static String FINDBYNAME = "select * from team where team_name = ?";
    private final static String FINDBYID =" select * from team where team_id = ?";
    private final static String DELETE = "delete from team where team_id = ?";
    private final static String UPDATE = "update team set team_id =?, team_name = ?, abbreviation = ?,icon = ?,num_players = ?, where team_id = ?";
    private final static String INSERT = "insert into team(team_id,team_name,abbreviation) values(?,?,?)";

    private Connection connection;

    public static PlayerDAO playerdao = new PlayerDAO();

    public TeamDAO(Connection connection) {this.connection = connection;}
    public TeamDAO() {this.connection = Connect.getConnect();}
    @Override
    public List findAll() throws SQLException {
        List<Team> teams = new ArrayList<>();

        try(PreparedStatement pst = this.connection.prepareStatement(FINDALL)){

            try(ResultSet resultSet = pst.executeQuery()) {

                while(resultSet.next()) {
                    Team t = new Team();
                    t.setTeam_id(resultSet.getInt("team_id"));
                    t.setName(resultSet.getString("team_name"));
                    t.setAbbreviation(resultSet.getString("abbreviation"));
                    t.setPlayers(playerdao.findByTeamWhole(t.getTeam_id()));
                    teams.add(t);
                }
            }
        }
        return teams;
    }

    @Override
    public Team findByid(int id) throws SQLException {
        Team result = null;
        PreparedStatement pst =this.connection.prepareStatement(FINDBYID);
        pst.setInt(1,id);
        try(ResultSet rs = pst.executeQuery()) {
            if(rs.next()) {
                result = new Team();
                result.setName(rs.getString("team_name"));
                result.setTeam_id(rs.getInt("team_id"));
                result.setAbbreviation(rs.getString("abbreviation"));
                result.setPlayers(playerdao.findByTeamWhole(id));

            }
            return result;
        }
    }


    public Team findByName(String name) throws SQLException {
        Team result = null;
        PreparedStatement pst =this.connection.prepareStatement(FINDBYNAME);
        pst.setString(1,name);
        try(ResultSet rs = pst.executeQuery()) {
            if(rs.next()) {
                result = new Team();
                result.setName(rs.getString("team_name"));
                result.setTeam_id(rs.getInt("team_id"));
                result.setAbbreviation(rs.getString("abbreviation"));
                //result.setIcon(rs.getBlob("icon"));
                result.setPlayers(playerdao.findByTeamWhole(result.getTeam_id()));

            }
            return result;
        }
    }


    @Override
    public Team save(Team entity) throws SQLException {
            Team result = new Team();
            if(entity!=null) {
                Team t = findByid(entity.getTeam_id());
                if(t == null) {
                    //INSERT
                    try (PreparedStatement pst = this.connection.prepareStatement(INSERT)) {
                        pst.setInt(1, entity.getTeam_id());
                        pst.setString(2, entity.getName());
                        pst.setString(3, entity.getAbbreviation());
                        pst.executeUpdate();
                        /** Players of team
                        PlayerDAO pdao = new PlayerDAO(this.connection);
                        List<Player> players = pdao.findByTeamWhole(entity.getTeam_id());

                        for(Player p : players) {
                            pdao.delete(p);
                        }
                        for(Player p : entity.getPlayers()) {
                            p.setTeam(entity);
                            pdao.save(p);
                        }*/
                    }
                }else {
                    //UPDATE
                    try (PreparedStatement pst = this.connection.prepareStatement(UPDATE)) {
                        pst.setInt(1, entity.getTeam_id());
                        pst.setString(2, entity.getName());
                        pst.setString(3, entity.getAbbreviation());
                        pst.setInt(5, 0); //cambiar por el tamaño del array de jugadores
                        pst.executeUpdate();
                    }
                    /** Players of team */
                    PlayerDAO pdao = new PlayerDAO(this.connection);
                    List<Player> players = pdao.findByTeamWhole(entity.getTeam_id());

                    for(Player p : players) {
                        pdao.delete(p);
                    }
                    for(Player p : entity.getPlayers()) {
                        p.setTeam(entity);
                        pdao.save(p);
                    }
                }
                result=entity;
            }
            return result;
    }

    @Override
    public void delete(Team t) throws SQLException {
        if(t!=null) {
            try(PreparedStatement pst=this.connection.prepareStatement(DELETE)){
                pst.setInt(1, t.getTeam_id());
                pst.executeUpdate();
                pst.close();
            }

        }
    }

    @Override
    public void close() throws Exception {
        playerdao.close();
        connection.close();
    }
}
