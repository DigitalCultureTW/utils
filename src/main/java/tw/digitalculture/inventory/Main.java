package tw.digitalculture.inventory;

import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import tw.digitalculture.utils.CSVWriterUtils;
import tw.digitalculture.utils.DirectoryReader;
import tw.digitalculture.utils.MD5Utils;
import tw.digitalculture.utils.ProgressBarUtil;
import static tw.digitalculture.utils.Constants.*;

/**
 * 臺中學資料庫檔名批次匯入工具
 *
 * @author Jonathan Chang
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    static ArrayList<Item> items = new ArrayList<>();
    static String root;
    static String dir;
    static double filter;
    static double minimal;

    static {
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.minimumSize", new Dimension(400, 120));
        UIManager.put("OptionPane.buttonFont", font);
        UIManager.put("TextField.font", font);
    }

    public static void main(String[] args) {
        messageWindow(APP_TITLE + ", 版本:" + APP_VERSION, 2000);
        LOGGER.log(Level.INFO, APP_TITLE + ", 版本:" + APP_VERSION);
        try {
            LOGGER.log(Level.INFO, "*** Reading Directories...");
            readDirectories();
            LOGGER.log(Level.INFO, "*** Setting Filter...");
            setFilter();
            LOGGER.log(Level.INFO, "*** Retrieving Checksum...");
            retrieveChecksum();
            LOGGER.log(Level.INFO, "*** Writing CSV...");
            outputCSV();
        } catch (Exception e) {
            String message = e.getMessage() == null ? "程式中斷。" : e.getMessage();
            LOGGER.log(Level.WARNING, message);
            messageWindow(message, 2000);
            System.exit(-1);
        }
    }

    private static void readDirectories() throws Exception {
        root = chooseDir(System.getProperty("user.home"), "請選擇根目錄");
        dir = chooseDir(root, "請選擇要匯入的資料夾");
        if (!dir.contains(root)) {
            throw new Exception("所選匯入資料夾不在根目錄下。");
        }
        int returnVal = JOptionPane.showConfirmDialog(null, "是否包含子目錄？", APP_TITLE, JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        DirectoryReader dr;
        switch (returnVal) {
            case JOptionPane.CANCEL_OPTION:
                throw new Exception("使用者中止操作。");
            case JOptionPane.YES_OPTION:
                dr = new DirectoryReader(dir, true);
                break;
            default:
                dr = new DirectoryReader(dir);
        }

        dr.files.forEach((f) -> {
            items.add(new Item(f.getParent(), f.getName(), f.length()));
        });
        LOGGER.log(Level.INFO, "{0} files have been located.", items.size());
    }

    private static void setFilter() {
        String input;
        do {
            try {
                input = (String) JOptionPane.showInputDialog(null, "請設定檔案大小下限(Mb, 0為不設限): ", APP_TITLE,
                        JOptionPane.PLAIN_MESSAGE, null, null, 0);
                minimal = Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                input = "Ex";
            }
        } while ("Ex".equals(input));
        input = (String) JOptionPane.showInputDialog(null, "請設定選取檔案類型，以分號隔開(如jpg;png): ", APP_TITLE,
                JOptionPane.PLAIN_MESSAGE);
        ArrayList<String> filetype = new ArrayList<>();
        for (String s : input.split(";")) {
            if (!s.isEmpty()) {
                filetype.add(s.trim());
            }
        }
        if (minimal > 0 || filetype.size() > 0) {
            LOGGER.log(Level.INFO, "Filter: size > {0} Mb, filetype: {1}", new Object[]{minimal, filetype.toString()});
            int c = 0;
            CheckItems:
            for (int i = items.size() - 1; i >= 0; i--) {
                if (items.get(i).getSize() < minimal * 1024 * 1024) {
                    items.remove(i);
                    c++;
                } else if (filetype.size() > 0) {
                    for (String type : filetype) {
                        if (items.get(i).getFilename().endsWith("." + type)) {
                            continue CheckItems;
                        }
                    }
                    items.remove(i);
                    c++;
                }
            }
            LOGGER.log(Level.INFO, "{0} files have been removed from the list.", c);
        }
    }

    private static void retrieveChecksum() throws Exception {
        try {
            double progressUnit = 100.0 / items.size();
            double progress = 0.0;
            ProgressBarUtil pb = new ProgressBarUtil(APP_TITLE, "處理中...");
            for (Item i : items) {
                i.setChecksumMD5(MD5Utils.getChecksum(i.getPath() + "\\" + i.getFilename()));
                progress += progressUnit;
                pb.setProgress((int) progress);
            }
            pb.close();
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new Exception("發生不明原因的錯誤。\n" + e.getMessage());
        }
    }

    private static void outputCSV() throws Exception {
        if (items.isEmpty()) {
            throw new Exception("找不到符合條件的檔案，請重新操作。");
        }
        String csvDir = chooseDir(System.getProperty("user.home"), "請選擇.csv檔案輸出位置");
        String csvFile, path;
        do {
            csvFile = JOptionPane.showInputDialog(null, "請輸入檔案名稱", "output");
            path = csvDir + "\\" + csvFile + ".csv";
        } while (dupeFile(path));
        int len = 0;
        if (new File(root).getParent() == null) {
            len = root.length() - 1;
        } else {
            len = new File(root).getParent().length();
        }
        LOGGER.log(Level.INFO, "Output path = {0}", path);
        try (FileWriter writer = new FileWriter(path)) {
            CSVWriterUtils.writeLine(writer, Arrays.asList("檔名", "原始路徑", "檔案大小", "MD5 Checksum"));
            for (Item i : items) {
                CSVWriterUtils.writeLine(writer, Arrays.asList(
                        i.getFilename(),
                        i.getPath().substring(len),
                        String.valueOf(i.getSize()),
                        i.getChecksumMD5()), ',', '"');
            }
            writer.flush();
        } catch (IOException e) {
            throw new Exception("發生不明原因的錯誤。\n" + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, path + " 輸出作業完成。", APP_TITLE, JOptionPane.INFORMATION_MESSAGE);
    }

    private static boolean dupeFile(String filename) {
        return new File(filename).isFile()
                && JOptionPane.showConfirmDialog(null, filename + " 已存在，是否覆蓋原檔案？",
                        APP_TITLE, JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION;
    }
}
