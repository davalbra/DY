/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Estructuras.ListaCircularDE;
import Modelo.Persona;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.util.Random;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    @FXML
    private Button btnAC;
    @FXML
    private VBox vBoxPalabra;
    @FXML
    private Button btnvp;

    static LinkedHashMap<Node, ArrayList<Integer>> mapa = new LinkedHashMap<>();
    static int din = 4;
    static int djn = 3;
    static ListaCircularDE<Node> listacircular = new ListaCircularDE<Node>();
    static int offset = 100;
    static int gap = 10;
    static int size = 50;
    static ListaCircularDE<Node> palabras = new ListaCircularDE<Node>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Persona person = new Persona();
        logicaDeColumnas(btnAC, person);

        //root.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(48,117,82),rgb(69,168,117), rgb(100,245,170));");
        root.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(11,75,217), rgb(129,12,248));");

        Random r = new Random();
        Label lb = new Label("casa");
        lb.setTextFill(Color.WHITE);
        vBoxPalabra.getChildren().add(lb);
        lb = new Label("perro");
        lb.setTextFill(Color.WHITE);
        vBoxPalabra.getChildren().add(lb);
        lb = new Label("gato");
        lb.setTextFill(Color.WHITE);
        vBoxPalabra.getChildren().add(lb);
        lb = new Label("pera");
        lb.setTextFill(Color.WHITE);
        vBoxPalabra.getChildren().add(lb);

        for (int i = 0; i < din; i++) {
            for (int j = 0; j < djn; j++) {
                logistica(r, i, j, person);
            }
        }

        //
    }

    public void logistica(Random r, int i, int j, Persona person) {
        char letra = (char) (r.nextInt(26) + 'A');
        Pane p = creacionPane(letra, i, j);
        //agregando al array que necesita el mapa
        ArrayList<Integer> lista = new ArrayList<>();
        lista.add(i);
        lista.add(j);
        lista.add(posIniX(i));
        lista.add(posIniY(j));
        lista.add(1);
        mapa.put(p, lista);
        eventoPreseed(p, person);
        eventoreleased(p);
        eventoDragged(p);
        eventomouseisup(p);
        eventoMouseIsDown(p);
        root.getChildren().add(p);
    }

    public Integer posIniX(int i) {
        Integer posIniX = offset + i * (size + gap);
        return posIniX;
    }

    public Integer posIniY(int j) {
        Integer posIniY = offset + j * (size + gap);
        return posIniY;

    }

    public PriorityQueue<Node> pqn(int tipo) {
        PriorityQueue<Node> coladenodos = new PriorityQueue<>((a, b) -> {
            return mapa.get(a).get(tipo).compareTo(mapa.get(b).get(tipo));
        });
        return coladenodos;

    }

    public void logicaDeColumnas(Button btn, Persona per) {
        btn.setOnMouseClicked((t) -> {
            if (per.getCambiosDisponibles() == per.getCambiosRealizados() && per.getCambiosDisponibles() > 0) {
                per.setCambiosDisponibles();
            }

        });
    }

    public PriorityQueue<Node> logicaCreaListasCirculares(Pane p, int dir, int bus) {
        PriorityQueue<Node> nodosordenados = pqn(bus);
        Set<Node> keys = mapa.keySet();
        for (Node nd : keys) {
            if (mapa.get(nd).get(dir).equals(mapa.get(p).get(dir))) {
                nodosordenados.add(nd);
            }
        }
        return nodosordenados;
    }

    public void logicadeanillo(int celdas, boolean bole, int coorde, int pos) {
        if (bole) {
            listacircular.movehead(-1);
        } else {
            listacircular.movehead(1);
        }
        for (int n = 0; n < celdas; n++) {
            Node nodoVis = listacircular.get(n);
            mapa.get(nodoVis).set(pos, posIniX(n));
            mapa.get(nodoVis).set(coorde, n);
        }

    }

    public Pane creacionPane(char letra, int i, int j) {
        Pane p = new Pane();
        p.setMinSize(size, size);
        p.setOpacity(2);
        p.setStyle("-fx-border-color: white;");
        //Label l = new Label(String.valueOf(i) + String.valueOf(j));
        Label l = new Label(String.valueOf(letra));
        l.setTextFill(Color.WHITE);
        p.setLayoutX(posIniX(i));
        p.setLayoutY(posIniY(j));
        l.setLayoutX(size - 30);
        l.setLayoutY(size - 35);
        l.setTextAlignment(TextAlignment.CENTER);
        p.getChildren().add(l);
        return p;
    }
    public void eventoPreseed(Pane p, Persona person) {
        p.setOnMousePressed((MouseEvent t) -> {
            listacircular.clear();
            PriorityQueue<Node> nodosordenados;
            if (rbn.isSelected()) {
                insertandoPanes(person, p, 0, djn, false);
                nodosordenados = logicaCreaListasCirculares(p, 0, 1);
            } else {

                insertandoPanes(person, p, 1, din, true);
                nodosordenados = logicaCreaListasCirculares(p, 1, 0);
            }
            while (!nodosordenados.isEmpty()) {
                Node nd = nodosordenados.poll();
                listacircular.addLast(nd);
            }
        }
        );
    }

    public void insertandoPanes(Persona person, Pane p, int b, int dn, boolean t) {
        if (person.getCambiosDisponibles() < person.getCambiosRealizados()) {
            Set<Node> keys = mapa.keySet();
            generaEspacio(keys, p, b);
            for (Node nd : keys) {
                if (mapa.get(p).get(b) < mapa.get(nd).get(b)) {
                    redireccionarPanes(nd, t);
                }
            }
            for (int i = 0; i < dn; i++) {
                if (t) {
                    logistica(new Random(), i, mapa.get(p).get(b) + 1, person);
                } else {
                    logistica(new Random(), mapa.get(p).get(b) + 1, i, person);
                }
            }
            if (t) {
                djn++;
            } else {
                din++;
            }
            person.setCambiosRealizados();
        }
    }

    public void redireccionarPanes(Node nd, boolean b) {
        if (b) {
            nd.setLayoutY(posIniY(mapa.get(nd).get(1)));
            mapa.get(nd).set(3, posIniY(mapa.get(nd).get(1)));
        } else {
            nd.setLayoutX(posIniX(mapa.get(nd).get(0)));
            mapa.get(nd).set(2, posIniX(mapa.get(nd).get(0)));
        }
    }

    public void generaEspacio(Set<Node> keys, Pane p, int log) {

        for (Node nd : keys) {
            if (mapa.get(p).get(log) < mapa.get(nd).get(log)) {
                mapa.get(nd).set(log, mapa.get(nd).get(log) + 1);
            }
        }
    }

    public void eventoreleased(Pane p) {
        p.setOnMouseReleased((MouseEvent t) -> {

            if (rbn.isSelected()) {
                for (int n = 0; n < djn; n++) {
                    Node nd = listacircular.get(n);
                    nd.setLayoutY(posIniY(n));
                }
            } else {

                for (int n = 0; n < din; n++) {
                    Node nd = listacircular.get(n);
                    nd.setLayoutX(posIniX(n));

                }
            }
        });
    }

    public void eventoDragged(Pane p) {
        p.setOnMouseDragged((MouseEvent t) -> {
            if (rbn.isSelected()) {
                for (int k = 0; k < djn; k++) {
                    Node nd = listacircular.get(k);
                    nd.setLayoutY(-20 + mapa.get(nd).get(3) + (t.getSceneY() - mapa.get(p).get(3)));
                    if (Math.abs(mapa.get(p).get(3) - Math.abs(p.getLayoutY())) > 60) {
                        boolean bole = (mapa.get(p).get(3) - Math.abs(p.getLayoutY()) < 0);
                        logicadeanillo(djn, bole, 1, 3);
                    }
                }
            } else {
                for (int k = 0; k < din; k++) {
                    Node nd = listacircular.get(k);
                    nd.setLayoutX(-20 + mapa.get(nd).get(2) + (t.getSceneX() - mapa.get(p).get(2)));
                    if (Math.abs(mapa.get(p).get(2) - Math.abs(p.getLayoutX())) > 60) {
                        boolean bole = (mapa.get(p).get(2) - Math.abs(p.getLayoutX()) < 0);
                        logicadeanillo(din, bole, 0, 2);
                    }
                }
            }
        });
    }

    public void eventomouseisup(Pane p) {
        p.setOnMouseMoved((MouseEvent e) -> {

            if (e.isDragDetect()) {
                p.setStyle(" -fx-border-color: white; -fx-background-color: rgba(70,70,70,0.5)");
            }

        });

    }

    public void eventoMouseIsDown(Pane p) {
        p.setOnMouseExited((MouseEvent d) -> {
            p.setStyle(" -fx-border-color: white; -fx-background-color: rgba(70,70,70,0)");
        });
    }

}
