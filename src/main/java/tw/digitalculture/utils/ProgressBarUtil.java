package tw.digitalculture.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import static tw.digitalculture.utils.Constants.*;

/**
 *
 * @author Jonathan
 */
public class ProgressBarUtil extends JDialog {

    private JProgressBar progressBar;

    public static void main(String[] args) {
        ProgressBarUtil pbu = new ProgressBarUtil("Title", "Description");
        setFont(pbu.getComponents());
        pbu.setVisible(true);
    }

    public ProgressBarUtil(String title, String description) {
        super((JDialog) null, title);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(500, 145);
        this.setLocation(dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2);
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        Border border = BorderFactory.createTitledBorder(null, description,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, font);
        progressBar.setBorder(border);
        this.add(progressBar);
        this.setVisible(true);
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

    public void setProgress(int p) {
        progressBar.setValue(p);
    }

    public void close() {
        this.setVisible(false);
        this.dispose();
    }
}
