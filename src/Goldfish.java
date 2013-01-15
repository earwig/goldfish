package edu.stuy.goldfish;


import edu.stuy.goldfish.rules.*;

public class Goldfish {

    private Grid _grid;
    private Render _render;

    public Goldfish () {
        _render = new Render(640, 480);
        _grid = new Grid(10, 10);
    }

    public void run () {
        //TODO: make it run.
        _grid.getPatch(0,1).setState(1);
        _grid.getPatch(1,2).setState(1);
        _grid.getPatch(2,2).setState(1);
        _grid.getPatch(2,1).setState(1);
        _grid.getPatch(2,0).setState(1);
        System.out.println(_grid);

        _grid = Conway.run(_grid);

        _grid = Conway.run(_grid);
        
        _grid = Conway.run(_grid);

        _grid = Conway.run(_grid);
    }

    public static void main (String[] args) {
        Goldfish g = new Goldfish();
        g.run();
    }
}
