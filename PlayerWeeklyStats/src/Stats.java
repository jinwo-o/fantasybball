import java.util.Scanner;

public class Stats {

    private int gamesLeft;
    private double points;
    private double threes;
    private double rebounds;
    private double assists;
    private double steals;
    private double blocks;
    private double turnovers;
    private double DD;

    public Stats readStats(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("How many games is he playing this week");
        String gamesLeft = scanner.nextLine();
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
        stats.setGamesLeft(Integer.parseInt(gamesLeft));
        stats.setAssists(d.parseDouble(assists));
        stats.setRebounds(d.parseDouble(rebounds));
        stats.setThrees(d.parseDouble(threes));
        stats.setSteals(d.parseDouble(steals));
        stats.setBlocks(d.parseDouble(blocks));
        stats.setTurnovers(d.parseDouble(turnovers));
        stats.setDD(d.parseDouble(doubleDoubles));

        return stats;
    }

    public double getGamesLeft() {
        return gamesLeft;
    }

    public void setGamesLeft(int gamesLeft) {
        this.gamesLeft = gamesLeft;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getThrees() {
        return threes;
    }

    public void setThrees(double threes) {
        this.threes = threes;
    }

    public double getRebounds() {
        return rebounds;
    }

    public void setRebounds(double rebounds) {
        this.rebounds = rebounds;
    }

    public double getAssists() {
        return assists;
    }

    public void setAssists(double assists) {
        this.assists = assists;
    }

    public double getSteals() {
        return steals;
    }

    public void setSteals(double steals) {
        this.steals = steals;
    }

    public double getBlocks() {
        return blocks;
    }

    public void setBlocks(double blocks) {
        this.blocks = blocks;
    }

    public double getTurnovers() {
        return turnovers;
    }

    public void setTurnovers(double turnovers) {
        this.turnovers = turnovers;
    }

    public double getDD() {
        return DD;
    }

    public void setDD(double DD) {
        this.DD = DD;
    }

}

