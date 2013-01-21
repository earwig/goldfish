package edu.stuy.goldfish.rules;

import edu.stuy.goldfish.Grid;
import edu.stuy.goldfish.Patch;

// Conway, without dying cells
public class LifeWithoutDeath extends RuleSet {
    public static int states = 2;

    public static Grid run(Grid g) {
        Grid newGrid = new Grid(g.getWidth(), g.getHeight(), false);
        for (int i = 0; i < g.getWidth(); i++) {
            for (int j = 0; j < g.getHeight(); j++) {
                Patch orig = g.getPatch(i, j);
                int numAlive = orig.get8Neighbors(1, 4);
                Patch p = orig.clone(newGrid);
                if (numAlive == 3)
                    p.setState(1); // Born with 3 neighbors
                newGrid.setPatch(i, j, p);
            }
        }
        return newGrid;
    }

    public static void setup(Grid g) {
        int[][] pattern = {
            {1,1,1,1,0,1},
            {1,0,1,1,1,1}
        };
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 2; j++) {
                g.getPatch(i + ((g.getWidth() - 6) / 2),
                           j + ((g.getHeight() - 2) / 2)).setState(pattern[j][i]);
            }
        }
    }
}
