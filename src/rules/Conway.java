package edu.stuy.goldfish.rules;

import edu.stuy.goldfish.Grid;
import edu.stuy.goldfish.Patch;

public class Conway implements RuleSet {

    public Conway () {
        Patch p = new Patch();
    }

    @Override
    public static Grid run (Grid g) {
        return g; //TODO: do stuff.
    }
}
