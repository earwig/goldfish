import java.util.Random;

public class Screen {

	private int width, height;
	public int[] pixels;
	private int[] _grid;

	Random random = new Random();

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;

		pixels = new int[width * height];
		_grid = new int[width * height];
	}

	public void clear() {
		for (int x = 0; x < _grid.length; x++) {
			pixels[x] = 0;
		}
	}

	public void render() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixels[x+y*width] = random.nextInt();
				//pixels[x + y * width] = _grid[x + y * width];
			}
		}
	}

	public void draw(int x, int y, int color) {
		_grid[x + y * width] = color;
	}
}
