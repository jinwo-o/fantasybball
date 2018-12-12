package ca.fantasybasketball.teamweeklystats.player;

import ca.fantasybasketball.teamweeklystats.stats.Stats;

// Player class represents a player in the NBA
// there is a unique identifier id, position, and
// the team they play for
public class Player {

    // private int id;
    private String name;
    private String team;
    private String position;
    public Stats stats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

}
