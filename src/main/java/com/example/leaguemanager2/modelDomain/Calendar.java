package com.example.leaguemanager2.modelDomain;

import java.util.ArrayList;
import java.util.List;

public class Calendar {

    private List<Match> matchs;
    private List<Team> teams;
    public Calendar() {
        this.matchs = new ArrayList<>();
        this.teams = teams;
    }


    public List<Match> createCalendar(List<Team> teams) {
        List<Match> matches = new ArrayList<>();
        int n = teams.size();
        int rounds = n - 1;
        int matchesPerRound = n / 2;
        int totalMatches = rounds * matchesPerRound;


        List<Team> rotatedTeams = new ArrayList<>(teams);

        for (int round = 0; round < rounds; round++) {
            for (int match = 0; match < matchesPerRound; match++) {
                Team local = rotatedTeams.get(match);
                Team visitor = rotatedTeams.get(n - 1 - match);
                Match newMatch = new Match(local, visitor, round + 1);
                matches.add(newMatch);
            }

            rotatedTeams.add(1, rotatedTeams.remove(rotatedTeams.size() - 1));
        }

        return matches;
    }
}
