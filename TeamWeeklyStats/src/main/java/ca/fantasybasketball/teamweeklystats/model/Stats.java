package ca.fantasybasketball.teamweeklystats.model;

// these stats describe a players statistics
// through the players 2019 season

public class Stats {
    //    private int id;
    private int season;
    private int gamesLeft;
    public double minutes;
    public double points;
    public double threes;
    public double rebounds;
    public double assists;
    public double steals;
    public double blocks;
    public double turnovers;
    public double DD;

    // add toString

    // add readStats method

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getGamesLeft() {
        return gamesLeft;
    }

    public void setGamesLeft(int gamesLeft) {
        this.gamesLeft = gamesLeft;
    }

    public double getMinutes() {
        return minutes;
    }

    public void setMinutes(double minutes) {
        this.minutes = minutes;
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
