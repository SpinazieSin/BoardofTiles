package com.zetcode;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

class Drawing2 extends JPanel {

	BufferedImage image1;
	BufferedImage image2;
	BufferedImage image3;
	TexturePaint image1p;
	TexturePaint image2p;
	TexturePaint image3p;

	public Drawing2() {
		loadImages();
	}

	private void loadImages() {

		try {

			image1 = ImageIO.read(this.getClass().getResource("hex.png"));
			image2 = ImageIO.read(this.getClass().getResource("Swordsman.png"));
			image3 = ImageIO.read(this.getClass().getResource("Goblin.png"));
		
		} catch (IOException e) {
			Logger.getLogger(Sprites.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);
	}
	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		image1p = new TexturePaint(image1, new Rectangle(0,0,90,60));
		image2p = new TexturePaint(image2, new Rectangle(0,0,90,60));
		image3p = new TexturePaint(image3, new Rectangle(0,0,90,60));

		g2d.setPaint(image1p);
		g2d.fillRect(10,15,90,60);
		g2d.setPaint(image2p);
		g2d.fillRect(130,15,90,60);
		g2d.setPaint(image3p);
		g2d.fillRect(250,15,90,60);
	}
}

public class Sprites extends JFrame {

	public Sprites() {
		initUI();
	}

	public final void initUI() {
		Drawing2 dpnl = new Drawing2();
		add(dpnl);

		setSize(500,500);
		setTitle("Sprites");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Sprites ex = new Sprites();
				ex.setVisible(true);
			}
		});
	}
}