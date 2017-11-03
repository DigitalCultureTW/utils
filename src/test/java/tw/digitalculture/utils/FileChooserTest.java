/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.digitalculture.utils;

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
public class FileChooserTest {

    public FileChooserTest() {
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
     * Test of chooseFile method, of class FileChooser.
     */
//    @Test
    public void testChooseFile_5args() throws Exception {
        System.out.println("chooseFile");
        String path = System.getProperty("user.home");
        String title = "chooseFile (5args)";
        String[] filter = new String[]{"txt", "Text File"};
        String button_text = "Ok5";
        String default_filename = "";
        String expResult = "txt";
        String result = FileChooser.chooseFile(path, title, filter, button_text, default_filename);
        assertTrue(result.endsWith(expResult));
    }

    /**
     * Test of chooseFile method, of class FileChooser.
     */
//    @Test
    public void testChooseFile_4args() throws Exception {
        System.out.println("chooseFile");
        String path = System.getProperty("user.home");
        String title = "chooseFile (4args)";
        String[] filter = new String[]{"txt", "Text File"};
        String button_text = "Ok4";
        String expResult = "txt";
        String result = FileChooser.chooseFile(path, title, filter, button_text);
        assertTrue(result.endsWith(expResult));
    }

}
