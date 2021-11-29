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
        LinkedList<CNode<String>> letrasnodo = new LinkedList<>();
        Random r = new Random();
        int dimension = 9;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                letrasnodo.add(new CNode(String.valueOf(i) + String.valueOf(j), i, j));
            }
        }
        int medidor = 0;
        for (CNode<String> nod : letrasnodo) {

            if (nod.getI() != medidor) {
                medidor = nod.getI();
                System.out.println("");
            }
            System.out.print(" " + nod.getI() + ":" + nod.getJ());
        }
        launch();
    }

    @Override
    public void start(Stage escenario) {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/Vista/FXML.fxml"));
            Scene sc = new Scene(p);
            escenario.setScene(sc);
            escenario.show();

        } catch (IOException ex) {
            System.out.println("gh");
        }

    }

    public static void movimientoNodo(int numeroNodo, String movimiento, int cantidad) {

    }

}
