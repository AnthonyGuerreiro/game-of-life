package gol.core;

import gol.display.Display;

public class GameOfLife {

    private final int height;
    private final int width;
    private final int randomCellsBornPerTurn;
    private final double interval;

    private Board board;
    private Board nextBoard;

    public GameOfLife(int height, int width, double aliveProbability, int randomCellsBornPerTurn, double interval) {
        if (height < 1
                || width < 1
                || interval < 0f
                || aliveProbability < 0
                || aliveProbability > 1
                || randomCellsBornPerTurn < 0) {
            throw new IllegalArgumentException();
        }
        this.height = height;
        this.width = width;
        this.interval = interval;
        this.randomCellsBornPerTurn = randomCellsBornPerTurn;
        this.board = new Board(height, width, aliveProbability);
        this.nextBoard = new Board(this.board);
    }

    public GameOfLife(Board board, int randomCellsBornPerTurn, double interval) {
        this.height = board.getHeight();
        this.width = board.getWidth();
        this.interval = interval;
        this.randomCellsBornPerTurn = randomCellsBornPerTurn;
        this.board = new Board(board);
        this.nextBoard = new Board(this.board);
    }

    public void play() throws InterruptedException {
        play(null);
    }

    public void play(Display display) throws InterruptedException {
        int iterations = 0;
        while (!board.isDone()) {
            step();
            if (display != null) {
                display.clearScreen();
                display.display(board);
                iterations++;
            }
            Thread.sleep((int) (interval * 1000L));
        }
        if (display != null) {
            display.displayStats(iterations);
        }
    }


    public void step() {
        compute();
        commit();
    }

    private void compute() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int neighbors = getNeighborsCount(i, j);
                compute(i, j, neighbors);
            }
        }

        spawnRandomCells();
    }

    private void commit() {
        this.board = this.nextBoard;
        this.nextBoard = new Board(this.board);
    }

    private int getNeighborsCount(int i, int j) {
        int neighborsCount = 0;

        //0, height is top-left
        boolean top = isTop(i);
        boolean bottom = isBottom(i);
        boolean left = isLeft(j);
        boolean right = isRight(j);

        if (!bottom && !left && board.isAlive(i - 1, j - 1)) {
            neighborsCount++;
        }
        if (!bottom && board.isAlive(i - 1, j)) {
            neighborsCount++;
        }
        if (!bottom && !right && board.isAlive(i - 1, j + 1)) {
            neighborsCount++;
        }
        if (!left && board.isAlive(i, j - 1)) {
            neighborsCount++;
        }
        if (!right && board.isAlive(i, j + 1)) {
            neighborsCount++;
        }
        if (!top && !left && board.isAlive(i + 1, j - 1)) {
            neighborsCount++;
        }
        if (!top && board.isAlive(i + 1, j)) {
            neighborsCount++;
        }
        if (!top && !right && board.isAlive(i + 1, j + 1)) {
            neighborsCount++;
        }
        return neighborsCount;
    }

    private void compute(int i, int j, int neighbors) {
        boolean underpopulated = neighbors < 2;
        boolean overpopulated = neighbors > 3;
        boolean optimal = neighbors == 3;
        //if neighbors == 2 then this cell state does not change

        if (underpopulated || overpopulated) {
            nextBoard.kill(i, j);
        } else if (optimal) {
            nextBoard.spawn(i, j);
        }
    }

    private void spawnRandomCells() {
        for (int i = 0; i < randomCellsBornPerTurn; i++) {
            nextBoard.spawnRandom();
        }
    }

    private boolean isTop(int i) {
        return i == height - 1;
    }

    private boolean isBottom(int i) {
        return i == 0;
    }

    private boolean isLeft(int j) {
        return j == 0;
    }

    private boolean isRight(int j) {
        return j == width - 1;
    }

    public boolean isAlive(int i, int j) {
        return board.isAlive(i, j);
    }

    public int getCellsAlive() {
        return board.getCellsAlive();
    }
}
