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
import static tw.digitalculture.utils.Constants.SCREEN_SIZE;
import static tw.digitalculture.utils.Constants.setFont;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class FileChooser {

    public static String chooseFile(String path, String title,
            String[] filter, String button_text, String default_filename)
            throws Exception {
        JFileChooser fc = new JFileChooser(path);
        fc.setPreferredSize(new Dimension(800, 600));
        setFont(fc.getComponents());
        fc.setLocation((SCREEN_SIZE.width - fc.getSize().width) / 2,
                (SCREEN_SIZE.height - fc.getSize().height) / 2);
        fc.setDialogTitle(title);
        fc.setName(default_filename);
        fc.setApproveButtonText(button_text);
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(filter[0]);
            }

            @Override
            public String getDescription() {
                return filter[1];
            }
        });
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.CANCEL_OPTION) {
            throw new Exception("使用者中止操作。");
        }
        return fc.getSelectedFile().getPath();

    }

    public static String chooseFile(String path, String title,
            String[] filter, String button_text) throws Exception {
        return chooseFile(path, title, filter, button_text, "");
    }
}
