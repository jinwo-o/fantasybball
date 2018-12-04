package Roster;//package FantasyBasketball;

import java.util.ArrayList;
import java.util.List;
//import java.util.Map;
import java.util.Scanner;

public class Roster {

//    private String fantasyTeamName;
    protected List<Player> roster = new ArrayList<Player>();

    // read player's names and statistics to fill
    // your roster
    public List<Player> getTeam(){

        Stats stats = new Stats();
        List<Player> roster = new ArrayList<Player>();
        Scanner scanner = new Scanner(System.in);

        for(int i=1; i<=2; i++) {
            System.out.println("Enter Player Name");
            String name = scanner.nextLine();
            Player player = new Player();
            player.setName(name);
            player.setStats(stats.readStats());
            roster.add(player);
            System.out.println("Players Added: " + i);
        }
        return roster;
    }

    // fix this
//    public double totalCategory(Map<String, Stats> roster){
//        roster.get()
//    }


//
//    public Stats get(String playerName) {
//        return roster.get(playerName);
//    }
//
//    public void put(String playerName, Stats stats) {
//        roster.put(playerName, stats);
//    }

//    public String toString() {
//        return String.format("%s,%s,%s,%s,%s,%s", roster.get(FantasyBasketball.StatsConstants.PLAYER_NAME), roster.get(FantasyBasketball.StatsConstants.POINTS), roster.get(FantasyBasketball.StatsConstants.THREES),
//                roster.get(FantasyBasketball.StatsConstants.REBOUNDS), roster.get(FantasyBasketball.StatsConstants.ASSISTS), roster.get(FantasyBasketball.StatsConstants.STEALS), roster.get(FantasyBasketball.StatsConstants.BLOCKS),
//                roster.get(FantasyBasketball.StatsConstants.TURNOVERS), roster.get(FantasyBasketball.StatsConstants.DD));
//    }
}
