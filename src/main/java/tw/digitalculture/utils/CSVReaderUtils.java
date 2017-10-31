package tw.digitalculture.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class CSVReaderUtils {

    public static void main(String[] args) throws IOException {
        String path = System.getProperty("user.dir")
                + File.separator + "src" + File.separator + "main"
                + File.separator + "resources" + File.separator + "demo.csv";
        System.out.println("path = " + path);
        readFile(path, (List<List<String>> table) -> {
            System.out.println("table row = " + table.size());
            table.forEach((List<String> row) -> {
                System.out.println(row);
            });
        });
    }

    private static final char DEFAULT_SEPARATOR = ',';

    public static void readFile(String path, Consumer<List<List<String>>> cb)
            throws FileNotFoundException, IOException {
        List<List<String>> table = new ArrayList<>();
        List<String> values;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(path)))) {
            do {
                values = CSVReaderUtils.readLine(reader);
//                //custom separator + quote
//                CSVReaderUtils.readLine(reader, Arrays.asList("aaa", "bb,b", "cc,c"), ',', '"');
//                //custom separator + quote
//                CSVReaderUtils.readLine(reader, Arrays.asList("aaa", "bbb", "cc,c"), '|', '\'');
//                //double-quotes
//                CSVReaderUtils.readLine(reader, Arrays.asList("aaa", "bbb", "cc\"c"));
                if (values != null) {
                    table.add(values);
                }
            } while (values != null);
        }
        cb.accept(table);
    }

    public static List<String> readLine(BufferedReader reader) throws IOException {
        return readLine(reader, DEFAULT_SEPARATOR, ' ');
    }

    public static List<String> readLine(BufferedReader w, char separators) throws IOException {
        return readLine(w, separators, ' ');
    }

    public static List<String> readLine(BufferedReader reader, char separators, char customQuote)
            throws IOException {
        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }
        String line = reader.readLine();
        return (line == null) ? null : Arrays.asList(line.replaceAll("\"", "").split(String.valueOf(separators)));
    }

}
