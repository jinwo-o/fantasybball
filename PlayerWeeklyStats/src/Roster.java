import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Roster {

    private string fantasyTeamName;
    protected Map<String, Stats> roster = new HashMap<>();

    // read player's names and statistics to fill
    // your roster
    public Map<String, Stats> getTeam(){

        Stats stats = new Stats();
        Map<String, Stats> roster = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        for(int i=1; i<=2; i++) {
            System.out.println("Enter Player Name");
            String name = scanner.nextLine();
            roster.put(name, stats.readStats());
            System.out.println("Players Added: " + i);
        }
        return roster;
    }

    // fix this
    public double totalCategory(Map<String, Stats> roster){
        roster.get()
    }



    public Stats get(String playerName) {
        return roster.get(playerName);
    }

    public void put(String playerName, Stats stats) {
        roster.put(playerName, stats);
    }

    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s", roster.get(StatsConstants.PLAYER_NAME), roster.get(StatsConstants.POINTS), roster.get(StatsConstants.THREES),
                roster.get(StatsConstants.REBOUNDS), roster.get(StatsConstants.ASSISTS), roster.get(StatsConstants.STEALS), roster.get(StatsConstants.BLOCKS),
                roster.get(StatsConstants.TURNOVERS), roster.get(StatsConstants.DD));
    }
}
