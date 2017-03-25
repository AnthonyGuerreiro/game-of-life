package gol.core.impl.board;

import gol.core.Board;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSafeBoard implements Board {

    private final int height;
    private final int width;
    private final Boolean[][] cells;
    private final AtomicInteger cellsAlive;

    /**
     * Not thread safe.
     * This constructor must be called safely
     */
    public ThreadSafeBoard(Board board) {
        this(board.getHeight(), board.getWidth(), 0f);
        copy(board, height, width);
    }

    public ThreadSafeBoard(int height, int width, double aliveProbability) {
        this.width = width;
        this.height = height;
        this.cellsAlive = new AtomicInteger();
        this.cells = new Boolean[height][width];
        init(aliveProbability);
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
            for (int j = 0; j < height; j++) {
                boolean spawn = Math.random() < aliveProbability;
                cells[i][j] = spawn ? Boolean.TRUE : Boolean.FALSE;
            }
        }
    }

    @Override
    public void spawn(int i, int j) {
        synchronized (cells[i][j]) {
            boolean alive = isAlive(i, j);
            if (!alive) {
                set(i, j, true);
                cellsAlive.incrementAndGet();
            }
        }
    }

    @Override
    public boolean isAlive(int i, int j) {
        synchronized (cells[i][j]) {
            return cells[i][j];
        }
    }

    @Override
    public void kill(int i, int j) {
        synchronized (cells[i][j]) {
            boolean alive = isAlive(i, j);
            if (alive) {
                set(i, j, false);
                cellsAlive.decrementAndGet();
            }
        }
    }

    @Override
    public void spawnRandom() {
        if (getCellsAlive() == height * width) {
            return;
        }

        int i = getRandomRow();
        int j = getRandomColumn();
        boolean spawned = false;
        synchronized (cells[i][j]) {
            if (!isAlive(i, j)) {
                spawn(i, j);
                spawned = true;
            }
        }
        if (!spawned) {
            spawnRandom();
        }
    }

    @Override
    public boolean isFinished() {
        return cellsAlive.get() == 0;
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
        return cellsAlive.get();
    }

    private void set(int i, int j, boolean alive) {
        cells[i][j] = alive;
    }

    private int getRandomRow() {
        int height = getHeight();
        int row = (int) (Math.random() * height);
        return row == height ? row - 1 : row;
    }

    private int getRandomColumn() {
        int width = getWidth();
        int column = (int) (Math.random() * width);
        return column == width ? column - 1 : column;
    }
}
