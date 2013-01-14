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
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) { 
                _grid.getPatch(i,j).setState(1);
            }
        }
        System.out.println(_grid);
        _grid = Conway.run(_grid);
        System.out.println("------------");
        System.out.println(_grid);
    }

    public static void main (String[] args) {
        Goldfish g = new Goldfish();
        g.run();
    }
}
