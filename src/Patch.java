package edu.stuy.goldfish;

public class Patch {
    private int _pxcor, _pycor, _state;
    private String _plabel;
    private Grid _myGrid;

    public Patch(Grid grid, int xcor, int ycor, int state) {
        _myGrid = grid;
        _pxcor = xcor;
        _pycor = ycor;
        _state = state;
    }

    public Patch() {
        this(new Grid(), 0, 0, 0);
    }

    public Grid getGrid() {
        return _grid;
    }

    public int getX() {
        return _pxcor;
    }

    public int getY() {
        return _pycor;
    }

    public String toString() {
        return _plabel;
    }
}    
