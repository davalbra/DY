/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Estructuras.ListaCircularDE;
import Modelo.Player;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
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
import javafx.scene.control.TextField;
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
    private TextField txp;
    @FXML
    private AnchorPane root1;
    @FXML
    private RadioButton rbn;
    @FXML
    private Button btnAC;
    @FXML
    private VBox vBoxPalabra;
    @FXML
    private Button btnvp;
    @FXML
    private VBox vBoxScore;
    @FXML
    private Button btnE;

    static LinkedHashMap<Node, ArrayList<Integer>> mapa = new LinkedHashMap<>();
    static int din = 7;
    static int djn = 6;
    static ListaCircularDE<Node> listacircular = new ListaCircularDE<Node>();
    static int offset = 100;
    static int gap = 10;
    static int size = 50;
    static ListaCircularDE<Node> palabras = new ListaCircularDE<Node>();
    static boolean elimnar = false;
    static ListaCircularDE<Node> NodosPalabras = new ListaCircularDE<Node>();
    static int puntaje = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        controller controlador1=new controller();
//        din=controlador1.obtenerColumna();
//        
//        djn=controlador1.obtenerFila();
        
        txp.setText("0");
        Player person = new Player();
        logicaDeColumnas(btnAC, person);
        elimnarDimension(btnE);

        //root.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(48,117,82),rgb(69,168,117), rgb(100,245,170));");
        root.setStyle("-fx-background-color: linear-gradient(to bottom, rgb(11,75,217), rgb(129,12,248));");

        Random r = new Random();
        Label lb = new Label("ca");
        lb.setTextFill(Color.WHITE);
        vBoxPalabra.getChildren().add(lb);
        lb = new Label("re");
        lb.setTextFill(Color.WHITE);
        vBoxPalabra.getChildren().add(lb);
        lb = new Label("de");
        lb.setTextFill(Color.WHITE);
        vBoxPalabra.getChildren().add(lb);
        lb = new Label("pe");
        lb.setTextFill(Color.WHITE);
        vBoxPalabra.getChildren().add(lb);

        for (int i = 0; i < din; i++) {
            for (int j = 0; j < djn; j++) {
                logistica(r, i, j, person);
            }
        }

        //
    }

    public void logistica(Random r, int i, int j, Player person) {
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
        validarPalabra(btnvp);
        root1.getChildren().add(p);
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

    public void logicaDeColumnas(Button btn, Player per) {
        btn.setOnMouseClicked((t) -> {
            if (per.getCambiosDisponibles() == per.getCambiosRealizados() && per.getCambiosDisponibles() > 0) {
                per.setCambiosDisponibles();
            }
            if (per.getCambiosDisponibles() == 0) {
                btn.setDisable(true);
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

    public VBox creacionScore() {
        Player p1 = new Player();
        Label labelScore = new Label("Puntaje");
        Label puntaje = new Label(String.valueOf(p1.getPuntaje()));
        vBoxScore.getChildren().add(labelScore);
        return vBoxScore;
    }

    public void eventoPreseed(Pane p, Player person) {
        p.setOnMousePressed((MouseEvent t) -> {
            mapa.get(p).set(4, 0);
            p.setStyle(" -fx-border-color: white; -fx-background-color: #F5293B");
            palabras.addLast(p);
            listacircular.clear();
            PriorityQueue<Node> nodosordenados = new PriorityQueue<>();
            if (rbn.isSelected()) {
                if (elimnar) {
                    eliminarPanes(person, p, 0, false);
                    elimnar = false;
                } else {
                    insertandoPanes(person, p, 0, djn, false);
                    nodosordenados = logicaCreaListasCirculares(p, 0, 1);
                }

            } else {
                if (elimnar) {
                    eliminarPanes(person, p, 1, true);
                    elimnar = false;
                } else {
                    insertandoPanes(person, p, 1, din, true);
                    nodosordenados = logicaCreaListasCirculares(p, 1, 0);
                }
            }

            while (!nodosordenados.isEmpty()) {
                Node nd = nodosordenados.poll();
                listacircular.addLast(nd);
            }
        }
        );
    }

    public void eliminarPanes(Player person, Pane p, int log, boolean t) {
        Set<Node> keys = mapa.keySet();
        if (person.getCambiosDisponibles() != 0) {
            int dimen = mapa.get(p).get(log);
            Queue<Node> nodos = new LinkedList<>();
            for (Node nd : keys) {
                if (dimen == mapa.get(nd).get(log)) {
                    nodos.add(nd);
                }
            }
            while (!nodos.isEmpty()) {
                Node n = nodos.poll();
                root1.getChildren().remove(n);
                mapa.remove(n);
            }
            if (t) {
                djn--;
            } else {
                din--;
            }

            for (Node nd : keys) {
                if (dimen < mapa.get(nd).get(log)) {
                    mapa.get(nd).set(log, mapa.get(nd).get(log) - 1);
                }
            }
            for (Node nd : keys) {
                if (dimen <= mapa.get(nd).get(log)) {
                    redireccionarPanes(nd, t);
                }
            }
        }
        person.setCambiosDisponibles();
        person.setCambiosRealizados();
        if (person.getCambiosDisponibles() == 0) {
            btnE.setDisable(true);
        }

    }

    public void elimnarDimension(Button btnE) {
        btnE.setOnMouseClicked((MouseEvent e) -> {
            elimnar = true;
        });
    }

    public void insertandoPanes(Player person, Pane p, int b, int dn, boolean t) {
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
            if (!listacircular.isEmpty()) {
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
            }
        });
    }

    public void eventoDragged(Pane p) {
        p.setOnMouseDragged((MouseEvent t) -> {
            if (!palabras.isEmpty()) {
                System.out.println("nodos en palabra: " + palabras.size() + " nodos en NodosPalabras: " + NodosPalabras.size());
                for (int i = 0; i < palabras.size(); i++) {
                    for (int j = 0; j < NodosPalabras.size(); j++) {
                        if (palabras.get(i) == NodosPalabras.get(j)) {
                            palabras.remove(i);
                        }
                    }
                }
                ListIterator<Node> it = palabras.listIterator();
                int contador = 0;
                while (contador < palabras.size() && it.hasNext()) {
                    Node nex = it.next();
                    mapa.get(nex).set(4, 1);

                    nex.setStyle(" -fx-border-color: white; -fx-background-color: rgba(70,70,70,0)");
                    contador++;
                }
            }
            palabras.clear();
            if (!listacircular.isEmpty()) {
                if (rbn.isSelected()) {
                    boolean permitir = true;
                    for (int n = 0; n < NodosPalabras.size(); n++) {
                        if (Objects.equals(mapa.get(p).get(0), mapa.get(NodosPalabras.get(n)).get(0))) {
                            permitir = false;
                        }
                    }
                    if (permitir) {
                        for (int k = 0; k < djn; k++) {
                            Node nd = listacircular.get(k);
                            nd.setLayoutY(-20 + mapa.get(nd).get(3) + (t.getSceneY() - mapa.get(p).get(3)));
                            if (Math.abs(mapa.get(p).get(3) - Math.abs(p.getLayoutY())) > 60) {
                                boolean bole = (mapa.get(p).get(3) - Math.abs(p.getLayoutY()) < 0);
                                logicadeanillo(djn, bole, 1, 3);
                            }
                        }
                    }
                } else {
                    boolean permitir = true;
                    for (int n = 0; n < NodosPalabras.size(); n++) {
                        if (Objects.equals(mapa.get(p).get(1), mapa.get(NodosPalabras.get(n)).get(1))) {
                            permitir = false;
                        }
                    }
                    if (permitir) {
                        for (int k = 0; k < din; k++) {

                            Node nd = listacircular.get(k);
                            nd.setLayoutX(-20 + mapa.get(nd).get(2) + (t.getSceneX() - mapa.get(p).get(2)));
                            if (Math.abs(mapa.get(p).get(2) - Math.abs(p.getLayoutX())) > 60) {
                                boolean bole = (mapa.get(p).get(2) - Math.abs(p.getLayoutX()) < 0);
                                logicadeanillo(din, bole, 0, 2);
                            }
                        }
                    }
                }
            }
        });
    }

    public void eventomouseisup(Pane p) {
        p.setOnMouseMoved((MouseEvent e) -> {

            boolean bandera = true;
            for (int i = 0; i < NodosPalabras.size(); i++) {
                if (p == NodosPalabras.get(i)) {
                    bandera = false;
                }
            }
            if (bandera && e.isDragDetect() && mapa.get(p).get(4) == 1) {
                p.setStyle(" -fx-border-color: white; -fx-background-color: rgba(70,70,70,0.5)");
            }

        });

    }

    public void eventoMouseIsDown(Pane p) {
        p.setOnMouseExited((MouseEvent d) -> {
            boolean bandera = true;
            for (int i = 0; i < NodosPalabras.size(); i++) {
                if (p == NodosPalabras.get(i)) {
                    bandera = false;
                }
            }
            if (bandera && mapa.get(p).get(4) == 1) {
                p.setStyle(" -fx-border-color: white; -fx-background-color: rgba(70,70,70,0)");
            }
        });
    }

    public void validarPalabra(Button btnvp) {
        btnvp.setOnMouseClicked((MouseEvent m) -> {
            boolean di = true;
            ListIterator<Node> it = palabras.listIterator();
            int contador = 1;
            while (contador < palabras.size() && it.hasNext()) {
                int i1 = mapa.get(it.next()).get(0);
                int i2 = mapa.get(it.next()).get(0);

                it.previous();
                int j2 = mapa.get(it.previous()).get(1);
                int j1 = mapa.get(it.previous()).get(1);
                if (!(calculo(i1, i2) && calculo(j1, j2))) {
                    di = false;
                }
                it.next();
                it.next();
                contador++;
            }
            if (di) {
                System.out.println("si soy");
                ListIterator<Node> dt = palabras.listIterator();
                String pal = "";
                int cont = 0;
                while (cont < palabras.size() && dt.hasNext()) {
                    Node det = dt.next();
                    System.out.println("d");
                    Pane pt = (Pane) det;
                    Label ld = (Label) pt.getChildren().get(0);
                    pal += ld.getText();
                    NodosPalabras.addLast(det);
                    cont++;
                    puntaje++;
                }
                txp.setText(puntaje+"");

            } else {
                System.out.println("no soy");
            }
        }
        );

    }

    public boolean calculo(int a, int b) {
        if (Math.abs(a - b) < 2) {
            return true;
        } else {
            return false;
        }

    }
}
