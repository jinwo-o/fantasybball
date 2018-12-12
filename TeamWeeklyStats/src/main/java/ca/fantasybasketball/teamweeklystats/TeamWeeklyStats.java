package ca.fantasybasketball.teamweeklystats;

import ca.fantasybasketball.teamweeklystats.player.Player;
import ca.fantasybasketball.teamweeklystats.player.PlayerWeeklyTotal;
import ca.fantasybasketball.teamweeklystats.readers.ReadCSV;
import ca.fantasybasketball.teamweeklystats.roster.Roster;
import ca.fantasybasketball.teamweeklystats.stats.AddStats;
import ca.fantasybasketball.teamweeklystats.stats.Stats;

import java.io.IOException;

public class TeamWeeklyStats {

    public static void main(String[] args) throws IOException {

        Roster roster = ReadCSV.CSV_Reader();
//        System.out.println(roster.roster.get(0).getPosition());
//        System.out.println(roster.roster.get(1).getPosition());

        int i = 0;
        Stats totalStats = new Stats();
        while(i < roster.roster.size()){
            Player player = roster.roster.get(i);
            Stats stats = PlayerWeeklyTotal.calculateWeeklyTotal(player);
            totalStats = AddStats.total(totalStats, stats);
            i++;
        }

        System.out.println("Minutes: "+ totalStats.getMinutes());
        System.out.println("Threes: " + totalStats.getThrees());
        System.out.println("Rebounds: " + totalStats.getRebounds());
        System.out.println("Assists: " + totalStats.getAssists());
        System.out.println("Steals: " + totalStats.getSteals());
        System.out.println("Blocks: " + totalStats.getBlocks());
        System.out.println("Turnovers: " + totalStats.getTurnovers());
        System.out.println("DD: " + totalStats.getDD());
        System.out.println("Points: " + totalStats.getPoints());
    }

}
