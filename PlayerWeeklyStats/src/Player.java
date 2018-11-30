import java.util.Scanner;
import java.lang.Double;

public class Player {
    private String name;
    private int gamesLeft;
    private Stats stats;

    public double weeklyStatsCalc(int gamesLeft, double stats){
        return gamesLeft*stats;
    }

    public Stats readStats(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Player Name");
        String name = scanner.nextLine();
        System.out.println("Enter Points Per Game");
        String points = scanner.nextLine();
        System.out.println("Enter Assists Per Game");
        String assists = scanner.nextLine();
        System.out.println("Enter Rebounds Per Game");
        String rebounds = scanner.nextLine();
        System.out.println("Enter 3PM Per Game");
        String threes = scanner.nextLine();
        System.out.println("Enter Steals Per Game");
        String steals = scanner.nextLine();
        System.out.println("Enter Blocks Per Game");
        String blocks = scanner.nextLine();
        System.out.println("Enter TO Per Game");
        String turnovers = scanner.nextLine();
        System.out.println("Enter DD Per Game");
        String doubleDoubles = scanner.nextLine();

        Stats stats = new Stats();
        Double d = new Double ("1.4");
        stats.setPoints(d.parseDouble(points));
        stats.setAssists(d.parseDouble(assists));
        stats.setRebounds(d.parseDouble(rebounds));
        stats.setThrees(d.parseDouble(threes));
        stats.setSteals(d.parseDouble(steals));
        stats.setBlocks(d.parseDouble(blocks));
        stats.setTurnovers(d.parseDouble(turnovers));
        stats.setDD(d.parseDouble(doubleDoubles));

        return stats;
    }


    public int getGamesLeft() {
        return gamesLeft;
    }

    public void setGamesLeft(int gamesLeft) {
        this.gamesLeft = gamesLeft;
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
