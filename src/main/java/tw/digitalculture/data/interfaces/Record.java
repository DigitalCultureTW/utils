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
package tw.digitalculture.data.interfaces;

import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public abstract class Record {

    public String id;
    public String title;
    public String description;
    public String uri;
    public String filename;
    public String filetype;

    public Record(String id, String title, String description, String uri) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.uri = uri;
        setFilename();
        setFiletype();
    }
    public Record(int id, String title, String description, String uri) {
        this(String.valueOf(id), title, description, uri);
    }
    public void setFilename() {
        this.filename = this.uri.contains("/")
                ? this.uri.split("/")[this.uri.split("/").length - 1] : "";
    }

    public void setFiletype() {
        this.filetype = FilenameUtils.getExtension(this.uri).toLowerCase();
    }
}
