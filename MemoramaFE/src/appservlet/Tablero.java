
package appservlet;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 *
 * @author Emiliano Cepeda Villarreal N-20130792
 */
public class Tablero extends JPanel {

    private String[] pers = {"Alm", "Azura", "Dorcas", "Hector", "Legion", "Setsuna", "Valter", "Zephiel"};

    private int fila = 4;
    private int col = 4;
    private int ancho_casilla = 140;
    public boolean play = false;

    int c = 0;
    Casilla c1;
    Casilla c2;
    int aciertos = 0;

    public Tablero() {
        super();
        //propiedades
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setLayout(new java.awt.GridLayout(fila, col));
        Dimension d = new Dimension((ancho_casilla * col), (ancho_casilla * fila));
        setSize(d);
        setPreferredSize(d);
        //crea instancias de casillas para crear el tablero 
        int count = 0;
        for (int i = 1; i <= (fila * col); i++) {
            Casilla p = new Casilla(String.valueOf(i));
            p.setPersonaje(pers[count]);
            count++;
            count = (count >= pers.length) ? 0 : count++;
            p.showPersonaje();
            p.addMouseListener(new juegoMouseListener());
            this.add(p);
        }
        setVisible(true);
    }

    public void comenzarJuego() {
        aciertos = 0;
        play = true;
        Component[] componentes = this.getComponents();
        //limpia banderas
        for (int i = 0; i < componentes.length; i++) {
            ((Casilla) componentes[i]).congelarImagen(false);
            ((Casilla) componentes[i]).ocultarPersonaje();
            ((Casilla) componentes[i]).setPersonaje("");
        }
        //coloca nuevo orden aleatorio de banderas
        for (int i = 0; i < componentes.length; i++) {
            int n = (int) (Math.random() * (pers.length));
            if (!existe(pers[n])) {//comprueba que bandera no este asignada mas de 2 veces 
                ((Casilla) componentes[i]).setPersonaje(pers[n]);
            } else {
                i--;
            }
        }

    }

    private boolean existe(String personaje) {
        int count = 0;
        Component[] componentes = this.getComponents();
        for (int i = 0; i < componentes.length; i++) {
            if (componentes[i] instanceof Casilla) {
                if (((Casilla) componentes[i]).getNamePersonaje().equals(personaje)) {
                    count++;
                }
            }
        }
        return (count == 2) ? true : false;
    }

    class juegoMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            if (play) {
                c++;//lleva la cuenta de los click realizados en las casillas 
                if (c == 1) { //primer click
                    c1 = ((Casilla) e.getSource()); //obtiene objeto
                    if (!c1.isCongelado()) {
                        c1.showPersonaje();
                        System.out.println("Primera Bandera: " + c1.getNamePersonaje());
                    } else {//no toma en cuenta
                        c = 0;
                    }
                } else if (c == 2 && !c1.getName().equals(((Casilla) e.getSource()).getName())) {//segundo click
                    c2 = ((Casilla) e.getSource());
                    if (!c2.isCongelado()) {
                        c2.showPersonaje();
                        System.out.println("Segunda Bandera: " + c2.getNamePersonaje());
                        //compara imagenes
                        Animacion ani = new Animacion(c1, c2);
                        ani.execute();
                    }
                    c = 0;//contador de click a 0
                } else { //mas de 2 clic consecutivos no toma en cuenta
                    c = 0;
                }
            } else {
                System.out.println("Para jugar: FILE -> JUGAR");
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }
    
    class Animacion extends SwingWorker<Void, Void>{
         private Casilla casilla1;
         private Casilla casilla2;
         
         public Animacion(Casilla value1, Casilla value2){
             this.casilla1= value1;
             this.casilla2= value2;
         }
         
         @Override
         protected Void doInBackground() throws Exception {
             System.out.println("doInBackground: Procesando...");            
             //espera 1 segundo 
             Thread.sleep( 1000 );                
             if( casilla1.getNamePersonaje().equals( casilla2.getNamePersonaje() ) ){//son iguales
                 casilla1.congelarImagen(true);
                 casilla2.congelarImagen(true);
                 aciertos++;
                 if( aciertos == 8 ){//win  
                     JOptionPane.showMessageDialog(null,"FELICIDADES\nUsted Gano!");
                 }
             }            
             else{//no son iguales
                 casilla1.ocultarPersonaje();
                 casilla2.ocultarPersonaje(); 
             }
             return null;
         }
     
     }

}
