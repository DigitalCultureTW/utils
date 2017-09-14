/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.digitalculture.test;

import java.io.File;

/**
 *
 * @author Jonathan Chang
 */
public class ParentTest {

    public static void main(String[] args) {
        File f = new File("C:\\");
        System.out.println(f.getParent());
    }
}
