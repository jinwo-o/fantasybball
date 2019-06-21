package ca.fantasybasketball.teamweeklystats.readers;

import ca.fantasybasketball.teamweeklystats.model.Player;
import ca.fantasybasketball.teamweeklystats.model.Roster;
import ca.fantasybasketball.teamweeklystats.model.Stats;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;


import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.File;

// Read the CSV File and enter the lines into
// a roster
public class ReadCSV {

    private ReadCSV() {
    }

    public static Roster CSV_Reader(String filename) throws IOException {

        String[] line;
        Double d = 1.4;
        Roster roster = new Roster();

        final CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
        final CSVReader reader = new CSVReaderBuilder(new FileReader(filename)).withSkipLines(1).withCSVParser(parser).build();
        while ((line = reader.readNext()) != null) {
            Player player = new Player();
            Stats stats = new Stats();
            player.setName(line[0]);
            player.setTeam(line[1]);
            player.setPosition(line[2]);
            stats.setSeason(Integer.parseInt(line[3]));
            stats.setGamesLeft(Integer.parseInt(line[4]));
            stats.setMinutes(d.parseDouble(line[5]));
            stats.setPoints(d.parseDouble(line[6]));
            stats.setThrees(d.parseDouble(line[7]));
            stats.setRebounds(d.parseDouble(line[8]));
            stats.setAssists(d.parseDouble(line[9]));
            stats.setSteals(d.parseDouble(line[10])










            );
            stats.setBlocks(d.parseDouble(line[11]));
            stats.setTurnovers(d.parseDouble(line[12]));
            stats.setDD(d.parseDouble(line[13]));

            player.setStats(stats);
            roster.getPlayers().add(player);
        }
        return roster;
    }

}
