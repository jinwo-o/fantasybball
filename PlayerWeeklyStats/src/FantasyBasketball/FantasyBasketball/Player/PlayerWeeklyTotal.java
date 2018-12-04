package FantasyBasketball.Player;

//import FantasyBasketball.Stats.Stats;

//package FantasyBasketball;
//
public class PlayerWeeklyTotal{

    private Player player;

    public Player calculateWeeklyTotal(Player player){
        int gamesLeft = player.stats.gamesLeft;
        player.stats.points = player.stats.points*gamesLeft;
        player.stats.threes = player.stats.threes*gamesLeft;
        player.stats.rebounds = player.stats.rebounds*gamesLeft;
        player.stats.assists = player.stats.assists*gamesLeft;
        player.stats.steals = player.stats.steals*gamesLeft;
        player.stats.blocks = player.stats.blocks*gamesLeft;
        player.stats.turnovers = player.stats.turnovers*gamesLeft;
        player.stats.DD = player.stats.DD*gamesLeft;

        return player;
    }
}