package edu.stuy.goldfish;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.event.*;

public class Render extends Canvas implements Runnable, MouseListener,
        MouseMotionListener, ActionListener {
    private static final long serialVersionUID = 1L;

    public static String title;
    public static int width;
    public static int height;
    public static int scale;

    public int fps_now;
    public boolean paused;
    public String rule;
    public boolean reset;

    private AtomicBoolean[] _flags;
    private AtomicInteger _turn;
    private Grid _grid;
    private int[] _pixels;
    private BufferedImage _image;
    private long _lastTick;
    private String[] _rules;
    private JFrame _frame;
    private JButton pauseButton;
    private Random random = new Random();
    private Patch _lastPatch;

    public Render(int width, int height, Grid g, String[] rules) {
        addMouseListener(this);
        addMouseMotionListener(this);
        Render.title = "Goldfish: " + rules[0];
        Render.width = width;
        Render.height = height;
        setScale();
        paused = false;
        reset = false;
        rule = rules[0];
        _flags = new AtomicBoolean[2];
        for (int i = 0; i < _flags.length; i++)
            _flags[i] = new AtomicBoolean();
        _turn = new AtomicInteger();
        _grid = g;
        _image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        _pixels = ((DataBufferInt) _image.getRaster().getDataBuffer()).getData();
        _lastTick = 0;
        _rules = rules;
        _lastPatch = null;
        fps_now = 15;
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
        JMenu menuAlgo = new JMenu("Algorithms");
        menuAlgo.setFont(new Font("Arial", 1, 12));
        menuAlgo.setPreferredSize(new Dimension(75, 0));
        for (String rule : _rules) {
            JMenuItem menuAlgoItem = new JMenuItem(rule);
            menuAlgo.add(menuAlgoItem);
            menuAlgoItem.addActionListener(this);
        }
        menuBar.add(menuAlgo);

        pauseButton = new JButton("Pause");
        pauseButton.setActionCommand("pause");
        pauseButton.setFont(new Font("Arial", 0, 12));
        pauseButton.setPreferredSize(new Dimension(90, 0));
        menuBar.add(pauseButton);
        pauseButton.addActionListener(this);

        JButton resetButton = new JButton("Reset");
        resetButton.setActionCommand("reset");
        resetButton.setFont(new Font("Arial", 0, 12));
        menuBar.add(resetButton);
        resetButton.addActionListener(this);

        JButton randomButton = new JButton("Random");
        randomButton.setActionCommand("random");
        randomButton.setFont(new Font("Arial", 0, 12));
        menuBar.add(randomButton);
        randomButton.addActionListener(this);

        JButton clearButton = new JButton("Clear");
        clearButton.setActionCommand("clear");
        clearButton.setFont(new Font("Arial", 0, 12));
        menuBar.add(clearButton);
        clearButton.addActionListener(this);

        JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, 0, 30, 15);
        ChangeListener fpschange = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
                JSlider source = (JSlider) event.getSource();
                if (!source.getValueIsAdjusting()) {
                    int fps = (int) source.getValue();
                    if (fps == 0) {
                        paused = true;
                        pauseButton.setText("Unpause");
                    } else {
                        paused = false;
                        fps_now = fps;
                        pauseButton.setText("Pause");
                    }
                }
            }
        };
        framesPerSecond.addChangeListener(fpschange);
        framesPerSecond.setMajorTickSpacing(10);
        framesPerSecond.setMinorTickSpacing(1);
        framesPerSecond.setPaintTicks(true);
        framesPerSecond.setPaintLabels(true);
        framesPerSecond.setFont(new Font("Arial", 0, 12));
        framesPerSecond.setPreferredSize(new Dimension(100, 0));
        menuBar.add(framesPerSecond);

        menuBar.setPreferredSize(new Dimension(width * scale, 40));
        setPreferredSize(new Dimension(width * scale, height * scale));
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
        int states = Goldfish.getMaxStates(rule);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                state = _grid.getPatch(i, j).getState();
                draw(i, j, (int) ((state / ((double) states - 1)) * 0xffffff));
            }
        }
    }

    public void acquireLock(int thread) {
        int other = (thread == 0 ? 1 : 0);
        _flags[thread].set(true);
        while (_flags[other].get() == true) {
            if (_turn.get() != thread) {
                _flags[thread].set(false);
                while (_turn.get() != thread) {}
                _flags[thread].set(true);
            }
        }
    }

    public void releaseLock(int thread) {
        int other = (thread == 0 ? 1 : 0);
        _turn.set(other);
        _flags[thread].set(false);
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
        if (since < 1000 / fps_now) {
            try {
                Thread.sleep(1000 / fps_now - since);
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
        if (_pixels[x + y * width] != color)
            _pixels[x + y * width] = color;
    }

    public void clear() {
        acquireLock(1);
        for (int i = 0; i < _grid.getHeight(); i++) {
            for (int j = 0; j < _grid.getWidth(); j++) {
                _grid.getPatch(i, j).setState(0);
            }
        }
        releaseLock(1);
    }

    public void randomize() {
        acquireLock(1);
        for (int i = 0; i < _grid.getHeight(); i++) {
            for (int j = 0; j < _grid.getWidth(); j++) {
                _grid.getPatch(i, j).setState(random.nextInt(Goldfish.getMaxStates(rule)));
            }
        }
        releaseLock(1);
    }

    private void mouseDraw(MouseEvent e) {
        int states = Goldfish.getMaxStates(rule);
        if (e.getX() < 0 || e.getY() < 0 || e.getX() / scale >= width || e.getY() / scale >= height)
            return;
        Patch p = _grid.getPatch(e.getX()/scale, e.getY()/scale);
        if (_lastPatch != p) {
            if (p.getState() != 0) {
                p.setState(0);
            } else {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    p.setState(states - 1);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    p.setState(1);
                }
            }
            _lastPatch = p;
        }
        e.consume();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseDraw(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDraw(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        _lastPatch = null;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if ("pause".equals(event.getActionCommand())) {
            if (paused) {
                paused = false;
                pauseButton.setText("Pause");
            } else {
                paused = true;
                pauseButton.setText("Unpause");
            }
        } else if ("reset".equals(event.getActionCommand())) {
            clear();
            reset = true;
        } else if ("random".equals(event.getActionCommand())) {
            randomize();
        } else if ("clear".equals(event.getActionCommand())) {
            clear();
        } else {
            String oldRule = rule;
            rule = event.getActionCommand();
            if (oldRule.equals("Brian's Brain") && !rule.equals("Brian's Brain")) {
                for (int i = 0; i < _grid.getWidth(); i++) {
                    for (int j = 0; j < _grid.getHeight(); j++) {
                        if (_grid.getPatch(i,j).getState() == 2)
                            _grid.getPatch(i,j).setState(1);
                    }
                }
            } else if (!oldRule.equals("Brian's Brain") && rule.equals("Brian's Brain")) {
                for (int i = 0; i < _grid.getWidth(); i++) {
                    for (int j = 0; j < _grid.getHeight(); j++) {
                        if (_grid.getPatch(i,j).getState() == 1)
                            _grid.getPatch(i,j).setState(2);
                    }
                }
            }
            title = "Goldfish: " + rule;
            _frame.setTitle(title);
        }
    }
}
