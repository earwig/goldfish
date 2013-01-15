package edu.stuy.goldfish;

import edu.stuy.goldfish.rules.*;

public class Goldfish {

    private Grid _grid;
    private Render _render;

    public Goldfish() {
    	int height = 96;
    	int width = 96;
        _render = new Render(width, height);
        _grid = new Grid(width, height);
    }

    public void run() {
        _grid.getPatch(1,0).setState(1);
        _grid.getPatch(2,1).setState(1);
        _grid.getPatch(2,2).setState(1);
        _grid.getPatch(1,2).setState(1);
        _grid.getPatch(0,2).setState(1);

        while (true) {
            _grid = Conway.run(_grid);
            _render.setGrid(_grid);
            _render.run();
        }
   }

    public static void main (String[] args) {
        Goldfish g = new Goldfish();
        g.run();
    }
}
