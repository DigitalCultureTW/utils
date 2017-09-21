package tw.digitalculture.temp;

import tw.digitalculture.utils.Constants;

public class TestTask {

    public static void main(String[] args) {

        String message = "我試看看。";
        Constants.messageWindow(message, new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    for (int i = 0; i < 1000000; i++) {
                        System.out.println(i);
                    }
                }
            }
        });
    }
}
