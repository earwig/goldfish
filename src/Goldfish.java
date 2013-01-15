package edu.stuy.goldfish;


import edu.stuy.goldfish.rules.*;

public class Goldfish {

    private Grid _grid;
    private Render _render;

    public Goldfish () {
        _render = new Render(96, 96);
        _grid = new Grid(96, 96);
    }

    public void run () {
        //TODO: make it run.
        _grid.getPatch(1,0).setState(1);
        _grid.getPatch(2,1).setState(1);
        _grid.getPatch(2,2).setState(1);
        _grid.getPatch(1,2).setState(1);
        _grid.getPatch(0,2).setState(1);
        System.out.println(_grid);

        _grid = LifeWithoutDeath.run(_grid);
        System.out.println("------------");
        System.out.println(_grid);
        _render.setGrid(_grid);
        _render.run();

   }

    public static void main (String[] args) {
        Goldfish g = new Goldfish();
        while(true) {
        	g.run();
        }
    }
}
