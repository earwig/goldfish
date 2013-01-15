package edu.stuy.goldfish;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Render extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static int width;
	public static int height;
	public static int scale;

	public String title = "Goldfish";

	private JFrame frame;

	private BufferedImage image;
	private Grid _grid;
	private int[] pixels;

	public Render(int width, int height, Grid g) {
		Render.width = width;
		Render.height = height;
		setScale();
		_grid = g;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		setFrame();
	}

	public Render(int width, int height) {
		Render.width = width;
		Render.height = height;
		setScale();
		_grid = new Grid(width, height);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		setFrame();
	}

	public Render() {
		width = 320;
		height = 240;
		setScale();
		_grid = new Grid(10, 10);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		frame = new JFrame();
	}

	private void setScale() {
		if (height < 128 || width < 128) {
			Render.scale = 3;
		} else if (height < 256 || width < 256) {
			Render.scale = 2;
		}
	}

	private void setFrame() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle(title);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void run() {
		render();
	}

	public void update() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				draw(i, j, _grid.getPatch(i, j).getState() * 0xffffff);
			}
		}
	}

	BufferStrategy bs;
	Graphics g;

	public void render() {
		bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(1);
			return;
		}

		clear();
		update();

		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public void clear() {
		for (int x = 0; x < pixels.length; x++) {
			pixels[x] = 0;
		}
	}

	public void setGrid(Grid g) {
		_grid = g;
	}

	public void draw(int x, int y, int color) {
		pixels[x + y * width] = color;
	}

	public static void main(String[] args) {
		Render render = new Render(320, 240);
		while (true) {
			render.run();
		}
	}
}
