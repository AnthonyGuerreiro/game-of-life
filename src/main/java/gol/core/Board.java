package gol.core;

public class Board {

    private final int height;
    private final int width;

    private boolean[][] cells;
    private int cellsAlive;

    public Board(Board board) {
        this.height = board.height;
        this.width = board.width;
        cells = new boolean[height][width];

        copy(board, height, width);
    }

    private void copy(Board board, int height, int width) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board.cells[i][j]) {
                    spawn(i, j);
                }
            }
        }
    }

    public void spawn(int i, int j) {
        boolean alive = cells[i][j];
        if (!alive) {
            cells[i][j] = true;
            cellsAlive++;
        }
    }

    public Board(int height, int width, double aliveProbability) {
        if (height < 1 || width < 1 || aliveProbability < 0 || aliveProbability > 1) {
            throw new IllegalArgumentException();
        }
        this.height = height;
        this.width = width;
        cells = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (Math.random() < aliveProbability) {
                    spawn(i, j);
                }
            }
        }
    }

    public boolean getCell(int i, int j) {
        return cells[i][j];
    }

    public void kill(int i, int j) {
        boolean alive = cells[i][j];
        if (alive) {
            cells[i][j] = false;
            cellsAlive--;
        }
    }

    public void spawnRandom() {
        int i = getRandomRow();
        int j = getRandomColumn();
        if (!cells[i][j]) {
            spawn(i, j);
        } else {
            spawnRandom();
        }
    }

    public boolean isDone() {
        return cellsAlive == 0;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getCellsAlive() {
        return cellsAlive;
    }

    private int getRandomRow() {
        int row = (int) (Math.random() * height);
        return row == height ? row - 1 : row;
    }

    private int getRandomColumn() {
        int column = (int) (Math.random() * width);
        return column == width ? column - 1 : column;
    }
}
