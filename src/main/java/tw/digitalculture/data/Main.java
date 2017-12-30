/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.digitalculture.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    static String filename = "twdc_output";

    public static void main(String[] args) throws IOException, Exception {
        twdc = new TWDC();
        twdc.dataset.sort(new RightsComparator());
        createParagraph();
    }

    /**
     * create table. 1: 識別機碼 2: 標題 title 3: 授權條款 rights 4: 描述 description
     *
     * @throws java.lang.Exception
     */
    public static void createParagraph() throws Exception {
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

                count(rec.rights);
            }
            try (FileOutputStream fos = new FileOutputStream(filename + ".docx")) {
                doc.write(fos);
            }
        }
//        System.out.println("CC = " + count);
    }

    public static void createTable() throws Exception {
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
                count(rec.rights);
            }
            try (FileOutputStream fos = new FileOutputStream(filename + ".docx")) {
                doc.write(fos);
            }
        }
        System.out.println("CC = " + count);
    }

    public static void createCSV() throws IOException {
        CSVWriter cw = new CSVWriter("twdc_output.csv");
        List<List<String>> data = new ArrayList<>();
        twdc.dataset.forEach((TWDC_Record row) -> {
            count(row.rights);
            data.add(Arrays.asList(new String[]{
                row.id,
                row.title,
                row.description,
                row.rights.short_label
            }));
        });
        System.out.println("CC = " + count);
        cw.write(data);
    }

    public static void count(Rights rec) {
        if (!rec.equals(Rights.COPYRIGHTED)) {
            count++;
        }
    }
}
