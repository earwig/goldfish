public class Goldfish {

    private Grid _grid;
    private Render _render;

    public Goldfish () {
        _render = new Render(640, 480);
        _grid = new Grid(640, 480);
    }

    public static void main (String[] args) {
        Goldfish g = new Goldfish();
    }
}
