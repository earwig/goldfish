package edu.stuy.goldfish;

public class Patch {
    private int _xcor, _ycor, _state;
    private String _label;
    private Grid _myGrid;

    public Patch(Grid grid, int xcor, int ycor, int state, String label) {
        _myGrid = grid;
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
        return _plabel;
    }
}    
