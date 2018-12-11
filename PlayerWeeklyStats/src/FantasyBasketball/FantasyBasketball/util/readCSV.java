//import com.opencsv.CSVReader;
//import java.io.IOException;
//import java.io.Reader;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//public class readCSV {
//    private static final String SAMPLE_CSV_FILE_PATH = "./users.csv";
//
//    public static void main(String[] args) throws IOException {
//        try (
//                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
//                CSVReader csvReader = new CSVReader(reader);
//        ) {
//            // Reading Records One by One in a String array
//            String[] nextRecord;
//            while ((nextRecord = csvReader.readNext()) != null) {
//                System.out.println("Name : " + nextRecord[0]);
//                System.out.println("Email : " + nextRecord[1]);
//                System.out.println("Phone : " + nextRecord[2]);
//                System.out.println("Country : " + nextRecord[3]);
//                System.out.println("==========================");
//            }
//        }
//    }
//}

//    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
//        int i;
//        //write file to server
//        while ((i = is.read()) != -1) {
//            stream.write(i);
//        }
//        stream.flush();
//    }
//    String[] line;
//    List<IEntry> listIEntry = new ArrayList<IEntry>();
//    FileReader fileReader = new FileReader(serverFile);
//    CSVReader reader = new CSVReader(fileReader, ',', '\"', '\'', 1); {
//        while ((line = reader.readNext()) != null) {
//            IEntry inputEntry = new DefaultIEntry();
//            inputEntry.put(CategoryAnalyticsConstants.SEARCH_QUERY, line[0]);
//            inputEntry.put(CategoryAnalyticsConstants.CATEGORY_LEAF, line[1]);
//            inputEntry.put(CategoryAnalyticsConstants.SCORE, line[2]);
//            inputEntry.put(CategoryAnalyticsConstants.LANGUAGE, line[3]);
//            listIEntry.add(inputEntry);
//        }
//    }
//        return listIEntry;

