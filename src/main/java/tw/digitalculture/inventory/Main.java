package tw.digitalculture.inventory;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import tw.digitalculture.utils.DirReader;

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
            JOptionPane.showMessageDialog(null, e.getMessage(), "臺中學資料庫檔名匯入工具", JOptionPane.PLAIN_MESSAGE);
            System.exit(-1);
        }
    }

    public static void setFont(Component[] comp) {
        for (int x = 0; x < comp.length; x++) {
            if (comp[x] instanceof Container) {
                setFont(((Container) comp[x]).getComponents());
            }
            try {
                comp[x].setFont(font);
            } catch (Exception e) {
            }//do nothing
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
        int returnVal = JOptionPane.showConfirmDialog(null, "是否包含子目錄？", "臺中學資料庫檔名匯入工具", JOptionPane.YES_NO_CANCEL_OPTION,
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
        File r = new File(root);
        items.forEach((i) -> {
            System.out.println(i.toString().substring(r.getParent().length()));
        });
    }

    private static void retrieveChecksum() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void outputCSV() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
