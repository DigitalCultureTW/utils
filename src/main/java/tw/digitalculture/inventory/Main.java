package tw.digitalculture.inventory;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import tw.digitalculture.utils.CSVUtils;
import tw.digitalculture.utils.DirectoryReader;
import tw.digitalculture.utils.MD5Utils;
import tw.digitalculture.utils.ProgressBarUtil;
import static tw.digitalculture.utils.Constants.*;

/**
 * 臺中學資料庫檔名匯入工具
 *
 * @author Jonathan Chang
 */
public class Main {

    static ArrayList<Item> items = new ArrayList<>();
    static String root;
    static String dir;
    static double filter;
    static double minimal;

    static boolean debug = false;

    public static void main(String[] args) {
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.buttonFont", font);
        UIManager.put("OptionPane.minimumSize", new Dimension(400, 120));
        try {
            System.out.println("* Reading Directories...");
            readDirectories();
            System.out.println("* Setting Filter...");
            setFilter();
            System.out.println("* Retrieving Checksum...");
            retrieveChecksum();
            System.out.println("* Writing CSV...");
            outputCSV();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "程式中斷。", APP_TITLE, JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    private static String chooseDir(String path, String title) throws Exception {
        JFileChooser fc = new JFileChooser(path);
        fc.setPreferredSize(new Dimension(800, 600));
        setFont(fc.getComponents());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        fc.setLocation(dim.width / 2 - fc.getSize().width / 2, dim.height / 2 - fc.getSize().height / 2);
        fc.setDialogTitle(title);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.CANCEL_OPTION) {
            throw new Exception("使用者中止操作。");
        }
        if (debug) {
            System.out.println(fc.getSelectedFile().getPath());
        }
        return fc.getSelectedFile().getPath();
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
    }

    private static void setFilter() {//throws Exception {
        String input;
        do {
            try {
                input = (String) JOptionPane.showInputDialog(null, "請設定檔案大小下限(Mb): ", APP_TITLE,
                        JOptionPane.QUESTION_MESSAGE, null, null, 0);
                minimal = Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                input = "Ex";
            }
        } while ("Ex".equals(input));
        int c = 0;
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i).getSize() < minimal * 1024 * 1024) {
                items.remove(i);
                c++;
            }
        }
        System.out.println(c + " files have been removed from the list.");
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
        System.out.println("Output path = " + path);
        try (FileWriter writer = new FileWriter(path)) {
            CSVUtils.writeLine(writer, Arrays.asList("檔名", "原始路徑", "檔案大小", "MD5 Checksum"));
            for (Item i : items) {
                CSVUtils.writeLine(writer, Arrays.asList(
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
