/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.digitalculture.metadata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sanselan.common.byteSources.ByteSource;
import org.apache.sanselan.common.byteSources.ByteSourceFile;
import org.apache.sanselan.formats.jpeg.JpegImageParser;
import org.apache.sanselan.formats.jpeg.exifRewrite.ExifRewriter;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffFieldTypeConstants;
import org.apache.sanselan.formats.tiff.write.TiffOutputDirectory;
import org.apache.sanselan.formats.tiff.write.TiffOutputSet;

/**
 *
 * @author Jonathan
 */
public class CorrectExif {

    static String photo_path = System.getProperty("user.dir")
            + "\\src\\main\\resources\\original_photo.JPG";

    public static void main(String[] args) throws Exception {

        String path = photo_path;
//                FileChooser.chooseFile(System.getProperty("user.home"),
//                "請選擇影像檔", new String[]{"jpg", "jpg影像"}, "Open");
        JpegImageParser jip = new JpegImageParser();
        File f = new File(path);
        ByteSource bs = new ByteSourceFile(f);
        Map params = new HashMap();
        TiffImageMetadata tim = jip.getExifMetadata(bs, params);
//        List data = tim.getAllFields();
//        data.forEach((t) -> {
//            System.out.println(t);
//        });
//        TiffField tf = 
        tim.findField(new TagInfo("Orientation", 274,
                TiffFieldTypeConstants.FIELD_TYPE_SHORT)).valueOffsetBytes[1] = 0;
//        System.out.println(tf);
//        for (byte b : tf.valueOffsetBytes) {
//            System.out.printf("%02X ", b);
//        }
//        tf.valueOffsetBytes[1] = 0;
        TiffOutputSet tos = tim.getOutputSet();
        ExifRewriter er = new ExifRewriter();
        String output_path = FileChooser.chooseFile(
                System.getProperty("user.home"),
                "請選擇影像檔", new String[]{"jpg", "jpg影像"}, "Save");
        er.updateExifMetadataLossless(f, new FileOutputStream(output_path),
                tos);

//        jip.
//        List records = new ArrayList(), rawBlocks = new ArrayList();
//        PhotoshopApp13Data pa13 = new PhotoshopApp13Data(records, rawBlocks);
//        JpegPhotoshopMetadata jpm = new JpegPhotoshopMetadata(pa13);
//        TiffHeader th = new TiffHeader(0, 0, 0);
//        ArrayList dir = new ArrayList();
//        TiffContents tc = new TiffContents(th, dir);
//        TiffImageMetadata tim = new TiffImageMetadata(tc);
//        JpegImageMetadata jim = new JpegImageMetadata(jpm, tim);
    }
}
