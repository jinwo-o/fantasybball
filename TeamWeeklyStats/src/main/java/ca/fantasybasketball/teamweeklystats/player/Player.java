package ca.fantasybasketball.teamweeklystats.player;

import com.sun.org.glassfish.external.statistics.Stats;

// Player class represents a player in the NBA
// there is a unique identifier id, position, and
// the team they play for
public class Player {

    // private int id;
    private String name;
    // private String position;
    // private String team;
    public Stats stats;

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