package edu.stuy.goldfish.rules;

import edu.stuy.goldfish.Grid;

public interface RuleSet {
    public static int states;

    /**
     * Run this ruleset on a grid, returning the result.
     *
     * @param g The grid this is running on
     *
     * @return The new grid
     */
    public static Grid run (Grid g);

}
