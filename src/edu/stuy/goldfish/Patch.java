package edu.stuy.goldfish;

public class Patch {
    private int _xcor, _ycor, _state;
    private Grid _grid;

    public Patch(Grid grid, int xcor, int ycor, int state) {
        _grid = grid;
        _xcor = xcor;
        _ycor = ycor;
        _state = state;
    }

    public Patch() {
        this(new Grid(), 0, 0, 0);
    }

    /* Return the grid corresponding to this patch. */
    public Grid getGrid() {
        return _grid;
    }

    /* Return this patch's x coordinate in the grid. */
    public int getX() {
        return _xcor;
    }

    /* Return this patch's y coordinate in the grid. */
    public int getY() {
        return _ycor;
    }

    /* Return this patch's state. */
    public int getState() {
        return _state;
    }

    /* Set this patch's state. */
    public void setState(int state) {
        _state = state;
    }

    public String toString() {
        return "" + ((_state == 0) ? "." : _state);
    }

    /* Return the number of immediate neighbors, excluding diagonals, that have
       the given state. Will not continue checking patches if the number of
       matching patches exceeds the given max value. */
    public int get4Neighbors(int state, int max) {
        int neighbors = 0;
        for (int i = 0; i < 4; i++) {
            Patch p;
            switch(i) {
                case 0:
                    p = _grid.getPatch(_xcor + 1, _ycor); break;
                case 1:
                    p = _grid.getPatch(_xcor - 1, _ycor); break;
                case 2:
                    p = _grid.getPatch(_xcor, _ycor + 1); break;
                default:
                    p = _grid.getPatch(_xcor, _ycor - 1); break;
            }
            if (p.getState() == state)
                neighbors++;
            if (neighbors == max)
                break;
        }
        return neighbors;
    }

    /* Return the number of immediate neighbors that have the given state. Will
       not continue checking patches if the number of matching patches exceeds
       the given max value. */
    public int get8Neighbors(int state, int max) {
        int neighbors = 0;
        for (int i = 0; i < 8; i++) {
            Patch p;
            switch(i) {
                case 0:
                    p = _grid.getPatch(_xcor + 1, _ycor); break;
                case 1:
                    p = _grid.getPatch(_xcor - 1, _ycor); break;
                case 2:
                    p = _grid.getPatch(_xcor, _ycor + 1); break;
                case 3:
                    p = _grid.getPatch(_xcor, _ycor - 1); break;
                case 4:
                    p = _grid.getPatch(_xcor + 1, _ycor + 1); break;
                case 5:
                    p = _grid.getPatch(_xcor + 1, _ycor - 1); break;
                case 6:
                    p = _grid.getPatch(_xcor - 1, _ycor + 1); break;
                default:
                    p = _grid.getPatch(_xcor - 1, _ycor - 1); break;
            }
            if (p.getState() == state)
                neighbors++;
            if (neighbors == max)
                break;
        }
        return neighbors;
    }

    /* Return the number of immediate neighbors, excluding diagonals, that have
       the given state. */
    public int get4Neighbors(int state) {
        return get4Neighbors(state, 4);
    }

    /* Return the number of immediate neighbors that have the given state. */
    public int get8Neighbors(int state) {
        return get8Neighbors(state, 8);
    }

    /* Return a new patch identical to this one, but in a different grid. */
    public Patch clone(Grid grid) {
        return new Patch(grid, _xcor, _ycor, _state);
    }
}
