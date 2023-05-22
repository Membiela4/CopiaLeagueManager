package com.example.leaguemanager2.modelDomain;

import java.util.List;
import java.util.Objects;

public class Match {
    private int match_id; //auto-increment id of each match
    private Team local; //First team which play as Local
    private Team visitor; //Second team as visitor
    private int matchweek; //Jornada
    private int local_result;
    private int visitor_result;

    public Match() {

    }

    public Match(int id, Team t1, Team t2, int matchweek, int local_result, int visitor_result) {
        this.match_id = id;
        this.local = t1;
        this.visitor = t2;
        this.matchweek = matchweek;
        this.local_result = local_result;
        this.visitor_result = visitor_result;
    }

    //Getter and Setters
    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public Team getLocal() {
        return local;
    }

    public void setLocal(Team local) {
        this.local = local;
    }

    public Team getVisitor() {
        return visitor;
    }

    public void setVisitor(Team visitor) {
        this.visitor = visitor;
    }

    public int getMatchweek() {
        return matchweek;
    }

    public void setMatchweek(int matchweek) {
        this.matchweek = matchweek;
    }

    public int getLocal_result() {
        return local_result;
    }

    public void setLocal_result(int local_result) {
        this.local_result = local_result;
    }

    public int getVisitor_result() {
        return visitor_result;
    }

    public void setVisitor_result(int visitor_result) {
        this.visitor_result = visitor_result;
    }

    //Constructors
    public Match(Team local, Team visitor, int matchweek) {
        this.local = local;
        this.visitor = visitor;
        this.matchweek = matchweek;
    }
    //Equals and HashCode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(getMatch_id(), match.getMatch_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMatch_id());
    }

    //ToString

    @Override
    public String toString() {
        return "Match{" +
                "match_id : '" + match_id + '\'' +
                ", local : " + local.getName() +
                ", visitor : " + visitor.getName() +
                ", matchweek : " + matchweek +
                '}';
    }

    public boolean containsTeams(Team team1, Team team2) {
        return (local.equals(team1) && visitor.equals(team2)) || (local.equals(team2) && visitor.equals(team1));
    }


}
