package tw.digitalculture.metadata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class TemplateApply {

    String source_file;
    String dest_file;
    static String template = "本照片為2016年「彩筆畫媽祖」水彩徵件比賽%s組%s之作品：%s。"
            + "\n作者為%s，作品說明：「%s」";

    public static void main(String[] args) throws Exception {
        TemplateApply ta = new TemplateApply();
        ta.readFile((List<List<String>> table) -> {
            try {
                List<String> result = ta.applyTemplate(table);
                ta.writeFile(result);
            } catch (Exception ex) {
                Logger.getLogger(TemplateApply.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void readFile(Consumer<List<List<String>>> cb) throws Exception {
        source_file = FileChooser.chooseFile(
                System.getProperty("user.home"), "請選擇資料來源");
        CSVReaderUtils.readFile(source_file, cb);
    }

    private List<String> applyTemplate(List<List<String>> table)
            throws FileNotFoundException, IOException {
        String path = System.getProperty("user.dir")
                + "\\src\\main\\resources\\template";
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
        table.forEach((List<String> t) -> {
            String r = String.format(template, t.toArray());
            result.add(r);
        });
        return result;
    }

    private void writeFile(List<String> result) throws IOException, Exception {
        try (FileWriter fw = new FileWriter(FileChooser.chooseFile(
                System.getProperty("user.home"), "請選擇寫入檔名"));
                BufferedWriter writer = new BufferedWriter(fw)) {
            result.forEach((String t) -> {
                try {
                    writer.append("\"" + t + "\"\n");
                } catch (IOException ex) {
                    Logger.getLogger(TemplateApply.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            writer.flush();
        }
    }

}
