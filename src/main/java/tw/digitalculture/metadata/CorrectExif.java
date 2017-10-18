package tw.digitalculture.metadata;

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
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShort;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import static tw.digitalculture.utils.Constants.chooseDir;
import tw.digitalculture.utils.DirectoryReader;

/**
 *
 * @author Jonathan
 */
public class CorrectExif {

    static String photo_path = System.getProperty("user.dir")
            + "\\src\\main\\resources\\original_photo.JPG";

    File src_file;
    String target_dir;
    TiffImageMetadata tim;
    JpegImageParser jip = new JpegImageParser();

    public static void main(String[] args) throws Exception {
        String d = chooseDir(System.getProperty("user.home"), "請選擇批次目錄");
        String t = chooseDir(System.getProperty("user.home"), "請選擇寫出目錄");
        DirectoryReader dr = new DirectoryReader(d, false);
        dr.files.forEach((File f) -> {
            if (f.getName().toLowerCase().endsWith("jpg")) {
                try {
                    CorrectExif ce = new CorrectExif(f, t);
                    int orientation = ce.getOrientation();
                    ce.correctOrientation((byte) 0);
                } catch (Exception ex) {
                    Logger.getLogger(CorrectExif.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
//        String dir = DirectoryReader.chooseFile(System.getProperty("user.home"),
//                "請選擇影像檔", new String[]{"jpg", "jpg影像"}, "Open");
//                   "C:\\Users\\Jonathan Chang\\Desktop\\competition\\大專組-第二名-A0118-楊天助-媽祖起駕.JPG";
    }

    public CorrectExif(File f, String t) throws Exception {
        System.out.println("path=" + f.getPath());
        this.src_file = f;
        this.target_dir = t;
        ByteSource bs = new ByteSourceFile(src_file);
        this.tim = jip.getExifMetadata(bs, null);
    }

    public int getOrientation() throws ImageReadException {
        TiffField tf = tim.findField(new TagInfo("Orientation", 274,
                FieldType.SHORT) {
        });
        System.out.println(tf);
//        for (byte b : tf.getByteArrayValue()) {
//            System.out.printf("%02X ", b);
//        }
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
    public void correctOrientation(byte orientation)
            throws ImageReadException, ImageWriteException, Exception {

        TiffOutputSet tos = tim.getOutputSet();
        TagInfoShort o = new TagInfoShort("Orientation", 274, 1,
                TiffDirectoryType.TIFF_DIRECTORY_ROOT);
        tos.getRootDirectory().removeField(o);
        tos.getRootDirectory().add(o, (short) 0);
        ExifRewriter er = new ExifRewriter();
        if (this.target_dir == null) {
            this.target_dir = FileChooser.chooseFile(System.getProperty("user.home"),
                    "請選擇影像檔", new String[]{"jpg", "jpg影像"}, "Save", src_file.getName());
        }
        er.updateExifMetadataLossless(src_file,
                new FileOutputStream(this.target_dir + "\\ExifCorrected - " + src_file.getName()),
                tos);
    }

    public List getAllFields() throws ImageReadException {
        List data = tim.getAllFields();
        data.forEach((t) -> {
            System.out.println(t);
        });
        return data;
    }
}
