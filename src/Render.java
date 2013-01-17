package edu.stuy.goldfish;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

public class Render extends Canvas implements Runnable, MouseListener,
        MouseMotionListener, ActionListener, ItemListener {
    private static final long serialVersionUID = 1L;

    public static String title = "Goldfish";
    public static int width;
    public static int height;
    public static int scale;
    public static int max_fps = 15;

    public boolean paused;
    public String rule;

    private Grid _grid;
    private BufferedImage _image;
    private int[] _pixels;
    private long _lastTick;
    private String[] _rules;
    private JFrame _frame;

    public Render(int width, int height, Grid g, String[] rules) {
        addMouseListener(this);
        addMouseMotionListener(this);
        Render.width = width;
        Render.height = height;
        setScale();
        paused = false;
        rule = rules[0];
        _grid = g;
        _image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        _pixels = ((DataBufferInt) _image.getRaster().getDataBuffer()).getData();
        _lastTick = 0;
        _rules = rules;
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        setFrame();
    }

    public Render(int width, int height, Grid g) {
        this(width, height, g, new String[0]);
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
        JMenuBar menuBar = new JMenuBar();
        JMenu menuMain = new JMenu("Menu");
        JMenu menuAlgo = new JMenu("Algorithms");
        for (String rule : _rules) {
            JMenuItem menuAlgoItem = new JMenuItem(rule);
            menuAlgo.add(menuAlgoItem);
            menuAlgoItem.addActionListener(this);
        }
        menuMain.add(menuAlgo);
        menuBar.add(menuMain);

        JCheckBoxMenuItem pauseButton = new JCheckBoxMenuItem("Pause");
        menuBar.add(pauseButton);
        pauseButton.addItemListener(this);

        _frame = new JFrame();
        _frame.setJMenuBar(menuBar);
        _frame.setResizable(false);
        _frame.setTitle(title);
        _frame.add(this);
        _frame.pack();
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setLocationRelativeTo(null);
        _frame.setVisible(true);
    }

    private void update() {
        int state;
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
        long since = System.currentTimeMillis() - _lastTick;
        if (since < 1000 / max_fps) {
            try {
                Thread.sleep(1000 / max_fps - since);
            } catch (InterruptedException e) {
                return;
            }
        }
        _lastTick = System.currentTimeMillis();
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

    // Affects the algorithms menu
    @Override
    public void actionPerformed(ActionEvent event) {
        rule = event.getActionCommand();
    }

    // Affects the pause button
    @Override
    public void itemStateChanged(ItemEvent event) {
        if (event.getStateChange() == ItemEvent.SELECTED)
            paused = true;
        else
            paused = false;
        return;
    }
}
