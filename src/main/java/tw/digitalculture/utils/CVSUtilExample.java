package tw.digitalculture.utils;

import java.io.FileWriter;
import java.util.Arrays;

public class CVSUtilExample {

    public static void main(String[] args) throws Exception {

        String csvFile = System.getProperty("user.home") + "/abc.csv";
        try (FileWriter writer = new FileWriter(csvFile)) {
            CSVUtils.writeLine(writer, Arrays.asList("a", "b", "c", "d"));

            //custom separator + quote
            CSVUtils.writeLine(writer, Arrays.asList("aaa", "bb,b", "cc,c"), ',', '"');

            //custom separator + quote
            CSVUtils.writeLine(writer, Arrays.asList("aaa", "bbb", "cc,c"), '|', '\'');

            //double-quotes
            CSVUtils.writeLine(writer, Arrays.asList("aaa", "bbb", "cc\"c"));

            writer.flush();
        }

    }

}