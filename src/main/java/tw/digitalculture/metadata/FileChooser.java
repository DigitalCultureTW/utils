/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.digitalculture.metadata;

import java.awt.Dimension;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import static tw.digitalculture.utils.Constants.dim;
import static tw.digitalculture.utils.Constants.setFont;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class FileChooser {

    public static String chooseFile(String path, String title)
            throws Exception {
        JFileChooser fc = new JFileChooser(path);
        fc.setPreferredSize(new Dimension(800, 600));
        setFont(fc.getComponents());
        fc.setLocation((dim.width - fc.getSize().width) / 2,
                (dim.height - fc.getSize().height) / 2);
        fc.setDialogTitle(title);
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".csv");
            }

            @Override
            public String getDescription() {
                return "Data source (.csv)";
            }
        });
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.CANCEL_OPTION) {
            throw new Exception("使用者中止操作。");
        }
        return fc.getSelectedFile().getPath();
    }
}
