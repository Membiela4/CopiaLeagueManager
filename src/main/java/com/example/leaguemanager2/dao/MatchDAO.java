package com.example.leaguemanager2.dao;

import com.example.leaguemanager2.modelDomain.Match;
import com.example.leaguemanager2.modelDomain.Player;
import com.example.leaguemanager2.modelDomain.Team;
import com.example.leaguemanager2.utils.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatchDAO implements DAO<Match>  {
    private final static String FINDALL = "select * from `matchs`";
    private final static String FINDBYID = "select * from `matchs` where match_id = ?";

    private final static String FINDBYDAY = "select * from `matchs` where matchweek = ?";
    private final static String FINDBYTEAM = "select * from `matchs` where local_id = ? or visitor_id =?";
    private final static String DELETE = "delete from `matchs` where match_id = ?";
    private final static String UPDATE = "update `matchs` set match_id = ?,local_id =?, visitor_id = ?, matchweek = ?, local_result = ?, visitor_result = ?  where match_id = ?";
    private final static String INSERT = "insert into `matchs`(match_id,local_id,visitor_id,matchweek,local_result, visitor_result) values (?,?,?,?,?,?)";

    private Connection connection;
    public MatchDAO() {this.connection = Connect.getConnect();}

    public MatchDAO(Connection connection) {this.connection = connection;}
    private PlayerDAO playerDAO = new PlayerDAO();
    private TeamDAO teamDAO = new TeamDAO();

    public List<Match> findAll() throws SQLException {
        List<Match> matchs = new ArrayList<>();

        try(PreparedStatement pst = this.connection.prepareStatement(FINDALL)){

            try(ResultSet resultSet = pst.executeQuery()) {



                while(resultSet.next()) {
                    Match m = new Match();
                    m.setMatch_id(resultSet.getInt("match_id"));
                    m.setLocal(teamDAO.findByid(resultSet.getInt("local_id")));
                    m.setVisitor(teamDAO.findByid(resultSet.getInt("visitor_id")));
                    m.setMatchweek(resultSet.getInt("matchweek"));
                    m.setLocal_result(resultSet.getInt("local_result"));
                    m.setVisitor_result(resultSet.getInt("visitor_result"));
                    matchs.add(m);

                }
            }
        }
        return matchs;
    }




    public Match findByid(int id) {
        try (PreparedStatement pst = this.connection.prepareStatement(FINDBYID)) {
            pst.setInt(1, id);
            try (ResultSet resultSet = pst.executeQuery()) {
                if (resultSet.next()) {
                    Match m = new Match();
                    m.setMatch_id(resultSet.getInt("match_id"));
                    m.setLocal(teamDAO.findByid(resultSet.getInt("local_id")));
                    m.setVisitor(teamDAO.findByid(resultSet.getInt("visitor_id")));
                    m.setMatchweek(resultSet.getInt("matchweek"));
                    m.setLocal_result(resultSet.getInt("local_result"));
                    m.setVisitor_result(resultSet.getInt("visitor_result"));

                    return m;
                } else {

                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public List<Match> findByTeam(int id) throws SQLException {
        List<Match> matchs = new ArrayList<>();
        Match result = null;
        PreparedStatement pst =this.connection.prepareStatement(FINDBYTEAM);
        pst.setInt(1,id);
        pst.setInt(2,id);
        try(ResultSet rs = pst.executeQuery()) {
            if(rs.next()) {
                result  = new Match();
                result.setMatch_id(rs.getInt("match_id"));
                result.setLocal(teamDAO.findByid(rs.getInt("local_id")));
                result.setVisitor(teamDAO.findByid(rs.getInt("visitor_id")));
                result.setMatchweek(rs.getInt("matchweek"));
                result.setLocal_result(rs.getInt("local_result"));
                result.setVisitor_result(rs.getInt("visitor_result"));

                matchs.add(result);

            }
            return matchs;
        }

    }

    @Override
    public Match save(Match entity) throws SQLException {
        Match result = new Match();
        if(entity!=null) {
            Match m = findByid(entity.getMatch_id());
            if(m== null) {
                //INSERT
                try (PreparedStatement pst = this.connection.prepareStatement(INSERT)) {
                    pst.setInt(1, entity.getMatch_id());
                    pst.setInt(2, entity.getLocal().getTeam_id());
                    pst.setInt(3, entity.getVisitor().getTeam_id());
                    pst.setInt(4,entity.getMatchweek());
                    pst.setInt(5,entity.getLocal_result());
                    pst.setInt(6,entity.getVisitor_result());
                    pst.executeUpdate();
                }
            }else {
                //UPDATE
                try (PreparedStatement pst = this.connection.prepareStatement(UPDATE)) {
                    pst.setInt(1, entity.getMatch_id());
                    pst.setInt(2, entity.getLocal().getTeam_id());
                    pst.setInt(3, entity.getVisitor().getTeam_id());
                    pst.setInt(4,entity.getMatchweek());
                    pst.setInt(5,entity.getLocal_result());
                    pst.setInt(6,entity.getVisitor_result());
                    pst.setInt(7,entity.getMatch_id());
                    pst.executeUpdate();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            result=entity;
        }
        return result;
    }

    @Override
    public void delete(Match m) throws SQLException {
        if(m!=null) {
            try(PreparedStatement pst=this.connection.prepareStatement(DELETE)){
                pst.setInt(1, m.getMatch_id());
                pst.executeUpdate();
                

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }


    public Object findByDay(int day) throws SQLException {

        try(PreparedStatement pst = this.connection.prepareStatement(FINDBYDAY)){
            pst.setInt(1,day);
            try(ResultSet resultSet = pst.executeQuery()) {
                    Match m = new Match();
                    m.setMatch_id(resultSet.getInt("match_id"));
                    m.setLocal(teamDAO.findByid(resultSet.getInt("local_id")));
                    m.setVisitor(teamDAO.findByid(resultSet.getInt("visitor_id")));
                    m.setMatchweek(resultSet.getInt("matchweek"));
                    m.setLocal_result(resultSet.getInt("local_result"));
                    m.setVisitor_result(resultSet.getInt("visitor_result"));

                    return m;


            }
        }

    }

    public void close() throws Exception {
        this.connection.close();
        this.playerDAO.close();
        this.teamDAO.close();
    }
}
