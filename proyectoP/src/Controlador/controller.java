/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author EvilFourier
 */
public class controller {
    @FXML
    Button btn1;
    @FXML
    Button btnScene2;
    @FXML
    AnchorPane ap;
    @FXML
    TextField campoFilas;
    @FXML
    TextField campoColumnas;
    
    public void handleBtn1() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/FXML.fxml"));
        Stage window = (Stage) btn1.getScene().getWindow();
        window.setScene(new Scene(root, 900, 800));
    }
    @FXML
    public void handleBtn2() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("controller.fxml"));
        Stage window = (Stage) btnScene2.getScene().getWindow();
        window.setScene(new Scene(root, 500, 600));
    }
    

    public int obtenerFila(){
        int fila = Integer.parseInt(campoFilas.getText());
        return fila;
    }
    
    
    public int obtenerColumna(){
        int columna = Integer.parseInt(campoColumnas.getText());
        return columna;
    }


    
}
