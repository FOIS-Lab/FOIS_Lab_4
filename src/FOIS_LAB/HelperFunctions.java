package FOIS_LAB;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static String schemeRemover(String hostname){
        if(hostname.startsWith("http://")) return hostname.replace("http://", "");
        else if(hostname.startsWith("https://")) return hostname.replace("https://", "");
        else return hostname;
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
    public static String resolver(String hostname){
        if(HelperFunctions.isValidIP(hostname)) return hostname;
        try{
            return Inet4Address.getByName(hostname).getHostAddress();
        }
        catch (UnknownHostException e){
            return hostname;
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
    public static boolean isValidIP(String ipAddr){
        Pattern ptn = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
        Matcher mtch = ptn.matcher(ipAddr);
        return mtch.find();
    }



}



