/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.digitalculture.utils;

import java.awt.Component;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class ConstantsTest {

    public ConstantsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of messageWindow method, of class Constants.
     */
//    @Test
    public void testMessageWindow_String_int() {
        System.out.println("messageWindow");
        String message = "messageWindow";
        int time = 3000;
        Constants.messageWindow(message, time);
    }

    /**
     * Test of messageWindow method, of class Constants.
     */
//    @Test
    public void testMessageWindow_String_Runnable() {
        System.out.println("messageWindow");
        String message = "messageWindow";
        Runnable f = () -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ConstantsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        Constants.messageWindow(message, f);
    }

    /**
     * Test of chooseDir method, of class Constants.
     */
//    @Test
    public void testChooseDir() throws Exception {
        System.out.println("chooseDirFileDialog");
        String path = System.getProperty("user.home");
        String title = "Get Java File";
        String result = Constants.chooseDir(path, title);
        assertTrue(new File(result).isDirectory());
    }

    /**
     * Test of chooseDirFileDialog method, of class Constants.
     */
//    @Test
    public void testChooseDirFileDialog() throws Exception {
        System.out.println("chooseDirFileDialog");
        String path = System.getProperty("user.home");
        String title = "Get Java File";
        String expResult = "java";
        String result = Constants.chooseDirFileDialog(path, title);
        assertTrue(result.endsWith(expResult));
    }

}
