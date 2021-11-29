/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.TextAlignment;

import jfxtras.labs.util.event.MouseControlUtil;

/**
 * FXML Controller class
 *
 * @author david
 */
public class FXMLController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private AnchorPane root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(20,20,20), rgb(30,60,80));");
        GridPane grid = new GridPane();
        Random r = new Random();
        
        int offset = 5;
        int gap = 10;
        int size = 50;
        LinkedList<Pane> enlace = new LinkedList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char letra = (char) (r.nextInt(26) + 'a');
                Label lb = new Label(String.valueOf(i) + String.valueOf(j));
                lb.setMinSize(40, 40);
                lb.setAlignment(Pos.CENTER);
                lb.setTextAlignment(TextAlignment.CENTER);
                
                Pane p = new Pane();
                p.setMinSize(size, size);
                p.setOpacity(2);
                p.setStyle("-fx-border-color: white;");
                Label l = new Label(String.valueOf(letra));
                l.setTextFill(Color.WHITE);
                p.setLayoutX(offset + j * (size + gap));
                p.setLayoutY(offset + i * (size + gap));
                l.setLayoutX(size - 30);
                l.setLayoutY(size - 35);
                l.setTextAlignment(TextAlignment.CENTER);
                p.getChildren().add(l);
                if (i == 0) {
                    enlace.add(p);
                }
                p.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    
                    @Override
                    public void handle(MouseEvent t) {
//                        p.setLayoutY(t.getSceneY() - 20);
//                        p.setLayoutX(t.getSceneX() - 20);

                        double x=t.getX();
                        double y=t.getY();

                        System.out.println("x: " + x + " | y:" + y);
                        if (x > y) {
                            p.setLayoutX(t.getSceneX() - 20);
                        } else if (x < y) {
                            p.setLayoutY(t.getSceneY() - 20);
                        }
                        x = 0;
                        y = 0;
                    }
                    
                });
                //MouseControlUtil.makeDraggable(p);

                root.getChildren().add(p);
            }
        }

        //
        pane.getChildren().add(grid);
        pane.setLayoutY(73);
        pane.setOnTouchMoved((e) -> {
            System.out.println("algo");
        });
        
    }
    
}
