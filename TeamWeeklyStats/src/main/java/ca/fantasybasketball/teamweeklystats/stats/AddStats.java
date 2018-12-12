package ca.fantasybasketball.teamweeklystats.stats;

public class AddStats {

    private AddStats()
    {
    }

    // add total games played
    public static Stats total(Stats total, Stats current){
        total.setMinutes(total.getMinutes() + current.getMinutes());
        total.setPoints(total.getPoints() + current.getPoints());
        total.setThrees(total.getThrees() + current.getThrees());
        total.setRebounds(total.getRebounds() + current.getRebounds());
        total.setAssists(total.getAssists() + current.getAssists());
        total.setSteals(total.getSteals() + current.getSteals());
        total.setBlocks(total.getBlocks() + current.getBlocks());
        total.setTurnovers(total.getTurnovers() + current.getTurnovers());
        total.setDD(total.getDD() + current.getDD());

        return total;
    }
}
