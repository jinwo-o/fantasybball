package ca.fantasybasketball.teamweeklystats.player;

import ca.fantasybasketball.teamweeklystats.stats.Stats;

public class PlayerWeeklyTotal {

    private PlayerWeeklyTotal()
    {
    }
    private Player player;

    public static Stats calculateWeeklyTotal(Player player){
        Stats stats = new Stats();
        int gamesLeft = player.stats.gamesLeft;
        stats.minutes = player.stats.minutes*gamesLeft;
        stats.points = player.stats.points*gamesLeft;
        stats.threes = player.stats.threes*gamesLeft;
        stats.rebounds = player.stats.rebounds*gamesLeft;
        stats.assists = player.stats.assists*gamesLeft;
        stats.steals = player.stats.steals*gamesLeft;
        stats.blocks = player.stats.blocks*gamesLeft;
        stats.turnovers = player.stats.turnovers*gamesLeft;
        stats.DD = player.stats.DD*gamesLeft;

        return stats;
    }
}