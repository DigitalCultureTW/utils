package tw.digitalculture.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

/**
 *
 * @author Jonathan Chang
 */
public class Constants {

    public static final String APP_TITLE = "臺中學資料庫檔名批次匯入工具";
    public static final String APP_VERSION = "1.03";
    public static final Font SYS_FONT = (OSDetector.isMac())?
            new Font(Font.MONOSPACED, Font.PLAIN, 14)
            :new Font(Font.MONOSPACED, Font.BOLD, 20);
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public static void setFont(Component[] comp) {
        for (Component c : comp) {
            if (c instanceof Container) {
                setFont(((Container) c).getComponents());
            }
            c.setFont(SYS_FONT);
        }
    }

    public static void messageWindow(String message, int time) {
        messageWindow(message, () -> {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void messageWindow(String message, Runnable f) {
        JWindow window = new JWindow();
        window.getContentPane().add(new JLabel(message, SwingConstants.CENTER));
        window.setSize(500, 150);
        setFont(window.getComponents());
        window.setLocation((SCREEN_SIZE.width - window.getSize().width) / 2, (SCREEN_SIZE.height - window.getSize().height) / 2);
        window.setVisible(true);
        try {
            f.run();
        } finally {
            window.setVisible(false);
            window.dispose();
        }
    }

    public static String chooseDir(String path, String title) throws Exception {
        if (OSDetector.isMac()) {
            System.out.println("MacOS detected.");
            return chooseDirFileDialog(path, title);
        }
        JFileChooser fc = new JFileChooser(path);
        fc.setPreferredSize(new Dimension(800, 600));
        setFont(fc.getComponents());
        fc.setLocation((SCREEN_SIZE.width - fc.getSize().width) / 2, (SCREEN_SIZE.height - fc.getSize().height) / 2);
        fc.setDialogTitle(title);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showDialog(null, "Select");
        if (returnVal == JFileChooser.CANCEL_OPTION) {
            throw new Exception("使用者中止操作。");
        }
        return fc.getSelectedFile().getPath();
    }

    public static String chooseDirFileDialog(String path, String title) throws Exception {
        System.setProperty("apple.awt.fileDialogForDirectories", "true");
        FileDialog fd = new FileDialog((Frame) null, title, FileDialog.LOAD);
        fd.setPreferredSize(new Dimension(800, 600));
        setFont(fd.getComponents());
        fd.setLocation((SCREEN_SIZE.width - fd.getSize().width) / 2, (SCREEN_SIZE.height - fd.getSize().height) / 2);
        fd.setVisible(true);
        if (fd.getFile() == null) {
            throw new Exception("使用者中止操作。");
        }
        return fd.getFiles()[0].getPath();
    }
}
