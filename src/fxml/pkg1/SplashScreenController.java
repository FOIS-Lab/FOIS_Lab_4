/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml.pkg1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Henok_Gelaneh
 */
public class SplashScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private BorderPane borderpane;
    @FXML
    private AnchorPane splashpane;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        splash();
    }    
    
    private void splash(){
        new Thread(){
            public void run(){
                try{
                    Thread.sleep(3000);
                }catch(Exception e){
                    System.out.println(e);
                }
                Platform.runLater(new Runnable(){
                    public void run(){
                        try {
                            borderpane = FXMLLoader.load(getClass().getResource("MainAppFXML.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(borderpane);
                            stage.setTitle("DDOS Attack");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.setMinHeight(400);
                            stage.setMinWidth(600);
                            stage.show();
                            splashpane.getScene().getWindow().hide();
                        } catch (IOException ex) {
                            Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }.start();
    }
    
}
