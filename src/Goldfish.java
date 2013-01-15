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
        _grid.getPatch(1,0).setState(2);
        _grid.getPatch(2,1).setState(2);
        _grid.getPatch(2,2).setState(2);
        _grid.getPatch(1,2).setState(2);
        _grid.getPatch(0,2).setState(2);
        System.out.println(_grid);

        for (int i = 0; i < 6; i++) {
            _grid = BriansBrain.run(_grid);
            System.out.println("------------");
            System.out.println(_grid);
        }
   }

    public static void main (String[] args) {
        Goldfish g = new Goldfish();
        g.run();
    }
}
