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
package tw.digitalculture.data.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class CSVWriter {

    private static final char DEFAULT_SEPARATOR = ',';
    private static final String PREFIX = System.getProperty("user.home") + File.separatorChar;
    private final String filename;

    public CSVWriter(String filename) {
        this.filename = PREFIX + filename;
    }

    public void write(List<List<String>> data) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "utf8")) {
            for (List<String> row : data) {
                writeLine(osw, row, DEFAULT_SEPARATOR, '"');
            }
            osw.flush();
        }
    }

    private static String followCSVformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    public static void writeLine(Writer w, List<String> values, char separators, char customQuote)
            throws IOException {

        StringBuilder sb = new StringBuilder();
        values.forEach((value) -> {
            if (sb.length() > 0) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCSVformat(value));
            } else {
                sb.append(customQuote).append(followCSVformat(value)).append(customQuote);
            }
        });
        sb.append("\n");
        w.append(sb.toString());
    }

}
