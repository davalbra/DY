/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Estructuras.CNode;
import java.util.Random;
import Estructuras.List;
import Estructuras.ListaCircularDE;
import java.io.IOException;
import static java.lang.Math.random;
import static java.lang.StrictMath.random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Luci
 */
public class NewMain extends Application {

    /**
     * @param args the command line arguments char na = (char) (r.nextInt(26) +
     * 'a'); System.out.println(na);
     */
    public static void main(String[] args) {
        ListaCircularDE<String> listap = new ListaCircularDE<>();
        int vueltas=5;
        for (int i = 0; i < vueltas; i++) {
            listap.add(i, ""+i);
        }
        //System.out.println(listap.toString());
        ListIterator<String> it=listap.listIterator();
//        while(it.hasNext()){
//            System.out.println(it.next());
//        }

        launch();
    }

    @Override
    public void start(Stage escenario) {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/Vista/FXML.fxml"));
            Scene sc = new Scene(p);
            escenario.setScene(sc);
            escenario.show();
            //escenario.close();

        } catch (IOException ex) {
            System.out.println("gh");
        }

    }

    public static void movimientoNodo(int numeroNodo, String movimiento, int cantidad) {

    }

}
