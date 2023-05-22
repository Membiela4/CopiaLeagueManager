package com.example.leaguemanager2.modelDomain;

import java.util.Objects;

public class Player extends Person{

    private int player_id;
    private String alias;
    private int dorsal; //each dorsal cant be duplicated for each team
    private Team team;

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Player(int player_id, String name, String lastName, String alias, int dorsal) {
        super(name, lastName);
        this.player_id = player_id;
        this.alias = alias;
        this.dorsal = dorsal;
    }

    public Player(String name, String lastName, String alias, int dorsal,Team team) {
        this.setName(name);
        this.setLastName(lastName);
        this.setAlias(alias);
        this.setDorsal(dorsal);
        this.setTeam(team);
    }
    public Player() {
        this.setPlayer_id(0); ;
        this.setName("");
        this.setLastName("");
        this.setDorsal(0);
        this.setAlias("");

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Player player = (Player) o;
        return getDorsal() == player.getDorsal() && Objects.equals(getPlayer_id(), player.getPlayer_id()) && Objects.equals(getAlias(), player.getAlias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPlayer_id());
    }

    @Override
    public String toString() {
        return "Player{" +
                "player_id=" + player_id +
                ", alias='" + alias + '\'' +
                ", dorsal=" + dorsal +
                ", team=" + team +
                '}';
    }

    public void setTeam(Player p, Team t) {
            p.setTeam(t);
    }
}
