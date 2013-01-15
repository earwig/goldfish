package edu.stuy.goldfish.rules;

import edu.stuy.goldfish.Grid;
import edu.stuy.goldfish.Patch;

// An implementation of conway using von Neumann Neighborhoods.
public class Conway4 extends RuleSet {
    public static int states = 2;

    public static Grid run (Grid g) {
        Grid newGrid = new Grid(g.getWidth(), g.getHeight());
        for (int i = 0; i < g.getWidth(); i++) {
            for (int j = 0; j < g.getHeight(); j++) {
                Patch[] neighbors = g.getPatch(i, j).get4Neighbors();
                int numAlive = 0;
                for (Patch p : neighbors)
                    if (p.getState() == 1) numAlive++;
                Patch p = g.getPatch(i,j).clone(newGrid);
                if (numAlive < 2) {
                    p.setState(0); //Dies by underpopulation
                }
                if (numAlive > 3) {
                    p.setState(0); //Dies by overpopulation
                }
                if (numAlive == 3)
                    p.setState(1); //Born with 3 neighbors.
                newGrid.setPatch(i,j,p);
            }
        }
        return newGrid;
    }

}
