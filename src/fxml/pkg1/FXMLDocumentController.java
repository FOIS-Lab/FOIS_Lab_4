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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Henok_Gelaneh
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField hostname;


    public TextField port;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:MM:SS");
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
    public void ping(ActionEvent event) throws IOException {
        boolean bol = checkInt();
        if (bol){
            if (hostname.getText().isEmpty()){
                showMessageDialog(null, "Please Enter Hostname/Ip!");
            }else{
                String str = sendPingRequest(hostname.getText());
                output.appendText("["+dtf.format(now)+"]"+ str+'\n');
            }
        } else{
            output.appendText("["+dtf.format(now)+"]"+ "Internet Connection not found"+'\n');
        }
        
    }
    @FXML
    public void checkport(ActionEvent event) throws IOException {
        boolean bol = checkInt();
        if (bol){
            if (port.getText().isEmpty()){
                showMessageDialog(null, "Please Enter Port!");
            }else if(hostname.getText().isEmpty()){
                showMessageDialog(null, "Please Enter Hostname/Ip!");
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
    public void checkinternet(ActionEvent event) throws IOException {
        String str3 = checkInterent();
        output.appendText("["+dtf.format(now)+"]"+ str3+'\n');
    }
    @FXML
    public void startattack(ActionEvent event) throws IOException {
        boolean bol = checkInt();
        if (bol){
            if (synflood.isSelected()){
                output.appendText("["+dtf.format(now)+"]"+ "Starting SYN flood Attack..."+'\n');
            } else {
                output.appendText("["+dtf.format(now)+"]"+ "Starting HTTP flood Attack..."+'\n');

            }
        }else if (port.getText().isEmpty()){
            showMessageDialog(null, "Please Enter Port!");
        }else if(hostname.getText().isEmpty()){
            showMessageDialog(null, "Please Enter Hostname/Ip!");
        }
        
        else{
            output.appendText("["+dtf.format(now)+"]"+ "Internet Connection not found"+'\n');
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    public String sendPingRequest(String ipAddress)
            throws UnknownHostException, IOException
    {
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
    
    public String checkInterent() throws IOException{
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
  // Sends ping request to a provided IP address
    
}
