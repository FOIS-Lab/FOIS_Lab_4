package FOIS_LAB;
import java.net.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class HelperFunctions {
    public static String slasher(String hostname){
        if(hostname.startsWith("https://")){
            return hostname.replace("https://", "");
        }else if(hostname.startsWith("http://")){
            return hostname.replace("http://","");
        }else return hostname;
    }
    public static String schemeAdderS(String hostname){
        return "https://"+hostname;
    }
    public static String schemeAdder(String hostname){
        return "http://"+hostname;
    }
    public static boolean ping(Inet4Address ipAddress) throws UnknownHostException, IOException {
        InetAddress addr = InetAddress.getByName(ipAddress.getHostName());
        System.out.println("Sending Ping Request to " + ipAddress);
        if (addr.isReachable(5000)) {
            System.out.println("Host is up and reachable!");
            return true;
        }
        else{
            System.out.println("Host is down!");
            return false;
        }
    }
    public static Inet4Address resolver(String hostname){
        try{

            if (hostname.startsWith("https://")) {
                System.out.println("Slashed");
            } else {
                System.out.println("No need to slash");
            }

            return (Inet4Address) Inet4Address.getByName(hostname);
        }
        catch (UnknownHostException e){
            return null;
        }
    }
    public static boolean pingHost(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public static boolean isReachable(String targetUrl) throws IOException {
        HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(
                targetUrl).openConnection();
        httpUrlConnection.setRequestMethod("HEAD");

        try
        {
            int responseCode = httpUrlConnection.getResponseCode();

            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (UnknownHostException noInternetConnection)
        {
            return false;
        }
    }
}



