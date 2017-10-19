package tw.digitalculture.metadata;

/**
 *
 * @author Jonathan
 */
public class ReadExifOrientation {

    public static void main(String[] args) throws Exception {
        String path = FileChooser.chooseFile("D:\\_Working_Temp", "",
                new String[]{"jpg", ""}, "Open");
        CorrectExif ce = new CorrectExif(path);
        System.out.println(ce.getOrientation());
    }

}
