package edu.stuy.goldfish;

public class Patch {
    private int _pxcor, _pycor;
    private String _plabel;
    private Grid _myGrid;

    public Patch(Grid grid, int xcor, int ycor) {
        _myGrid = grid;
        _pxcor = xcor;
        _pycor = ycor;
    }

    public Patch () {
        this(new Grid(), 0, 0);
    }

    public String toString() {
        return _plabel;
    }
}    
