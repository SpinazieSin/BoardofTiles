package drawing;

import java.awt.*;
import javax.swing.*;


public class Main extends JPanel {
    private static final long serialVersionUID = 1L;
    private final int WIDTH = 900;
    private final int HEIGHT = 900;
    private boolean secondColor = true;
    private int id = 0;
    private int xCor = 4;
    private int yCor = 8;

    private Font font = new Font("Arial", Font.BOLD, 18);
    FontMetrics metrics;

    public Main() {
         setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public Main(JFrame f) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //JFrame f = new JFrame();
        //Main p = new Main();

        f.setContentPane(this);
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Point origin = new Point(WIDTH / 2, HEIGHT / 2);
        Point newOrigin = new Point((WIDTH / 2) + 5, (HEIGHT / 2) - 5);

        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        g2d.setFont(font);
        metrics = g.getFontMetrics();

        // drawCircle(g2d, origin, 800, true, true, 0x000000, 0);
        // drawHexGridLoop(g2d, origin, 9, 50, 4);
        // drawHex(g2d, 200, 200, 10);
        // drawHex(g2d, 220, 200, 10);
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
                int xLbl = row < half ? col - row : col - half;
                int yLbl = row - half;
                int x = (int) (origin.x + xOff * (col * 2 + 1 - cols));
                int y = (int) (origin.y + yOff * (row - half) * 3);

                drawHex(g, x, y, radius);

                id++;
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
        int w = metrics.stringWidth(text);
        int h = metrics.getHeight();
        if(!secondColor){
            hex.draw(g2d, x, y, 0, 0x00CC66, true);
            hex.draw(g2d, x, y, 4, 0x00994C, false);
        } else {
            hex.draw(g2d, x, y, 0, 0x808080, true);
            hex.draw(g2d, x, y, 4, 0x808080, false);
        }

        g.setColor(new Color(0xFFFFFF));
        g.drawString(text, x - w/2, y + h/2);
    }

    private String coord(int value) {
        return (value > 0 ? "+" : "") + Integer.toString(value);
    }

    public void drawCircle(Graphics2D g, Point origin, int radius,
            boolean centered, boolean filled, int colorValue, int lineThickness) {
        // Store before changing.
        Stroke tmpS = g.getStroke();
        Color tmpC = g.getColor();

        g.setColor(new Color(colorValue));
        g.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));

        int diameter = radius * 2;
        int x2 = centered ? origin.x - radius : origin.x;
        int y2 = centered ? origin.y - radius : origin.y;

        if (filled)
            g.fillOval(x2, y2, diameter, diameter);
        else
            g.drawOval(x2, y2, diameter, diameter);

        // Set values to previous when done.
        g.setColor(tmpC);
        g.setStroke(tmpS);
    }

    public static void main(String[] args) {
        new Main(new JFrame() );
    }
}