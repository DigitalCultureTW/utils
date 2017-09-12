/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.digitalculture.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Jonathan
 */
public class MD5Utils {

    static String algorithm = "MD5";
    public static boolean debug = false;

    public static void main(String[] avg) throws IOException {
        String str = null;
        try {
            str = MD5Utils.getChecksum("D:\\Google Drive\\sound_clips-2015-09-04.zip");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("MD5 Checksum: " + str);
    }

    private static byte[] encryptMD5(File f) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] buffer = new byte[1024 * 1024];
        try (InputStream is = Files.newInputStream(f.toPath());
                DigestInputStream dis = new DigestInputStream(is, md)) {
            int numRead;
            do {
                numRead = dis.read(buffer);
            } while (numRead != -1);
        }
        if (debug) {
            System.out.println(NumberFormat.getNumberInstance(Locale.US).format(f.length()) + " bytes been processed.");
        }
        return md.digest();
    }

    public static String getChecksum(String path)
            throws IOException, NoSuchAlgorithmException {
        byte[] data = MD5Utils.encryptMD5(new File(path));
        String result = "";
        for (int i = 0; i < data.length; i++) {
            result += Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1).toUpperCase();
        }
        return result;
    }

}
