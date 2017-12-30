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
package tw.digitalculture.inventory;

/**
 * 
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
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
