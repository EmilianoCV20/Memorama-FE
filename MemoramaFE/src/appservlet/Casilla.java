
package appservlet;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Emiliano Cepeda Villarreal N-20130792
 */
public class Casilla extends JLabel {

    private int ancho = 140;
    private int alto = 140;
    private ImageIcon hide = new ImageIcon(getClass().getResource("/tarjetas/hide.jpg"));
    private ImageIcon Personaje;
    private String sPersonaje = "";
    private boolean congelado = false;

    public Casilla(String name) {
        super();
        Dimension d = new Dimension(ancho, alto);
        setName(name);
        setSize(d);
        setPreferredSize(d);
        setText("");
        setIcon(hide);
        setVisible(true);
        setOpaque(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void showPersonaje() {
        setIcon(Personaje);
    }

    public void ocultarPersonaje() {
        if (!congelado) {
            setIcon(hide);
        }
    }

    public void congelarImagen(boolean value) {
        this.congelado = value;
    }

    public boolean isCongelado() {
        return this.congelado;
    }
    
    public void setPersonaje( String name ){
         this.sPersonaje = name;
         if( !name.equals("") ){            
             Personaje = new ImageIcon(getClass().getResource("/tarjetas/"+name+".jpg"));        
         }        
     }
    
    public String getNamePersonaje(){
         return sPersonaje;
     }
    
}
