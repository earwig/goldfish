package edu.stuy.goldfish;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import java.util.Random;

import javax.swing.JFrame;

import edu.stuy.goldfish.rules.*;

public class Render extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static int width;
	public static int height;
	public static int scale = 2;
	
	public String title = "Title";

	private Thread thread;
	private JFrame frame;
	private boolean running = false;

	private BufferedImage image;
	private Grid _grid;
	private int[] pixels;
	
	Random random = new Random();

	public Render(int width, int height) {
		Render.width = width;
		Render.height = height;
		_grid = new Grid(width, height);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		Dimension size = new Dimension(width*scale, height*scale);
		setPreferredSize(size);
		frame = new JFrame();
	}
	
	public Render() {
		width = 320;
		height = 240;
		_grid = new Grid(10, 10);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		Dimension size = new Dimension(width*scale, height*scale);
		setPreferredSize(size);
		frame = new JFrame();
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	long lastTime;
	long timer;
	double delta;
	int frames;
	int updates;
	long now;
	public void run() {
		lastTime = System.nanoTime();
		timer = System.currentTimeMillis();
		delta = 0;
		frames = 0;
		updates = 0;
		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) * 6.0 / 100000000.0;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
				render();
				frames++;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + " | updates: " + updates + " | frames: "+ frames);
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}

	public void update() {
		//TODO: make it run.
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) { 
//				_grid.getPatch(i,j).setState(1);
				 draw(i,j,random.nextInt());
//				//draw(i,j,_grid.getPatch(i,j).getState());
			}
		}
//		System.out.println(_grid);
//		_grid = Conway.run(_grid);
//		System.out.println("------------");
//		System.out.println(_grid);
	}

	BufferStrategy bs;
	Graphics g;
	public void render() {
		bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
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
	
	public void draw(int x, int y, int color) {
		pixels[x + y * width] = color;
	}
	
	public static void main(String[] args) {
		Render render = new Render(320,240);
		render.frame.setResizable(false);
		render.frame.setTitle(render.title);
		render.frame.add(render);
		render.frame.pack();
		render.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		render.frame.setLocationRelativeTo(null);
		render.frame.setVisible(true);
		render.start();
	}
}
