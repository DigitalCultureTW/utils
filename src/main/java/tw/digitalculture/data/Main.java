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
package tw.digitalculture.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import tw.digitalculture.data.model.Rights;
import tw.digitalculture.data.model.RightsComparator;
import tw.digitalculture.data.model.TWDC_Record;
import tw.digitalculture.data.query.TWDC;
import tw.digitalculture.data.utils.CSVWriter;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Main {

    static TWDC twdc;
    static int count;
    static String template = "twdc_template";
    static String statistics = "twdc_statistics";
    static String report = "twdc_report";

    public static void main(String[] args) throws IOException, Exception {
        twdc = new TWDC();
        twdc.dataset.sort(new RightsComparator());
        createStatistics();
        createParagraph();
    }

    public static void createStatistics() throws IOException {
        class Data {

            int[] in = new int[Rights.values().length];
            int[] out = new int[Rights.values().length];
        }
        Data d = new Data();
        twdc.dataset.forEach(rec -> {
            if (rec.contributor.contains("臺中市政府")) {
                d.in[rec.rights.ordinal()]++;
            } else {
                d.out[rec.rights.ordinal()]++;
                System.out.println(rec.title);
            }
        });
        try (XWPFDocument doc = new XWPFDocument()) {
            XWPFTable table = doc.createTable(1, 2);
            table.getRow(0).getCell(0).setText("府內物件授權狀態統計");
            for (Rights right : Rights.values()) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(right.full_label);
                row.getCell(1).setText("" + d.in[right.ordinal()]);
            }
            table.createRow().getCell(0).setText("府外物件授權狀態統計");
            for (Rights right : Rights.values()) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(right.full_label);
                row.getCell(1).setText("" + d.out[right.ordinal()]);
            }
            try (FileOutputStream fos = new FileOutputStream(statistics + ".docx")) {
                doc.write(fos);
            }
        }
    }

    /**
     * create table. 1: 識別機碼 2: 標題 title 3: 授權條款 rights 4: 描述 description
     *
     * @throws java.lang.Exception
     */
    public static void createParagraph() throws Exception {
        count = 0;
        try (XWPFDocument doc = new XWPFDocument()) {

            //create paragraph
            XWPFParagraph paragraph = doc.createParagraph();
            int r = 0;
            for (TWDC_Record rec : twdc.dataset) {
                System.out.println((++r) + ". " + rec.title);

                XWPFRun p1 = paragraph.createRun();
//                p1.setFontFamily("新細明體");
                p1.setBold(true);
                p1.setText("序　　號：" + String.format("%03d", r));
                p1.addBreak();

                p1.setText("識別機碼：" + rec.id);
                p1.addBreak();

                p1.setText("標　　題：" + rec.title);
                p1.addBreak();

                p1.setText("授權條款：" + rec.rights.full_label);
                p1.addBreak();
                p1.addBreak();

                XWPFRun p2 = paragraph.createRun();
                p2.setFontFamily("標楷體");
                for (String p : rec.description.replaceAll("。 ", "。\n").split("\n")) {
                    p2.setText(p);
                    p2.addBreak();
                }
                p2.addBreak();

                countCopyrighted(rec.rights);
            }
            try (FileOutputStream fos = new FileOutputStream(report + ".docx")) {
                doc.write(fos);
            }
        }
        System.out.println("CC = " + count);
    }

    public static void createTable() throws Exception {
        count = 0;
        try (XWPFDocument doc = new XWPFDocument()) {
            XWPFTable table = doc.createTable();
            int r = 0;
            for (TWDC_Record rec : twdc.dataset) {
                System.out.println((r / 4) + ". " + rec.title);
                XWPFTableRow row;
                if (r == 0) {
                    row = table.getRow(r);
                    row.getCell(0).setText("識別機碼");
                    row.addNewTableCell().setText(rec.id);
                } else {
                    row = table.createRow();
                    row.getCell(0).setText("識別機碼");
                    row.getCell(1).setText(rec.id);
                }
                r++;
                row = table.createRow();
                row.getCell(0).setText("標題");
                row.getCell(1).setText(rec.title);
                r++;
                row = table.createRow();
                row.getCell(0).setText("授權條款");
                row.getCell(1).setText(rec.rights.full_label);
                r++;
                row = table.createRow();
                row.getCell(0).setText(rec.description);
                r++;
                countCopyrighted(rec.rights);
            }
            try (FileOutputStream fos = new FileOutputStream(report + ".docx")) {
                doc.write(fos);
            }
        }
        System.out.println("CC = " + count);
    }

    public static void createCSV() throws IOException {
        count = 0;
        CSVWriter cw = new CSVWriter("twdc_output.csv");
        List<List<String>> data
                = twdc.dataset.stream().map(Main::recToStringList).collect(Collectors.toList());
        System.out.println("CC = " + count);
        cw.write(data);
    }

    private static List<String> recToStringList(TWDC_Record row) {
        countCopyrighted(row.rights);
        return Arrays.asList(new String[]{
            row.id,
            row.title,
            row.description,
            row.rights.short_label
        });
    }

    public static void countCopyrighted(Rights rec) {
        if (!rec.equals(Rights.COPYRIGHTED)) {
            count++;
        }
    }
}
