/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.digitalculture.metadata;

import java.io.File;
import java.util.List;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
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
public class CorrectExifTest {

    String path = System.getProperty("user.dir") + "\\src\\main\\resources\\photo.JPG";

    public CorrectExifTest() {
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
     * Test of main method, of class CorrectExif.
     */
//    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        CorrectExif.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOrientation method, of class CorrectExif.
     */
    @Test
    public void testGetOrientation() throws Exception {
        System.out.println("getOrientation");
        CorrectExif instance = new CorrectExif(path);
        int expResult = 8;
        int result = instance.getOrientation();
        assertEquals(expResult, result);
    }

    /**
     * Test of correctOrientation method, of class CorrectExif.
     */
    @Test
    public void testCorrectOrientation() throws Exception {
        System.out.println("correctOrientation");
        short orientation = 1;
        File src_file = new File(path);
        CorrectExif instance = new CorrectExif(src_file, src_file.getParent());
        String out_path = instance.correctOrientation(orientation);
        int new_orientation = new CorrectExif(out_path).getOrientation();
        assertEquals(orientation, new_orientation);
    }

    /**
     * Test of getAllFields method, of class CorrectExif.
     */
    @Test
    public void testGetAllFields() throws Exception {
        System.out.println("getAllFields");
        CorrectExif instance = new CorrectExif(path);
        String expResult = "NIKON CORPORATION";
        List result = instance.getAllFields();
        assertTrue(result.get(0).toString().contains(expResult));
    }

}
