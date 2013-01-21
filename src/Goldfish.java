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
        setup(_render.rule);
        while (true) {
            if (_render.reset) {
                setup(_render.rule);
                _render.reset = false;
            }
            if (!_render.paused) {
                _render.acquireLock(0);
                String rule = _render.rule;
                if (rule.equals("Conway"))
                    _grid = Conway.run(_grid);
                else if (rule.equals("Conway4"))
                    _grid = Conway4.run(_grid);
                else if (rule.equals("Life Without Death"))
                    _grid = LifeWithoutDeath.run(_grid);
                else if (rule.equals("Brian's Brain"))
                    _grid = BriansBrain.run(_grid);
                _render.releaseLock(0);
            }
            _render.setGrid(_grid);
            _render.run();
            _render.sleep();
        }
    }

    public static int getMaxStates(String rule) {
            if (rule.equals("Conway"))
                return Conway.states;
            else if (rule.equals("Conway4"))
                return Conway4.states;
            else if (rule.equals("Life Without Death"))
                return LifeWithoutDeath.states;
            else if (rule.equals("Brian's Brain"))
                return BriansBrain.states;
            return 2;
    }

    private void setup(String rule) {
        if (rule.equals("Conway")) {
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
                    _grid.getPatch(i + 2, j + 2).setState(glidergun[j][i]);
                }
            }
        } else if (rule.equals("Life Without Death")) {
            int[][] pattern = {
                {1,1,1,1,0,1},
                {1,0,1,1,1,1}
            };
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 2; j++) {
                    _grid.getPatch(i + ((_grid.getHeight() - 6) / 2),
                                   j + ((_grid.getWidth() - 2) / 2)).setState(pattern[j][i]);
                }
            }
        } else if (rule.equals("Brian's Brain")) {
            int[][] pattern = {
                {2,0,2,0,2},
                {2,0,2,0,2},
                {0,1,0,1,0}
            };
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 3; j++) {
                    _grid.getPatch(i + ((_grid.getHeight() - 5) / 2),
                                   j + ((_grid.getWidth() - 3) / 2)).setState(pattern[j][i]);
                }
            }
        }
    }

    public static void main(String[] args) {
        Goldfish g = new Goldfish();
        g.run();
    }
}
