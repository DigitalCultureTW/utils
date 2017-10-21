package tw.digitalculture.metadata;

import tw.digitalculture.utils.Constants;
import tw.digitalculture.utils.FileChooser;

/**
 *
 * @author Jonathan
 */
public class ReadExifOrientation {

    public static void main(String[] args) throws Exception {
        String path = FileChooser.chooseFile("D:\\_Working_Temp", "",
                new String[]{"jpg", ""}, "Open");
        CorrectExif ce = new CorrectExif(path);
        Constants.messageWindow("Orientation = " + ce.getOrientation(), 2000);
    }

}
