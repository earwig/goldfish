package edu.stuy.goldfish;

import edu.stuy.goldfish.rules.*;

public class Goldfish {
    private Grid _grid;
    private Render _render;

    public Goldfish() {
        int width = 128;
        int height = 128;
        _grid = new Grid(width, height);
        _render = new Render(width, height, _grid);
    }

    public void run() {
        for (int i = 0; i < _grid.getWidth(); i += 16) {
            for (int j = 0; j < _grid.getHeight(); j += 16) {
                _grid.getPatch(i + 1, j + 0).setState(1);
                _grid.getPatch(i + 2, j + 1).setState(1);
                _grid.getPatch(i + 2, j + 2).setState(1);
                _grid.getPatch(i + 1, j + 2).setState(1);
                _grid.getPatch(i + 0, j + 2).setState(1);
            }
        }

        while (true) {
            _grid = Conway.run(_grid);
            _render.setGrid(_grid);
            _render.run();
            _render.sleep();
        }
    }

    public static void main(String[] args) {
        Goldfish g = new Goldfish();
        g.run();
    }
}
