package FantasyBasketball.Player;

import FantasyBasketball.Stats.Stats;

// Player class represents a player in the NBA
// there is a unique identifier id, position, and
// the team they play for
public class Player {
//    private int id;
    private String name;
//    private String position;
//    private String nbaTeamName;
    public Stats stats;



    // calculates the player's total expected stats
    // for that week, ex: 3 games, 2.0 3pm = 6 3pm for
    // the week
    public double weeklyStatsCalc(int gamesLeft, String category, double stats){
        return gamesLeft*stats;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
