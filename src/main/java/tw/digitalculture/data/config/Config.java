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
package tw.digitalculture.data.config;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Config {

    public static class DATA {

        public static List<String> FILETYPES
                = Arrays.asList(new String[]{"jpg", "png"});

        public static class TWDC {

            public static String URL_BASE = "http://data.digitalculture.tw/taichung/oai?verb=ListRecords&metadataPrefix=oai_dc";
            public static String URL_TOKEN = "http://data.digitalculture.tw/taichung/oai?verb=ListRecords&&resumptionToken=";
        }

        public static class IDEASQL {

            public static String URL = "http://designav.io/api/image/search/";
            public static String MULTI_URL = "http://designav.io/api/image/search_multi/";
            public static String WB_URL = "http://designav.io/api/image/wordbreak/";
        }
    }
}
