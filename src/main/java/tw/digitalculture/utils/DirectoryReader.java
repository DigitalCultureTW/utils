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
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class DirectoryReader {

    private String path;
    private boolean subDir = false;
    public List<File> files;

//
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        DirectoryReader dr = new DirectoryReader(System.getProperty("user.home"), true);
        for (File f : dr.files) {
            System.out.print(f.getPath());
            System.out.println(f.getParent() + " " + f.getName());
            System.out.printf(" = %.1f kbytes.\n", f.length() / 1024.0);
//            System.out.printf("= %.1f kbytes. [%s]\n", f.length() / 1024.0, MD5Utils.getChecksum(f.getPath()));
        }
        System.out.println("Total: " + dr.files.size() + " files.");
    }
//

    /**
     * Directory Reader.
     *
     * @param path Specify the folder to be read.
     * @param subDir Specify if sub-directories included.
     */
    public DirectoryReader(String path, boolean subDir) {
        System.out.println("Reading Directory: " + path);
        System.out.println("Including Sub-Directories: " + subDir);
        this.path = path;
        this.subDir = subDir;
        refresh();
    }

    /**
     * Directory Reader, sub-directories not included.
     *
     * @param path Specify the folder to be read.
     */
    public DirectoryReader(String path) {
        this(path, false);
    }

    public final void refresh() {
        //System.out.println("Checkpoint: refresh()");
        files = new ArrayList<>();
        getDir(this.path);
    }

    private void getDir(String path) {
        File obj = new File(path);
        if (obj.exists() && obj.listFiles() != null) {
            for (File f : obj.listFiles()) {
                if (f.isFile() && !f.isHidden()) {
                    files.add(f);
                } else if (f.isDirectory() && subDir) {
                    getDir(f.getPath());
                }
            }
        }
//        System.out.println("Checkpoint: getDir(), path = " + path);
    }

    /**
     * Get the value of subDir
     *
     * @return the value of subDir
     */
    public boolean isSubDir() {
        return subDir;
    }

    /**
     * Set the value of subDir
     *
     * @param subDir new value of subDir
     */
    public void setSubDir(boolean subDir) {
        this.subDir = subDir;
    }

    /**
     * Get the value of path
     *
     * @return the value of path
     */
    public String getPath() {
        return path;
    }

    /**
     * Set the value of path
     *
     * @param path new value of path
     */
    public void setPath(String path) {
        this.path = path;
    }

}
