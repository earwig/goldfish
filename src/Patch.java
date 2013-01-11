public class Patch {
    private int _pxcor, _pycor;
    private String _plabel;
    private Grid _myGrid;

    public Patch(Grid grid, int xcor, int ycor) {
        _myGrid = grid;
        _pxcor = xcor;
        _pycor = ycor;
    }

    public String toString() {
        return _plabel;
    }
}    
