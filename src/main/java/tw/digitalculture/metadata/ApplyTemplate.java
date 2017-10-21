package tw.digitalculture.metadata;

import tw.digitalculture.utils.CSVReaderUtils;
import tw.digitalculture.utils.FileChooser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class ApplyTemplate {

    String source_file;
    String dest_file;
    static String template = "本照片為2016年「彩筆畫媽祖」水彩徵件比賽%s組%s之作品：%s。"
            + "\n作者為%s，作品說明：「%s」";

    public static void main(String[] args) throws Exception {
        ApplyTemplate ta = new ApplyTemplate();
        ta.readFile((List<List<String>> table) -> {
            try {
                List<String> result = ta.applyTemplate(table);
                ta.writeFile(result);
            } catch (Exception ex) {
                Logger.getLogger(ApplyTemplate.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void readFile(Consumer<List<List<String>>> cb) throws Exception {
        source_file = FileChooser.chooseFile(System.getProperty("user.dir"),
                "請選擇資料來源", new String[]{"csv", "Data source (.csv)"}, "Open");
        CSVReaderUtils.readFile(source_file, cb);
    }

    private List<String> applyTemplate(List<List<String>> table)
            throws FileNotFoundException, IOException, Exception {
        File f = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\template");
        String path = (f.exists()) ? f.getPath() : FileChooser.chooseFile(
                System.getProperty("user.dir"), "請選擇樣版檔案",
                new String[]{"", "樣版(String format)"}, "Open");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(path), "UTF-8"))) {
            template = "";
            String line;
            do {
                line = reader.readLine();
                if (line != null) {

                    template += (template.isEmpty()) ? line : "\n" + line;
                }
            } while (line != null);
        }
        System.out.println(template);
        List<String> result = new ArrayList<>();
        table.forEach((List<String> row) -> {
            result.add(String.format(template, row.toArray()));
        });
        return result;
    }

    private void writeFile(List<String> result) throws IOException, Exception {
        String path = FileChooser.chooseFile(System.getProperty("user.home"),
                "請選擇寫入檔名", new String[]{"csv", "csv檔案"}, "Save");
        if (!path.toLowerCase().endsWith(".csv")) {
            path += ".csv";
        }
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path)))) {
            result.forEach((String t) -> {
                System.out.println(t + "\n");
                try {
                    writer.append("\"" + t + "\"\n");
                } catch (IOException ex) {
                    Logger.getLogger(ApplyTemplate.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            writer.flush();
        }
    }

}
