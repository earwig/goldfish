package edu.stuy.goldfish;

public class Grid {
    private Patch[][] _grid;

    public Grid() {
        _grid = new Patch[1][1];
        _grid[0][0] = new Patch(this, 0, 0, 0);
    }

    public Grid(int x, int y, boolean fill) {
        _grid = new Patch[y][x];
        if (fill) {
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    _grid[j][i] = new Patch(this, i, j, 0);
                }
            }
        }
    }

    public Grid(int x, int y) {
        this(x, y, true);
    }

    /* Take an x coordinate, and return x′ such that 0 <= x′ < width. */
    private int normalizeX(int x) {
        while (x >= getWidth())
            x -= getWidth();
        while (x < 0)
            x += getWidth();
        return x;
    }

    /* Take a y coordinate, and return y′ such that 0 <= y′ < height. */
    private int normalizeY(int y) {
        while (y >= getHeight())
            y -= getHeight();
        while (y < 0)
            y += getHeight();
        return y;
    }

    /* Return the width of the grid. */
    public int getWidth() {
        return _grid[0].length;
    }

    /* Return the height of the grid. */
    public int getHeight() {
        return _grid.length;
    }

    /* Return the patch at (x, y). x and y need not be within range of the
       grid. */
    public Patch getPatch(int x, int y) {
        x = normalizeX(x);
        y = normalizeY(y);
        return _grid[y][x];
    }

    /* Set the patch at (x, y) to p. */
    public void setPatch(int x, int y, Patch p) {
        x = normalizeX(x);
        y = normalizeY(y);
        _grid[y][x] = p;
    }

    public String toString() {
        String ans = "";
        for (int i = 0; i < _grid.length; i++) {
            for (int j = 0; j < _grid[i].length; j++) {
                ans += _grid[i][j];
            }
            ans += "\n";
        }
        return ans;
    }
}
