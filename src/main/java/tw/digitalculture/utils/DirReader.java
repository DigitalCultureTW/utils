/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.digitalculture.utils;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan
 */
public class DirReader {

    private String path;
    private boolean subDir = false;
    public List<File> files = new ArrayList<>();
//

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        DirReader dr = new DirReader("D:\\Google Drive\\Google Photos", true);
        for (File f : dr.files) {
            System.out.print(f.getPath());
            //     System.out.println(f.getParent() + " " + f.getName());
            System.out.printf(" = %.1f kbytes.\n", f.length() / 1024.0);
            System.out.printf("= %.1f kbytes. [%s]\n", f.length() / 1024.0, MD5Utils.getChecksum(f.getPath()));
        }
        System.out.println("Total: " + dr.files.size() + " files.");
    }
//

    public DirReader(String path, boolean subDir) {
        this.path = path;
        this.subDir = subDir;
        refresh();
    }

    public DirReader(String path) {
        this(path, false);
    }

    public final void refresh() {
        this.files = getDir(this.path);
    }

    private List<File> getDir(String path) {
        File[] fileList = new File(path).listFiles();
        ArrayList<File> file_pool = new ArrayList<>();

        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) {
                file_pool.add(fileList[i]);
                //System.out.println("File " + listOfFiles[i].getPath());
            } else if (fileList[i].isDirectory() && subDir) {
                file_pool.addAll(getDir(fileList[i].getPath()));
            }
        }
        return file_pool;
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
