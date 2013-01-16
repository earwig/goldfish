package edu.stuy.goldfish;

import java.util.Date;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Render extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static String title = "Goldfish";
	public static int width;
	public static int height;
	public static int scale;
	public static int max_fps = 15;

	private JFrame _frame;
	private BufferedImage _image;
	private Grid _grid;
	private int[] _pixels;
	private long _last_tick;

	public Render(int width, int height, Grid g) {
		Render.width = width;
		Render.height = height;
		setScale();
		_grid = g;
		_image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		_pixels = ((DataBufferInt) _image.getRaster().getDataBuffer()).getData();
		_last_tick = 0;
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		setFrame();
	}

	public Render(int width, int height) {
		this(width, height, new Grid(width, height));
	}

	public Render() {
		this(256, 256);
	}

	private void setScale() {
		if (height <= 128 || width <= 128) {
			Render.scale = 4;
		} else if (height <= 256 || width <= 256) {
			Render.scale = 2;
		}
	}

	private void setFrame() {
		_frame = new JFrame();
		_frame.setResizable(false);
		_frame.setTitle(title);
		_frame.add(this);
		_frame.pack();
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
	}

	private void clear() {
		for (int x = 0; x < _pixels.length; x++) {
			_pixels[x] = 0;
		}
	}

	private void update() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				draw(i, j, _grid.getPatch(i, j).getState() * 0xffffff);
			}
		}
	}

	public void run() {
		BufferStrategy bs;
		Graphics g;

		bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(1);
			return;
		}

		clear();
		update();

		g = bs.getDrawGraphics();
		g.drawImage(_image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public void sleep() {
		long since = (new Date()).getTime() - _last_tick;
		if (since < 1000 / max_fps) {
			try {
				Thread.sleep(1000 / max_fps - since);
			}
			catch (InterruptedException e) {
				return;
			}
		}
		_last_tick = (new Date()).getTime();
	}

	public void setGrid(Grid g) {
		_grid = g;
	}

	public void draw(int x, int y, int color) {
		_pixels[x + y * width] = color;
	}
}
