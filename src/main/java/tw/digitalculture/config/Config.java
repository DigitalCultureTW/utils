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
package tw.digitalculture.config;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Config {

    public static class PROJECT {

        public static String TITLE_MAIN = "記憶窗櫺";
        public static String TITLE_ENGLISH = "The Window of Our Memories";
        public static String SUBTITLE = "共築臺中印象的角落";

        public static String TITLE = PROJECT.TITLE_MAIN + "—" + PROJECT.SUBTITLE;
        public static String LOGO_PATH = "/element/logo_2.png";
        public static String VERSION = "0.9.1";
    }

    public static class UMBRA {

        public static String URL = "http://wm.localstudies.tw";
        public static String FONT = "'DFKai-sb', 'BiauKai'";
        public static String TITLE_COLOR = "Silver";
        public static String QRCODE_IMG;
    }

    public static class LUNA {

        public static int COLUMN = 8, ROW = 4;
//        public static String FONT = "Arial, 'Microsoft JhengHei', 'Heiti TC'";
        public static String FONT = "'Yu Gothic', SimHei";

        public static class CARD {

            public static double BORDER_RATIO = 0.03;
            public static String BORDER_STYLE = "inset";
            public static String[] BORDER_COLOR = {"Silver", "White"};
            public static String COLOR = "#121212";
//            public static String FONT = "'Times New Roman', DFKai-sb, BiauKai";
            public static String FONT = "Meiryo, '微軟正黑體', 'Microsoft JhengHei'";
//            public static String FONT = "source-han-serif-tc-n7";
//            public static String FONT = "source-han-sans-traditional";
            public static String FONT_WEIGHT = "normal";
            public static String FONT_COLOR = "white";

        }

        public static int FLIP_TIME_OUT = 5000; //ms
        public static int SYSTEM_LOGO_TIME_OUT = 7000; //ms
        public static int SHOW_INTERVAL = 3000; //ms
        public static int SHOW_STAY = 1500; //ms
        public static String QRCODE = "@QR_CODE_TOKEN";
        public static String TEXT = "@TEXT_TOKEN";

        public static int MIN_LOGO() {
            return ((LUNA.ROW * LUNA.COLUMN) / 8);
        }

        public static double TITLE_RATIO = 0.6;
        public static String TITLE_COLOR = "Silver";
        public static double TOP_HEIGHT_RATIO = 0.08;
        public static double BOTTOM_HEIGHT_RATIO = 0.04;

        public static double MOD(int row) {
            return (row > 2) ? 1 : ((row == 1) ? 0.70 : 0.95);
        }
    }

    public static class DATA {

        public static List<String> FILETYPES
                = Arrays.asList(new String[]{"jpg", "png"});

        public static int LIMIT() {
            return (int) (LUNA.ROW * LUNA.COLUMN) / 2;
        }

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
