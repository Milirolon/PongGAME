

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong1 extends JFrame implements ActionListener, KeyListener {
    private int ancho = 800;
    private int alto = 600;
    private int anchoPaleta = 100;
    private int altoPaleta = 20;
    private int tamañoBola = 20;
    private int velocidadPaleta = 15;
    private int velocidadBolaX = 10;
    private int velocidadBolaY = 10;
    private int posicionJugadorX = ancho / 2 - anchoPaleta / 2;
    private int posicionJugadorY = alto - altoPaleta - 20;
    private int posicionBolaX = ancho / 2 - tamañoBola / 2;
    private int posicionBolaY = alto / 2 - tamañoBola / 2;
    private int vidas = 3;
    private int puntuacion = 0;
    
    private ImageIcon backgroundImage; // Variable para la imagen de fondo
    
    boolean colisionPaleta = (posicionBolaY + tamañoBola >= posicionJugadorY &&
            posicionBolaX + tamañoBola >= posicionJugadorX &&
            posicionBolaX <= posicionJugadorX + anchoPaleta);


    public Pong1() {
        setTitle("Juego Pong");
        setSize(ancho, alto);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

   
        backgroundImage = new ImageIcon(getClass().getResource("fondo.jpg"));

        Timer timer = new Timer(30, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Dibujar la imagen de fondo que abarca todo el panel
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);

        // Dibujar la paleta y la bola
        g.setColor(Color.ORANGE);
        g.fillRect(posicionJugadorX, posicionJugadorY, anchoPaleta, altoPaleta);
        g.setColor(Color.WHITE);
        g.fillOval(posicionBolaX, posicionBolaY, tamañoBola, tamañoBola);

        // Dibujar texto de vidas y puntuación
        g.setFont(new Font("Courier New", Font.BOLD, 27));
        g.drawString("Vidas: " + vidas, ancho / 2 - 380, alto - 500);
        g.drawString("Puntuación: " + puntuacion, ancho / 2 - 380, alto - 470);
    }
    
    public void actionPerformed(ActionEvent e) {
        // Rebote en los bordes laterales
        if (posicionBolaX <= 0 || posicionBolaX >= ancho - tamañoBola) {
            velocidadBolaX = -velocidadBolaX;
        }

        // Rebote en la parte superior
        if (posicionBolaY <= 20||posicionBolaY >= alto - tamañoBola) {
            velocidadBolaY = -velocidadBolaY;
        }

        // Colisión con la paleta del jugador
        if (posicionBolaY + tamañoBola >= posicionJugadorY &&
                posicionBolaX + tamañoBola >= posicionJugadorX &&
                posicionBolaX <= posicionJugadorX + anchoPaleta) {
            velocidadBolaY = -velocidadBolaY;
            puntuacion++;
        }

        // Colisión con la parte inferior
        if (posicionBolaY >= alto - tamañoBola) {
            vidas--; // El jugador pierde una vida cuando la bola toca la parte inferior
            if (vidas <= 0) {
                JOptionPane.showMessageDialog(this, "Perdiste!");
                System.exit(0);
            } else {
                // Reubicar la bola al centro si hay vidas restantes
                posicionBolaX = ancho / 4- tamañoBola / 4;
                posicionBolaY = alto / 4- tamañoBola / 4;
            }
        }
        posicionBolaX += velocidadBolaX;
        posicionBolaY += velocidadBolaY;
        repaint();
    }


    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT && posicionJugadorX > 0) {
            posicionJugadorX -= velocidadPaleta;
        } else if (keyCode == KeyEvent.VK_RIGHT && posicionJugadorX < ancho - anchoPaleta) {
            posicionJugadorX += velocidadPaleta;
        }
    }

    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        Pong1 game = new Pong1();
        game.setVisible(true);
    }
}




