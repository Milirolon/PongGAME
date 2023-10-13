
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong1 extends JPanel implements ActionListener, KeyListener {
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
    private int tiempo = 0; // Contador de tiempo en segundos
    private Timer timerTiempo; // Timer para el contador de tiempo

    private ImageIcon backgroundImage; // Variable para la imagen de fondo

    public Pong1() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(ancho, alto));
        setFocusable(true);
        addKeyListener(this);

        backgroundImage = new ImageIcon(getClass().getResource("fondo.jpg"));

        Timer timer = new Timer(30, this);
        timer.start();

        // Inicializa el Timer para el contador de tiempo
        timerTiempo = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiempo++;
                repaint();
            }
        });
        timerTiempo.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar la imagen de fondo que abarca todo el panel
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);

        // Dibujar la paleta y la bola
        g.setColor(Color.ORANGE);
        g.fillRect(posicionJugadorX, posicionJugadorY, anchoPaleta, altoPaleta);
        g.setColor(Color.WHITE);
        g.fillOval(posicionBolaX, posicionBolaY, tamañoBola, tamañoBola);

        // Dibujar contador de tiempo en la esquina superior derecha
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.BOLD, 22));
        g.drawString("Tiempo: " + tiempo + " s",  20, 90);

        // Dibujar texto de vidas y puntuación
        g.setFont(new Font("Courier New", Font.BOLD, 22));
        g.drawString("Vidas: " + vidas, 20, 30);
        g.drawString("Puntuación: " + puntuacion, 20, 60);
    }

public void actionPerformed(ActionEvent e) {
    // Actualiza la posición de la pelota
    posicionBolaX += velocidadBolaX;
    posicionBolaY += velocidadBolaY;

    // Rebote en los bordes laterales
    if (posicionBolaX <= 0 || posicionBolaX >= ancho - tamañoBola) {
        velocidadBolaX = -velocidadBolaX;
    }

    // Rebote en la parte superior
    if (posicionBolaY <= 20) {
        velocidadBolaY = -velocidadBolaY;
    }

    // Pierde una vida si toca el borde inferior
    if (posicionBolaY >= alto - tamañoBola) {
        vidas--; // Pierde una vida cuando la bola toca el borde inferior
        if (vidas <= 0) {
        	
        	 timerTiempo.stop();
            JOptionPane.showMessageDialog(this, "Perdiste! Tu puntuacion fue de: "+puntuacion);
            System.exit(0);
            
        } else {
            // Reubicar la bola  si hay vidas restantes
            posicionBolaX = ancho / 5- tamañoBola / 5;
            posicionBolaY = alto / 5- tamañoBola / 5;
        }
    }

    // Colisión con la paleta del jugador
    if (posicionBolaY + tamañoBola >= posicionJugadorY &&
            posicionBolaX + tamañoBola >= posicionJugadorX &&
            posicionBolaX <= posicionJugadorX + anchoPaleta) {
        velocidadBolaY = -velocidadBolaY;
        puntuacion++;
    }

    // Redibuja la escena
    repaint();
}

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT && posicionJugadorX > 0) {
            posicionJugadorX -= velocidadPaleta;
        } else if (keyCode == KeyEvent.VK_RIGHT && posicionJugadorX < ancho - anchoPaleta) {
            posicionJugadorX += velocidadPaleta;
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Juego Pong");
            Pong1 game = new Pong1();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(game);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
