package tw.digitalculture.inventory;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
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
import tw.digitalculture.utils.DirReader;
import tw.digitalculture.utils.MD5Utils;

/**
 * 臺中學資料庫檔名匯入工具
 *
 * @author Jonathan Chang
 */
public class Main {

    static final String APP_TITLE = "臺中學資料庫檔名批次匯入工具";
    static ArrayList<Item> items = new ArrayList<>();
    static String root;
    static String dir;
    static double filter;

    static boolean debug = true;
    static Font font = new Font(Font.SERIF, Font.PLAIN, 20);

    public static void main(String[] args) {
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.buttonFont", font);
        UIManager.put("OptionPane.minimumSize", new Dimension(400, 120));
        try {
            readDirectories();
            setFilter();
            retrieveChecksum();
            outputCSV();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), APP_TITLE, JOptionPane.PLAIN_MESSAGE);
            System.exit(-1);
        }
    }

    public static void setFont(Component[] comp) {
        for (Component c : comp) {
            if (c instanceof Container) {
                setFont(((Container) c).getComponents());
            }
            c.setFont(font);
        }
    }

    private static String chooseDir(String path, String title) throws Exception {
        JFileChooser fc = new JFileChooser(path);
        fc.setPreferredSize(new Dimension(800, 600));
        setFont(fc.getComponents());
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
        DirReader dr;
        switch (returnVal) {
            case JOptionPane.CANCEL_OPTION:
                throw new Exception("使用者中止操作。");
            case JOptionPane.YES_OPTION:
                dr = new DirReader(dir, true);
                break;
            default:
                dr = new DirReader(dir);
        }
        dr.files.forEach((f) -> {
            items.add(new Item(f.getParent(), f.getName(), f.length()));
        });
    }

    private static void setFilter() throws Exception {
        /*
        File r = new File(root);
        for (Item i : items) {
            System.out.println(i.toString().substring(r.getParent().length()));
            System.out.println("[" + MD5Utils.getChecksum(i.getPath() + "\\" + i.getFilename()) + "]");
        }*/
    }

    private static void retrieveChecksum() throws Exception {
        try {
            for (Item i : items) {
                i.setChecksumMD5(MD5Utils.getChecksum(i.getPath() + "\\" + i.getFilename()));
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new Exception("發生不明原因的錯誤。\n" + e.getMessage());
        }
    }

    private static void outputCSV() throws Exception {
        String csvDir = chooseDir(System.getProperty("user.home"), "請選擇.csv檔案輸出位置");
        String csvFile;
        do {
            csvFile = JOptionPane.showInputDialog(null, "請輸入檔案名稱", "output.csv");
        } while (isFile(csvDir + "\\" + csvFile));
        System.out.println("Output path = " + csvDir + "\\" + csvFile);
        File r = new File(root);
        try (FileWriter writer = new FileWriter(csvDir + "\\" + csvFile)) {
            CSVUtils.writeLine(writer, Arrays.asList("檔名", "原始路徑", "檔案大小", "MD5 Checksum"));
            for (Item i : items) {
                CSVUtils.writeLine(writer, Arrays.asList(
                        i.getFilename(),
                        i.getPath().substring(r.getParent().length()),
                        String.valueOf(i.getSize()),
                        i.getChecksumMD5()), ',', '"');
            }
            writer.flush();
        } catch (IOException e) {
            throw new Exception("發生不明原因的錯誤。\n" + e.getMessage());
        }

    }

    private static boolean isFile(String filename) {
        if (new File(filename).isFile()) {
            JOptionPane.showMessageDialog(null, "該檔名已存在，請重新指定檔名。", APP_TITLE, JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }

}
