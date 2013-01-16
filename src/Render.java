package edu.stuy.goldfish;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class Render extends Canvas implements Runnable, MouseListener,
        MouseMotionListener, ActionListener, ItemListener {
    private static final long serialVersionUID = 1L;

    public static String title = "Goldfish";
    public static int width;
    public static int height;
    public static int scale;
    public static int max_fps = 15;

    public boolean paused;
    private JFrame _frame;
    private BufferedImage _image;
    private Grid _grid;
    private int[] _pixels;
    private long _last_tick;

    JMenuBar menu_bar;
    JMenu menu_main, menu_algo;
    JMenuItem menu_algo_conway;
    JCheckBoxMenuItem pause_button;

    public Render(int width, int height, Grid g) {
        addMouseListener(this);
        addMouseMotionListener(this);
        Render.width = width;
        Render.height = height;
        setScale();
        _grid = g;
        _image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        _pixels = ((DataBufferInt) _image.getRaster().getDataBuffer())
                .getData();
        _last_tick = 0;
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        setFrame();
        menu_algo_conway.addActionListener(this);
        pause_button.addActionListener(this);
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
        } else {
            Render.scale = 1;
        }
    }

    private void setFrame() {
        menu_bar = new JMenuBar();

        menu_main = new JMenu("Menu");

        menu_algo = new JMenu("Algorithms");

        menu_algo_conway = new JMenuItem("Conway");
        menu_algo.add(menu_algo_conway);

        menu_main.add(menu_algo);
        menu_bar.add(menu_main);

        pause_button = new JCheckBoxMenuItem("Pause");
        menu_bar.add(pause_button);
        ItemListener pauseListener = new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                AbstractButton pause_button = (AbstractButton) event
                        .getSource();
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    paused = true;
                } else {
                    paused = false;
                }
                return;
            }
        };
        pause_button.addItemListener(pauseListener);

        _frame = new JFrame();
        _frame.setJMenuBar(menu_bar);
        _frame.setResizable(false);
        _frame.setTitle(title);
        _frame.add(this);
        _frame.pack();
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setLocationRelativeTo(null);
        _frame.setVisible(true);
    }

    int state;
    private void update() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                state = _grid.getPatch(i, j).getState();
                if(_pixels[i + j * width] == state) {
                } else {
                    draw(i, j, state * 0xffffff);
                }
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

        update();

        g = bs.getDrawGraphics();
        g.drawImage(_image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }

    public void sleep() {
        long since = System.currentTimeMillis() - _last_tick;
        if (since < 1000 / max_fps) {
            try {
                Thread.sleep(1000 / max_fps - since);
            } catch (InterruptedException e) {
                return;
            }
        }
        _last_tick = System.currentTimeMillis();
    }

    public void setGrid(Grid g) {
        _grid = g;
    }

    public void draw(int x, int y, int color) {
        _pixels[x + y * width] = color;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getX() < 0 || e.getY() < 0 || e.getX() / scale > width || e.getY() / scale > height)
            return;
        _grid.getPatch(e.getX() / scale, e.getY() / scale).setState(1);
        e.consume();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        _grid.getPatch(e.getX() / scale, e.getY() / scale).setState(1);
        e.consume();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        _grid.getPatch(e.getX() / scale, e.getY() / scale).setState(1);
        e.consume();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void itemStateChanged(ItemEvent arg0) {
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }

}
