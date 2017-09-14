package tw.digitalculture.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import static tw.digitalculture.utils.Constants.font;

/**
 *
 * @author Jonathan Chang
 */
public class Constants {

    public static final String APP_TITLE = "臺中學資料庫檔名批次匯入工具";
    public static final String APP_VERSION = "1.0";
    public static Font font = new Font(Font.MONOSPACED, Font.BOLD, 20);
    public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    public static void setFont(Component[] comp) {
        for (Component c : comp) {
            if (c instanceof Container) {
                setFont(((Container) c).getComponents());
            }
            c.setFont(font);
        }
    }

    public static void messageWindow(String message, int time) {
        JWindow window = new JWindow();
        window.getContentPane().add(new JLabel(message, SwingConstants.CENTER));
        window.setSize(500, 150);
        setFont(window.getComponents());
        window.setLocation((dim.width - window.getSize().width) / 2, (dim.height - window.getSize().height) / 2);
        window.setVisible(true);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        window.dispose();
    }

    public static String chooseDir(String path, String title) throws Exception {
        JFileChooser fc = new JFileChooser(path);
        fc.setPreferredSize(new Dimension(800, 600));
        setFont(fc.getComponents());
        fc.setLocation((dim.width - fc.getSize().width) / 2, (dim.height - fc.getSize().height) / 2);
        fc.setDialogTitle(title);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.CANCEL_OPTION) {
            throw new Exception("使用者中止操作。");
        }
        return fc.getSelectedFile().getPath();
    }
}
