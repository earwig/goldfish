package edu.stuy.goldfish.rules;

import edu.stuy.goldfish.Grid;
import edu.stuy.goldfish.Patch;

public class BriansBrain extends RuleSet {
    public static int states = 3;

    public static Grid run(Grid g) {
        Grid newGrid = new Grid(g.getWidth(), g.getHeight(), false);
        for (int i = 0; i < g.getWidth(); i++) {
            for (int j = 0; j < g.getHeight(); j++) {
                Patch orig = g.getPatch(i, j);
                Patch p = g.getPatch(i, j).clone(newGrid);
                if (orig.getState() == 1) p.setState(0); // Dying cells die.
                else if (orig.getState() == 2) p.setState(1); // Make living cells dying.
                else {
                    int numAlive = orig.get8Neighbors(2, 3);
                    if (orig.getState() == 0 && numAlive == 2) p.setState(2);
                }
                newGrid.setPatch(i,j,p);
            }
        }
        return newGrid;
    }
}
