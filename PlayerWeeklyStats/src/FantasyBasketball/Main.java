//import java.io.FileNotFoundException;Exception;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
//
public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(new File("test3.csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("id");
        sb.append(',');
        sb.append("Name");
        sb.append('\n');

        sb.append("3/8\"");
        sb.append(',');
        sb.append("Sanded Plywood");
        sb.append('\n');

        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }
}
//        String csvFile = "/Users/JXH3JJU/fantasy/FantasyCSV.csv";
//        BufferedReader br = null;
//        String line = "";
//        String cvsSplitBy = ",";
//
//        try {
//
//            br = new BufferedReader(new FileReader(csvFile));
//            while ((line = br.readLine()) != null) {
//
//                // use comma as separator
//                String[] player = line.split(cvsSplitBy);
//
//                System.out.println("Player [name= " + player[0] + " , PPG=" + player[1] + "]");
//
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter Player Name");
//        String name = scanner.nextLine();
//        System.out.println(name);

//        Player Klay =  new Player();
//        Klay.readStats();
//        Klay.setName("Klay Thompson");
//        Stats stats = new Stats();
//        stats.setAssists(1.8);
//        stats.setBlocks(0.7);
//        stats.setDD(0.0);
//        stats.setPoints(22.9);
//        stats.setThrees(2.8);
//        stats.setRebounds(3.7);
//        stats.setSteals(0.9);
//        Klay.setStats(stats);
//        Klay.setGamesLeft(3);
//        System.out.println(Klay.getStats());

//        Roster roster = new Roster();
//        roster.getTeam();
//        Player player = new Player();
//        player.setName("Klay Thompson");
//        Stats stats = new Stats();
//        player.setStats(stats.readStats());
//        PlayerWeeklyTotal total = new PlayerWeeklyTotal();
//
//        playerWeekly = total.calculateWeeklyTotal(player);
//        System.out.println(stats.getAssists());
//        System.out.println(total.calculateWeeklyTotal(player));

