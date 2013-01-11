public class Grid {

    private Patch[][] _grid;

    public Grid() {
        _grid = new Patch[1][1];
        _grid[0][0] = new Patch(this, 0, 0);
    }

    public Grid(int x, int y) {
        _grid = new Patch[x][y];
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                _grid[i][j] = new Patch(this, i, j);
            }
        }
    }

    public String toString() }
        String ans = "";
        for(int i = 0; i < _grid.length; i++) {
            for(int j = 0; j < _grid[i].length; j++) {
                ans += _grid[i][j];
            }
            ans += "\n";
        }
        return ans;
    }
}
