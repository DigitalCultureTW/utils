package tw.digitalculture.utils;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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

    public DirectoryReader(String path, boolean subDir) {
        System.out.println("Reading Directory: " + path);
        System.out.println("Including Sub-Directories: " + subDir);
        this.path = path;
        this.subDir = subDir;
        refresh();
    }

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
                if (f.isFile()) {
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
