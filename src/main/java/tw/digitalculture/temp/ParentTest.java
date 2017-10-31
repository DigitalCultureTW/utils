package tw.digitalculture.temp;

import java.io.File;

public class ParentTest {

    public static void main(String[] args) {
        File f = new File("C:" + File.separator);
        System.out.println(f.getParent());
    }
}
