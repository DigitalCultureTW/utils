/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.digitalculture.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import static tw.digitalculture.utils.Constants.font;

/**
 *
 * @author Jonathan Chang
 */
public class Constants {

    public static final String APP_TITLE = "臺中學資料庫檔名批次匯入工具";
    public static Font font = new Font(Font.MONOSPACED, Font.PLAIN, 20);

    public static void setFont(Component[] comp) {
        for (Component c : comp) {
            if (c instanceof Container) {
                setFont(((Container) c).getComponents());
            }
            c.setFont(font);
        }
    }
}
