package ca.fantasybasketball.teamweeklystats.model;


import java.util.ArrayList;
import java.util.List;

public class Roster {

    //    private String fantasyTeamName;
    private List<Player> players = new ArrayList<Player>();

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    // read player's names and statistics to fill
    // your roster
//    public List<Player> getTeam(){
//
//        Stats stats = new Stats();
//        List<Player> roster = new ArrayList<Player>();
//        Scanner scanner = new Scanner(System.in);
//
//        for(int i=1; i<=2; i++) {
//            System.out.println("Enter Player Name");
//            String name = scanner.nextLine();
//            Player player = new Player();
//            player.setName(name);
//            player.setStats(stats.readStats());
//            roster.add(player);
//            System.out.println("Players Added: " + i);
//        }
//        return roster;
//    }
//
//    public Stats get(String playerName) {
//        return roster.get(playerName);
//    }
//
//    public void put(String playerName, Stats stats) {
//        roster.put(playerName, stats);


}
