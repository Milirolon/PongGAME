import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong extends JFrame implements ActionListener, KeyListener {
    private int ancho = 800;
    private int alto = 600;
    private int anchoPaleta = 100;
    private int altoPaleta = 20;
    private int tamañoBola = 20;
    private int velocidadPaleta = 10;
    private int velocidadBolaX = 5;
    private int velocidadBolaY = 5;
    private int posicionJugadorX = ancho / 2 - anchoPaleta / 2;
    private int posicionJugadorY = alto - altoPaleta - 20;
    private int posicionBolaX = ancho / 2 - tamañoBola / 2;
    private int posicionBolaY = alto / 2 - tamañoBola / 2;
    private int vidas = 3;

    public Pong() {
        setTitle("Juego Pong");
        setSize(ancho, alto);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Timer timer = new Timer(25, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, ancho, alto);

        g.setColor(Color.WHITE);
        g.fillRect(posicionJugadorX, posicionJugadorY, anchoPaleta, altoPaleta);
        g.fillOval(posicionBolaX, posicionBolaY, tamañoBola, tamañoBola);

        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("Vidas: " + vidas, ancho / 2 - 30, alto - 500);
    }

    public void actionPerformed(ActionEvent e) {
        posicionBolaX += velocidadBolaX;
        posicionBolaY += velocidadBolaY;

        // Rebote en los bordes laterales
        if (posicionBolaX <= 0 || posicionBolaX >= ancho - tamañoBola) {
            velocidadBolaX = -velocidadBolaX;
        }

        // Rebote en la parte superior
        if (posicionBolaY <= 0) {
            velocidadBolaY = -velocidadBolaY;
        }

        // Colisión con la paleta del jugador
        if (posicionBolaY + tamañoBola >= posicionJugadorY &&
            posicionBolaX + tamañoBola >= posicionJugadorX &&
            posicionBolaX <= posicionJugadorX + anchoPaleta) {
            velocidadBolaY = -velocidadBolaY;
        }

        // Colisión con la parte inferior
        if (posicionBolaY >= alto - tamañoBola) {
            vidas--; // El jugador pierde una vida cuando la bola toca la parte inferior
            if (vidas <= 0) {
                JOptionPane.showMessageDialog(this, "Perdiste!");
                System.exit(0);
            } else {
                // Reubicar la bola al centro si hay vidas restantes
                posicionBolaX = ancho / 2 - tamañoBola / 2;
                posicionBolaY = alto / 2 - tamañoBola / 2;
            }
        }

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
        Pong game = new Pong();
        game.setVisible(true);
    }
}
