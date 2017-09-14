package tw.digitalculture.inventory;

public class Item {

    private String path;
    private String filename;
    private String checksum_md5;
    private long size;

    public Item(String path, String filename, long size) {
        this.path = path;
        this.filename = filename;
        this.size = size;
    }

    /**
     * Get the value of size
     *
     * @return the value of size
     */
    public long getSize() {
        return size;
    }

    /**
     * Set the value of size
     *
     * @param size new value of size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Get the value of checksumMD5
     *
     * @return the value of checksumMD5
     */
    public String getChecksumMD5() {
        return checksum_md5;
    }

    /**
     * Set the value of checksumMD5
     *
     * @param checksumMD5 new value of checksumMD5
     */
    public void setChecksumMD5(String checksumMD5) {
        this.checksum_md5 = checksumMD5;
    }

    /**
     * Get the value of filename
     *
     * @return the value of filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Set the value of filename
     *
     * @param filename new value of filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
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

    @Override
    public String toString() {
        return getPath() + " - " + getFilename() + " [" + getSize() + "]";
    }
}
