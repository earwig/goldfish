package edu.stuy.goldfish;

import edu.stuy.goldfish.rules.*;

public class Goldfish {
    private static final String[] RULES = {"Conway", "Conway4", "Life Without Death", "Brian's Brain"};

    private Grid _grid;
    private Render _render;

    public Goldfish() {
        int width = 128;
        int height = 128;
        _grid = new Grid(width, height);
        _render = new Render(width, height, _grid, RULES);
    }

    public void run() {
        setup();
        while (true) {
        	if (!_render.paused) {
                String rule = _render.rule;
                if (rule.equals("Conway"))
                    _grid = Conway.run(_grid);
                else if (rule.equals("Conway4"))
                    _grid = Conway4.run(_grid);
                else if (rule.equals("Life Without Death"))
                    _grid = LifeWithoutDeath.run(_grid);
                else if (rule.equals("Brian's Brain"))
                    _grid = BriansBrain.run(_grid);
            }
            _render.setGrid(_grid);
            _render.run();
            _render.sleep();
        }
    }

    private void setup() {
        for (int i = 0; i < _grid.getWidth(); i += 16) {
            for (int j = 0; j < _grid.getHeight(); j += 16) {
                _grid.getPatch(i + 1, j + 0).setState(1);
                _grid.getPatch(i + 2, j + 1).setState(1);
                _grid.getPatch(i + 2, j + 2).setState(1);
                _grid.getPatch(i + 1, j + 2).setState(1);
                _grid.getPatch(i + 0, j + 2).setState(1);
            }
        }
    }

    public static void main(String[] args) {
        Goldfish g = new Goldfish();
        g.run();
    }
}
