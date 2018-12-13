package ca.fantasybasketball.teamweeklystats.stats;

import ca.fantasybasketball.teamweeklystats.model.Player;
import ca.fantasybasketball.teamweeklystats.model.Stats;

public class PlayerWeeklyTotal {

    public static Stats calculateWeeklyTotal(Player player){
        Stats statsTotal = new Stats();
        Stats stat1 = player.getStats();
        int gamesLeft = stat1.getGamesLeft();
        statsTotal.minutes = stat1.minutes*gamesLeft;
        statsTotal.points = stat1.points*gamesLeft;
        statsTotal.threes = stat1.threes*gamesLeft;
        statsTotal.rebounds = stat1.rebounds*gamesLeft;
        statsTotal.assists = stat1.assists*gamesLeft;
        statsTotal.steals = stat1.steals*gamesLeft;
        statsTotal.blocks = stat1.blocks*gamesLeft;
        statsTotal.turnovers = stat1.turnovers*gamesLeft;
        statsTotal.DD = stat1.DD*gamesLeft;

        return statsTotal;
    }
}