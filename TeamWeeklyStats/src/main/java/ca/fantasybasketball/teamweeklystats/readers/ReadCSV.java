package ca.fantasybasketball.teamweeklystats.readers;

import ca.fantasybasketball.teamweeklystats.player.Player;
import ca.fantasybasketball.teamweeklystats.roster.Roster;
import ca.fantasybasketball.teamweeklystats.stats.Stats;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;

// Read the CSV File and enter the lines into
// a roster
public class ReadCSV {

    private static final String csvFile = "/Users/JXH3JJU/fantasy/FantasyCSV.csv";

    private ReadCSV() {
    }

    public static Roster CSV_Reader() throws IOException {
        String[] line;
        Double d = new Double("1.4");
        Roster roster = new Roster();

        CSVReader reader = new CSVReader(new FileReader("/Users/JXH3JJU/fantasy/FantasyCSV.csv"), ',', '\'', 1);

        while ((line = reader.readNext()) != null) {
            Player player = new Player();
            Stats stats = new Stats();

            player.setName(line[0]);
            player.setTeam(line[1]);
            player.setPosition(line[2]);
            stats.setSeason(Integer.parseInt(line[3]));
            stats.setGamesLeft(Integer.parseInt(line[4]));
            stats.setPoints(d.parseDouble(line[5]));
            stats.setThrees(d.parseDouble(line[6]));
            stats.setRebounds(d.parseDouble(line[7]));
            stats.setAssists(d.parseDouble(line[8]));
            stats.setSteals(d.parseDouble(line[9]));
            stats.setBlocks(d.parseDouble(line[10]));
            stats.setTurnovers(d.parseDouble(line[11]));
            stats.setDD(d.parseDouble(line[12]));

            player.setStats(stats);
            roster.roster.add(player);
        }
        return roster;
    }

}
