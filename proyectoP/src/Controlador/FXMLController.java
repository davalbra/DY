/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Estructuras.ListaCircularDE;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.TextAlignment;

/**
 * FXML Controller class
 *
 * @author david
 */
public class FXMLController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private RadioButton rbn;
    static HashMap<Node, ArrayList<Integer>> mapa = new HashMap<>();
    static int celdas = 9;
    static ListaCircularDE<Node> listacircular = new ListaCircularDE<Node>();
    static int offset = 5;
    static int gap = 10;
    static int size = 50;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        root.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(20,20,20), rgb(30,60,80));");
        GridPane grid = new GridPane();
        Random r = new Random();

        for (int i = 0; i < celdas; i++) {
            for (int j = 0; j < celdas; j++) {
                //matriz
                //char letra = (char) (r.nextInt(26) + 'a');
                Pane p = new Pane();
                p.setMinSize(size, size);
                p.setOpacity(2);
                p.setStyle("-fx-border-color: white;");
                Label l = new Label(String.valueOf(i)+String.valueOf(j));
                l.setTextFill(Color.WHITE);
                p.setLayoutX(posIniX(i));
                p.setLayoutY(posIniY(j));
                l.setLayoutX(size - 30);
                l.setLayoutY(size - 35);
                l.setTextAlignment(TextAlignment.CENTER);
                p.getChildren().add(l);
                //agregando al array que necesita el mapa
                ArrayList<Integer> lista = new ArrayList<>();
                lista.add(i);
                lista.add(j);
                lista.add(posIniX(i));
                lista.add(posIniY(j));
                mapa.put(p, lista);
                //fin
                
                //creando la logica de las listas circulates
                p.setOnMousePressed((MouseEvent t) -> {
                    System.out.println("i: "+mapa.get(p).get(0)+" j: "+mapa.get(p).get(1)+"  posX: "+mapa.get(p).get(2)+"  posY: "+mapa.get(p).get(3));
                    System.out.println("clikeo");
                     
                    listacircular.clear();
                    if (rbn.isSelected()) {
                        for (int k = 0; k < celdas; k++) {
                            listacircular.addLast(root.getChildren().get(saltosX(k, p)));
                        }
                    } else {
                        for (int k = 0; k < celdas; k++) {
                            listacircular.addLast(root.getChildren().get(saltosY(k, p)));
                        }
                    }
                    for (int k = 0; k < celdas; k++) {
                        System.out.println("posx: "+listacircular.get(k).getLayoutX()+" posy: "+listacircular.get(k).getLayoutY());
                    }
                    
                });
                //ordenando la matriz una vez modificada
                p.setOnMouseReleased((MouseEvent t) -> {
                    System.out.println("desclickeo");
                    if (rbn.isSelected()) {
                        for (int n = 0; n < celdas; n++) {
                            Node nd = listacircular.get(n);
                            nd.setLayoutY(posIniY(n));
                        }
                    } else {
                        
                        for (int n = 0; n < celdas; n++) {
                            Node nd = listacircular.get(n);
                            nd.setLayoutX(posIniX(n));
                            
                        }
                    }
                });
                //craendo la logica de movimiento
                p.setOnMouseDragged((MouseEvent t) -> {
                    System.out.println("deslizo");
                    if (rbn.isSelected()) {
                        
                        for (int k = 0; k < celdas; k++) {
                            Node nd = listacircular.get(k);
                            nd.setLayoutY(-20 + mapa.get(nd).get(3) + (t.getSceneY() - mapa.get(p).get(3)));
                            if (Math.abs(mapa.get(p).get(3) - Math.abs(p.getLayoutY())) > 60) {
                                listacircular.movehead(-1);
                                for (int n = 0; n < celdas; n++) {
                                    Node nodoVis = listacircular.get(n);
                                    mapa.get(nodoVis).set(3, posIniY(n));
                                    mapa.get(nodoVis).set(1, n);
                                }
                            }
                        }
                    } else {
                        for (int k = 0; k < celdas; k++) {
                            Node nd = listacircular.get(k);
                            nd.setLayoutX(-20 + mapa.get(nd).get(2) + (t.getSceneX() - mapa.get(p).get(2)));
                            if (Math.abs(mapa.get(p).get(2) - Math.abs(p.getLayoutX())) > 60) {
//                                System.out.println("mapa.get(p).get(2)  "+mapa.get(p).get(2));
//                                System.out.println("Math.abs(p.getLayoutX())  "+Math.abs(p.getLayoutX()));
//                                System.out.println("mapa.get(p).get(2) - Math.abs(p.getLayoutX())   " +(mapa.get(p).get(2) - Math.abs(p.getLayoutX())));
//                                System.out.println("Math.abs(mapa.get(p).get(2) - Math.abs(p.getLayoutX())) > 60  " +(Math.abs(mapa.get(p).get(2) - Math.abs(p.getLayoutX())) > 60));
                                listacircular.movehead(-1);
                                for (int n = 0; n < celdas; n++) {
                                    Node nodoVis = listacircular.get(n);
                                    mapa.get(nodoVis).set(2, posIniX(n));
                                    mapa.get(nodoVis).set(0, n);
                                }
                            }
                        }
                    }
                });
                root.getChildren().add(p);
            }
        }
        
        //
    }

    public int saltosX(int k, Pane p) {
        
        int saltos = mapa.get(p).get(0) * celdas + 1 + k;
        return saltos;
    }

    public int saltosY(int k, Pane p) {
        int saltos = k * celdas + mapa.get(p).get(1) + 1;
        return saltos;
    }

    public Integer posIniX(int i) {
        Integer posIniX = offset + i * (size + gap);
        return posIniX;
    }

    public Integer posIniY(int j) {
        Integer posIniY = offset + j * (size + gap);
        return posIniY;

    }

}
