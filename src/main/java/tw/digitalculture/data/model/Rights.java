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
package tw.digitalculture.data.model;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public enum Rights {
    TCFCL("http://portal.digitalculture.tw/license/TCFCL/", "臺中自由文化授權條款1.0版"),
    TCOCL("http://portal.digitalculture.tw/license/TCOCL/", "臺中開放文化授權條款1.0版"),
    CC_BY_4_0("https://creativecommons.org/licenses/by/4.0/tw/", "創用CC授權條款：姓名標示(4.0)", "CC BY 4.0"),
    CC_BY_NC_4_0("https://creativecommons.org/licenses/by-nc/4.0/tw/", "創用CC授權條款：姓名標示─非商業性(4.0)", "CC BY-NC 4.0"),
    CC_BY_NC_SA_4_0("https://creativecommons.org/licenses/by-nc-sa/4.0/tw/", "創用CC授權條款：姓名標示─非商業性─相同方式分享(4.0)", "CC BY-NC-SA 4.0"),
    CC_BY_ND_4_0("https://creativecommons.org/licenses/by-nd/4.0/tw/", "創用CC授權條款：姓名標示─禁止改作(4.0)", "CC BY-ND 4.0"),
    CC_BY_NC_ND_4_0("https://creativecommons.org/licenses/by-nc-nd/4.0/tw/", "創用CC授權條款：姓名標示─非商業性─禁止改作(4.0)", "CC BY-NC-ND 4.0"),
    CC_BY_SA_4_0("https://creativecommons.org/licenses/by-sa/4.0/tw/", "創用CC授權條款：姓名標示─相同方式分享(4.0)", "CC BY-SA 4.0"),
    OGDL("https://data.gov.tw/license", "政府資料開放授權", "Open Government Data License"),
    CC0("http://creativecommons.tw/cc0/", "公眾領域貢獻宣告(CC0)"),
    PDM("http://creativecommons.tw/pdm", "公眾領域標章(PDM，Public Domain Mark)"),
    COPYRIGHTED("http://data.digitalculture.tw/taichung/copyright", "僅得提供有限制之公眾瀏覽");

    String uri;
    public final String full_label;
    public final String short_label;

    Rights(String uri, String full_label) {
        this.uri = uri;
        this.full_label = full_label;
        this.short_label = this.toString();
    }

    Rights(String uri, String full_label, String short_label) {
        this.uri = uri;
        this.full_label = full_label;
        this.short_label = short_label;
    }

    public static Rights fromURI(String uri) {
        for (Rights r : Rights.values()) {
            if (r.uri.contains(uri.trim().split("//")[1])) {
                return r;
            }
        }
        return null;
    }

}
