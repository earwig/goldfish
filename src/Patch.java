package edu.stuy.goldfish;

public class Patch {
    private int _xcor, _ycor, _state;
    private String _label;
    private Grid _grid;

    public Patch(Grid grid, int xcor, int ycor, int state, String label) {
        _grid = grid;
        _xcor = xcor;
        _ycor = ycor;
        _state = state;
        _label = label;
    }

    public Patch() {
        this(new Grid(), 0, 0, 0, "");
    }

    public Grid getGrid() {
        return _grid;
    }

    public int getX() {
        return _xcor;
    }

    public int getY() {
        return _ycor;
    }

    public int getState() {
        return _state;
    }

    public void setState(int state) {
        _state = state;
    }

    public String getLabel() {
        return _label;
    }

    public void setLabel(String label) {
        _label = label;
    }

    public String toString() {
        return _label;
    }

    public Patch[] get4Neighbors() {
        Patch[] neighbors = new Patch[4];
        neighbors[0] = _grid.getPatch(_xcor + 1, _ycor);
        neighbors[1] = _grid.getPatch(_xcor - 1, _ycor);
        neighbors[2] = _grid.getPatch(_xcor, _ycor + 1);
        neighbors[3] = _grid.getPatch(_xcor, _ycor - 1);
        return neighbors;
    }

    public Patch[] get8Neighbors() {
        Patch[] neighbors = new Patch[8];
        neighbors[0] = _grid.getPatch(_xcor + 1, _ycor);
        neighbors[1] = _grid.getPatch(_xcor - 1, _ycor);
        neighbors[2] = _grid.getPatch(_xcor, _ycor + 1);
        neighbors[3] = _grid.getPatch(_xcor, _ycor - 1);
        neighbors[4] = _grid.getPatch(_xcor + 1, _ycor + 1);
        neighbors[5] = _grid.getPatch(_xcor + 1, _ycor - 1);
        neighbors[6] = _grid.getPatch(_xcor - 1, _ycor + 1);
        neighbors[7] = _grid.getPatch(_xcor - 1, _ycor - 1);
        return neighbors;
    }

    public Patch clone() {
        return new Patch(_grid, _xcor, _ycor, _state, _label);
    }
}
