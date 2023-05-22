package com.example.leaguemanager2.dao;
import com.example.leaguemanager2.modelDomain.Player;
import com.example.leaguemanager2.utils.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO implements DAO<Player> {
    private final static String FINDALL = "select * from player";
    private final static String FINDBYNAME = "select * from player where name = ?";
    private final static String FINDBYID = "select * from player where player_id = ?";
    private final static String FINDBYTEAM = "select * from player where team_id = ?";
    private final static String DELETE = "delete from player where player_id = ?";
    private final static String DELETEBYNAME = "delete from player where name = ?";
    private final static String UPDATE = "update player set player_id = ?,name = ?, last_name = ?, alias = ?, dorsal = ?, team_id = ? where player_id = ?";
    private final static String INSERT = "insert into player(player_id,name,last_name,alias,dorsal,team_id) values(?,?,?,?,?,?)";

    private Connection connection;
     public PlayerDAO(Connection connection) {this.connection = connection;}
    public PlayerDAO() {this.connection = Connect.getConnect();}

    public static TeamDAO teamDAO = new TeamDAO();
    public static PlayerDAO playerdao;


    @Override
    public List findAll() throws SQLException {
        List<Player> players = new ArrayList<>();

        try(PreparedStatement pst = this.connection.prepareStatement(FINDALL)){

            try(ResultSet resultSet = pst.executeQuery()) {

                while(resultSet.next()) {
                    Player p = new Player();
                    p.setPlayer_id(resultSet.getInt("player_id"));
                    p.setName(resultSet.getString("name"));
                    p.setLastName(resultSet.getString("last_name"));
                    p.setAlias(resultSet.getString("alias"));
                    p.setDorsal(resultSet.getInt("dorsal"));
                    p.setTeam(teamDAO.findByid(resultSet.getInt("team_id")));
                    players.add(p);
                }
            }
        }
        return players;
    }

    @Override
    public Player findByid(int player_id) throws SQLException {
        Player result = null;
        PreparedStatement pst = this.connection.prepareStatement(FINDBYID);
        pst.setInt(1,player_id);
        try(ResultSet rs = pst.executeQuery()) {
            if(rs.next()) {
                result = new Player();
                result.setName(rs.getString("name"));
                result.setLastName(rs.getString("last_name"));
                result.setAlias(rs.getString("alias"));
                result.setDorsal(rs.getInt("dorsal"));
                result.setPlayer_id(rs.getInt("player_id"));
                result.setTeam(teamDAO.findByid(rs.getInt("team_id")));
            }
            return result;
        }

    }

    public Player findByName(String name) throws SQLException {
        Player result = null;
        PreparedStatement pst =this.connection.prepareStatement(FINDBYNAME);
        pst.setString(1,name);
            try(ResultSet rs = pst.executeQuery()) {
                if(rs.next()) {
                    result = new Player();
                    result.setName(rs.getString("name"));
                    result.setPlayer_id(rs.getInt("player_id"));
                    result.setLastName(rs.getString("last_name"));
                    result.setAlias(rs.getString("alias"));
                    result.setDorsal(rs.getInt("dorsal"));
                    result.setTeam(teamDAO.findByid(rs.getInt("team_id")));

                }
                return result;
            }
    }


    public Player findByTeam(int id) throws SQLException {
         Player result = null;
        PreparedStatement pst =this.connection.prepareStatement(FINDBYTEAM);
        pst.setInt(1,id);
        try(ResultSet rs = pst.executeQuery()) {
            if(rs.next()) {
                result  = new Player();
                result.setName(rs.getString("name"));
                result.setPlayer_id(rs.getInt("player_id"));
                result.setLastName(rs.getString("last_name"));
                result.setAlias(rs.getString("alias"));
                result.setDorsal(rs.getInt("dorsal"));

            }
            return result;
        }

    }
    public List<Player> findByTeamWhole(int id) throws SQLException {
        List<Player> players = new ArrayList<>();
         Player result = null;
        PreparedStatement pst =this.connection.prepareStatement(FINDBYTEAM);
        pst.setInt(1,id);
        try(ResultSet rs = pst.executeQuery()) {
            if(rs.next()) {
                result  = new Player();
                result.setName(rs.getString("name"));
                result.setPlayer_id(rs.getInt("player_id"));
                result.setLastName(rs.getString("last_name"));
                result.setAlias(rs.getString("alias"));
                result.setDorsal(rs.getInt("dorsal"));
                players.add(result);

            }
            return players;
        }

    }


    public Player save(Player entity) throws SQLException {
        Player result = new Player();
        if (entity != null) {
            Player p = findByid(entity.getPlayer_id());
            if (p == null) {
                //INSERT
                try (PreparedStatement pst = this.connection.prepareStatement(INSERT)) {
                    pst.setInt(1, entity.getPlayer_id());
                    pst.setString(2, entity.getName());
                    pst.setString(3, entity.getLastName());
                    pst.setString(4, entity.getAlias());
                    pst.setInt(5, entity.getDorsal());
                    pst.setInt(6, entity.getTeam().getTeam_id());
                    pst.executeUpdate();
                    /** Team */
                    TeamDAO teamDAO = new TeamDAO(this.connection);
                    result.setTeam(teamDAO.findByid(entity.getTeam().getTeam_id()));

                }
            } else {
                //UPDATE
                try (PreparedStatement pst = this.connection.prepareStatement(UPDATE)) {
                    pst.setInt(1, entity.getPlayer_id());
                    pst.setString(2, entity.getName());
                    pst.setString(3, entity.getLastName());
                    pst.setString(4, entity.getAlias());
                    pst.setInt(5, entity.getDorsal());
                    pst.setInt(6, entity.getTeam().getTeam_id());
                    pst.setInt(7, entity.getPlayer_id());
                    pst.executeUpdate();
                    /** Team */
                    TeamDAO teamDAO = new TeamDAO(this.connection);
                    result.setTeam(teamDAO.findByid(entity.getTeam().getTeam_id()));
                }
                result = entity;
            }

        }
        return result;
    }

    @Override
    public void delete(Player p) throws SQLException {
        if(p != null) {
            try(PreparedStatement pst=this.connection.prepareStatement(DELETE)){
                pst.setInt(1, p.getPlayer_id());
                pst.executeUpdate();
                pst.close();
            }

        }
    }

    public void delete(String name) throws SQLException {
        if(name != null) {
            try(PreparedStatement pst=this.connection.prepareStatement(DELETEBYNAME)){
                pst.setString(1, name);
                pst.executeUpdate();
                pst.close();
            }

        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
