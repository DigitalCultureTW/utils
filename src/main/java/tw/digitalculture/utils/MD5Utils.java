/*
 * The MIT License
 *
 * Copyright 2017 Jonathan Chang, Chun-yien <ccy@musicapoetica.org>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package tw.digitalculture.utils;

import java.io.File;
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
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class MD5Utils {

    static String algorithm = "MD5";
    public static boolean debug = false;

    public static void main(String[] avg) throws IOException {
        String str = null;
        try {
            str = MD5Utils.getChecksum("D:" + File.separator + "Google Drive"
                    + File.separator + "sound_clips-2015-09-04.zip");
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
