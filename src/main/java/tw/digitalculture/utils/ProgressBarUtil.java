/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.digitalculture.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Jonathan
 */
public class ProgressBarUtil extends JFrame {

    private JProgressBar progressBar;

    public static void main(String[] args) {
        ProgressBarUtil pbu = new ProgressBarUtil("Title", "Description");
        setFont(pbu.getComponents());
        pbu.setVisible(true);
    }

    public ProgressBarUtil(String title, String description) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(500, 150);
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        Border border = BorderFactory.createTitledBorder(null, description,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, font);
        progressBar.setBorder(border);
        this.add(progressBar);
        this.setVisible(true);
    }
    static Font font = new Font(Font.SERIF, Font.PLAIN, 20);

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
