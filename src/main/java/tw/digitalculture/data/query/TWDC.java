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
package tw.digitalculture.data.query;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import tw.digitalculture.data.Config.DATA;
import tw.digitalculture.data.bin.TWDC_XML;
import tw.digitalculture.data.model.TWDC_Record;
import static tw.digitalculture.data.Config.DATA.TWDC.URL_BASE;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public final class TWDC {

    public List<TWDC_Record> dataset;

    public TWDC() {
        this.dataset = new ArrayList<>();
        refresh(URL_BASE);
    }

    public void refresh(String url) {
        TWDC_XML.fetch(url, (Document data) -> {
            Elements xml_records = data.getElementsByTag("record");
            System.out.println("processing " + xml_records.size() + " records...");
            xml_records.forEach((rec) -> {
                TWDC_Record record = new TWDC_Record(
                        rec.getElementsByTag("header"),
                        rec.getElementsByTag("metadata"));
                // if (!record.uri.isEmpty() && FILETYPES.contains(record.filetype.toLowerCase())) {
                this.dataset.add(record);
//                System.out.println(this.dataset.size() + ". " + record.title);
                // }
            });
            String resumptionToken = data.getElementsByTag("resumptionToken").text();
            if (resumptionToken.isEmpty()) {
                System.out.println(
                        "Initializing twdc dataset completed. Total record fetched = " + dataset.size());
            } else {
                refresh(DATA.TWDC.URL_TOKEN + resumptionToken);
            }
        }
        );
    }

}
