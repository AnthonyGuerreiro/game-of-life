package gol.core.impl.board;

import gol.core.Board;

public class SimpleBoard implements Board {

    private final int height;
    private final int width;

    private boolean[][] cells;
    private int cellsAlive;

    public SimpleBoard(Board board) {
        this(board.getHeight(), board.getWidth(), 0f);
        copy(board, height, width);
    }

    public SimpleBoard(int height, int width, double aliveProbability) {
        this.height = height;
        this.width = width;
        cells = new boolean[height][width];
        init(aliveProbability);
    }

    @Override
    public void spawn(int i, int j) {
        boolean alive = isAlive(i, j);
        if (!alive) {
            set(i, j, true);
            cellsAlive++;
        }
    }

    @Override
    public boolean isAlive(int i, int j) {
        return cells[i][j];
    }

    @Override
    public void kill(int i, int j) {
        boolean alive = isAlive(i, j);
        if (alive) {
            set(i, j, false);
            cellsAlive--;
        }
    }

    @Override
    public void spawnRandom() {
        if (getCellsAlive() == height * width) {
            return;
        }

        int i = getRandomRow();
        int j = getRandomColumn();
        if (!cells[i][j]) {
            spawn(i, j);
        } else {
            spawnRandom();
        }
    }

    @Override
    public boolean isFinished() {
        return getCellsAlive() == 0;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getCellsAlive() {
        return cellsAlive;
    }

    private void set(int i, int j, boolean alive) {
        cells[i][j] = alive;
    }

    private void copy(Board board, int height, int width) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board.isAlive(i, j)) {
                    spawn(i, j);
                }
            }
        }
    }

    private void init(double aliveProbability) {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (Math.random() < aliveProbability) {
                    spawn(i, j);
                }
            }
        }
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
