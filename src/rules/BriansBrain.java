package edu.stuy.goldfish.rules;

import edu.stuy.goldfish.Grid;
import edu.stuy.goldfish.Patch;

public class BriansBrain extends RuleSet {
    public static int states = 3;

    public static Grid run(Grid g) {
        Grid newGrid = new Grid(g.getWidth(), g.getHeight());
        for (int i = 0; i < g.getWidth(); i++) {
            for (int j = 0; j < g.getHeight(); j++) {
                Patch[] neighbors = g.getPatch(i, j).get8Neighbors();
                int numAlive = 0;
                for (Patch neighbor : neighbors)
                    if (neighbor.getState() == 2) numAlive++;
                Patch orig = g.getPatch(i, j);
                Patch p = g.getPatch(i, j).clone(newGrid);
                if (orig.getState() == 0 && numAlive == 2) p.setState(2);
                if (orig.getState() == 1) p.setState(0); // Dying cells die.
                if (orig.getState() == 2) p.setState(1); // Make living cells dying.
                newGrid.setPatch(i,j,p);
            }
        }
        return newGrid;
    }
}
