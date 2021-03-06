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
package tw.digitalculture.metadata;

import tw.digitalculture.utils.FileChooser;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import org.apache.commons.imaging.formats.jpeg.JpegImageParser;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShort;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import static tw.digitalculture.utils.Constants.chooseDir;
import tw.digitalculture.utils.DirectoryReader;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class CorrectExif {

    File src_file;
    String target_dir;
    TiffImageMetadata tim;
    JpegImageParser jip = new JpegImageParser();
    TagInfoShort o = new TagInfoShort("Orientation", 274, TiffDirectoryType.TIFF_DIRECTORY_ROOT);

    public static void main(String[] args) throws Exception {
        String d = chooseDir(System.getProperty("user.home"), "請選擇批次目錄");
        String t = chooseDir(System.getProperty("user.home"), "請選擇寫出目錄");
        DirectoryReader dr = new DirectoryReader(d, false);
        dr.files.forEach((File f) -> {
            if (f.getName().toLowerCase().endsWith("jpg")) {
                try {
                    CorrectExif ce = new CorrectExif(f, t);
                    int orientation = ce.getOrientation();
                    ce.correctOrientation((short) 1);
                } catch (Exception ex) {
                    Logger.getLogger(CorrectExif.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public CorrectExif(String src_path) throws Exception {
        this(new File(src_path), null);
    }

    public CorrectExif(File f, String t) throws Exception {
        System.out.println("Source file = " + f.getPath());
        this.src_file = f;
        this.target_dir = t;
        ByteSource bs = new ByteSourceFile(src_file);
        this.tim = jip.getExifMetadata(bs, null);
    }

    public int getOrientation() throws ImageReadException {
        TiffField tf = tim.findField(o);
        System.out.println(tf);
        return tf.getIntValue();
    }


    /*
    1        2       3      4         5            6           7          8

   888888  888888      88  88      8888888888  88                  88  8888888888
   88          88      88  88      88  88      88  88          88  88      88  88
   8888      8888    8888  8888    88          8888888888  8888888888          88
   88          88      88  88
   88          88  888888  888888
     */
    public String correctOrientation(short orientation)
            throws ImageReadException, ImageWriteException, Exception {

        TiffOutputSet tos = tim.getOutputSet();
        tos.getRootDirectory().removeField(o);
        tos.getRootDirectory().add(o, orientation);
        ExifRewriter er = new ExifRewriter();
        if (this.target_dir == null) {
            this.target_dir = FileChooser.chooseFile(System.getProperty("user.home"),
                    "請選擇影像檔", new String[]{"jpg", "jpg影像"}, "Save", src_file.getName());
        }
        String out_path = this.target_dir + File.separator + "ExifCorrected - " + src_file.getName();
        er.updateExifMetadataLossless(src_file,
                new FileOutputStream(out_path),
                tos);
        return out_path;
    }

    public List getAllFields() throws ImageReadException {
        List data = tim.getAllFields();
//        data.forEach((t) -> {
//            System.out.println(t);
//        });
        return data;
    }
}
