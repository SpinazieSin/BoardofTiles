/**
    UTF-8 was used in this file, please beware
**/
package drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
    private static final long serialVersionUID = 1L;
    private final int WIDTH = 900;
    private final int HEIGHT = 900;
    private boolean secondColor = true;
    private int xCor = 4;
    private int yCor = 8;
    private Integer[][][] data;

    private Font font = new Font("Arial", Font.BOLD, 18);
    FontMetrics metrics;

    public Main(Integer[][][] dataIn) {
        data = dataIn;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public Main(JFrame f) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        f.setContentPane(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Point newOrigin = new Point((WIDTH / 2) + 5, (HEIGHT / 2) - 5);

        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        g2d.setFont(font);
        metrics = g.getFontMetrics();

        // drawHexGridLoop(g2d, origin, 9, 50, 4);
        secondColor = false;
        drawHexGridLoop(g2d, newOrigin, 9, 50, 3);
    }

    private void drawHexGridLoop(Graphics g, Point origin, int size, int radius, int padding) {
        double ang30 = Math.toRadians(30);
        double xOff = Math.cos(ang30) * (radius + padding);
        double yOff = Math.sin(ang30) * (radius + padding);
        int half = size / 2;
        boolean makeswitch = false;
        for (int row = 0; row < size; row++) {
            int cols = size - java.lang.Math.abs(row - half);
            int prevline = xCor;

            for (int col = 0; col < cols; col++) {

                int x = (int) (origin.x + xOff * (col * 2 + 1 - cols));
                int y = (int) (origin.y + yOff * (row - half) * 3);
                drawHex(g, x, y, radius);
                if(xCor == 8) {
                    makeswitch = true;
                }
                xCor--;
            }
            if(makeswitch){
                xCor = xCor -2;
            }
            if(!makeswitch){
                xCor = prevline + 1;    
            }
            if(makeswitch){
                xCor = prevline -1;
            }
            yCor--;
        }
    }

    private void drawHex(Graphics g, int x, int y, int r) {
        Graphics2D g2d = (Graphics2D) g;

        Hexagon hex = new Hexagon(x, y, r);
        String text = String.format(xCor + ", " + yCor);
        String character = "";
        int w = metrics.stringWidth(text);
        int h = metrics.getHeight();
        BufferedImage goblin = null;
        BufferedImage swordsman = null;
        BufferedImage general = null;
        BufferedImage orc = null;
        try {
            goblin = ImageIO.read(new File("Goblin.png"));
            swordsman = ImageIO.read(new File("Swordsman.png"));
            general = ImageIO.read(new File("General.png"));
            orc = ImageIO.read(new File("Orc.png"));
        } catch (IOException e) {
        }
        if(!secondColor){
            hex.draw(g2d, x, y, 0, 0x00CC66, true);
            hex.draw(g2d, x, y, 4, 0x00994C, false);
        } else {
            hex.draw(g2d, x, y, 0, 0x808080, true);
            hex.draw(g2d, x, y, 4, 0x808080, false);
        }

        g.setColor(new Color(0x888888));
        g.drawString(text, x - w/2, y + h/2);
        g.setColor(new Color(0xffffff));
        g.drawString(character, x - w/2, y + h/2);
        if(xCor < data[xCor].length && yCor < data[yCor].length && data[xCor][yCor] != null){
            if(data[xCor][yCor][0] == 1){
                g.drawImage(swordsman, x - 40, y - 45, null);
                String health = Integer.toString(data[xCor][yCor][1]);
                g.setColor(new Color(0x9f0303));
                g.drawString("❤: " + health, x - 25, y + 30);
            }
            if(data[xCor][yCor][0] == 3) {
                g.drawImage(goblin, x - 40, y - 45, null);
                String health = Integer.toString(data[xCor][yCor][1]);
                g.setColor(new Color(0x9f0303));
                g.drawString("❤: " + health, x - 25, y + 30);
            }
            if(data[xCor][yCor][0] == 2) {
                g.drawImage(general, x - 40, y - 45, null);
                String health = Integer.toString(data[xCor][yCor][1]);
                g.setColor(new Color(0x9f0303));
                g.drawString("❤: " + health, x - 25, y + 30);
            }
            if(data[xCor][yCor][0] == 4) {
                g.drawImage(orc, x - 40, y - 45, null);
                String health = Integer.toString(data[xCor][yCor][1]);
                g.setColor(new Color(0x9f0303));
                g.drawString("❤: " + health, x - 25, y + 30);
            }
        }
    }

    public static void main(String[] args) {
        new Main(new JFrame() );
    }
}