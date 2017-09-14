/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.digitalculture.temp;

/**
 *
 * @author Jonathan
 */
public class SplitTest {

    public static void main(String[] args) {
        String input = "";
        String[] filetype = input.split(";");
        System.out.println(filetype.length);
        System.out.println(filetype[0]);
        System.out.println(filetype[0].isEmpty());
    }
}
