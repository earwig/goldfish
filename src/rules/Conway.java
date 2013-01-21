package edu.stuy.goldfish.rules;

import edu.stuy.goldfish.Grid;
import edu.stuy.goldfish.Patch;

public class Conway extends RuleSet {
    public static int states = 2;

    public static Grid run(Grid g) {
        Grid newGrid = new Grid(g.getWidth(), g.getHeight(), false);
        for (int i = 0; i < g.getWidth(); i++) {
            for (int j = 0; j < g.getHeight(); j++) {
                Patch orig = g.getPatch(i, j);
                int numAlive = orig.get8Neighbors(1, 4);
                Patch p = orig.clone(newGrid);
                if (numAlive < 2)
                    p.setState(0); // Dies by underpopulation
                else if (numAlive > 3)
                    p.setState(0); // Dies by overpopulation
                else if (numAlive == 3)
                    p.setState(1); // Born with 3 neighbors
                newGrid.setPatch(i, j, p);
            }
        }
        return newGrid;
    }

    public static void setup(Grid g) {
        int[][] glidergun = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
            {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
            {1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,1,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        };
        for (int i = 0; i < 36; i++) {
            for (int j = 0; j < 9; j++) {
                g.getPatch(i + 2, j + 2).setState(glidergun[j][i]);
            }
        }
    }
}
