package gol.core.impl.gol;

import gol.core.Board;
import gol.core.GameOfLife;
import gol.core.NeighborCounter;
import gol.core.impl.board.SimpleBoard;
import gol.display.Display;
import gol.display.NoDisplay;
import gol.validator.GameOfLifeValidator;

public class SimpleGameOfLife implements GameOfLife {

    private final int height;
    private final int width;
    private final int randomCellsBornPerTurn;
    private final double interval;
    private final NeighborCounter neighborCounter = new NeighborCounter();

    private Board board;
    private Board nextBoard;

    public SimpleGameOfLife(int height, int width, double aliveProbability, int randomCellsBornPerTurn,
                            double interval) {
        new GameOfLifeValidator().validate(height, width, aliveProbability, randomCellsBornPerTurn, interval);
        this.height = height;
        this.width = width;
        this.interval = interval;
        this.randomCellsBornPerTurn = randomCellsBornPerTurn;
        this.board = createBoard(height, width, aliveProbability);
        this.nextBoard = copy(this.board);
    }

    private Board createBoard(int height, int width, double aliveProbability) {
        return new SimpleBoard(height, width, aliveProbability);
    }

    private Board copy(Board board) {
        return new SimpleBoard(board);
    }

    public SimpleGameOfLife(Board board, int randomCellsBornPerTurn, double interval) {
        this.height = board.getHeight();
        this.width = board.getWidth();
        this.interval = interval;
        this.randomCellsBornPerTurn = randomCellsBornPerTurn;
        this.board = copy(board);
        this.nextBoard = copy(this.board);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void play(Display display) throws InterruptedException {
        int iterations = 0;
        if (display == null) {
            display = new NoDisplay();
        }

        while (!board.isFinished()) {
            step();
            display.clearScreen();
            display.display(board);
            iterations++;
            Thread.sleep((int) (interval * 1000L));
        }
        display.displayStats(iterations);
    }

    @Override
    public void step() throws InterruptedException {
        compute();
        commit();
    }

    private void commit() {
        this.board = this.nextBoard;
        this.nextBoard = copy(this.board);
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

    @Override
    public void compute() throws InterruptedException {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int neighbors = neighborCounter.count(board, i, j);
                compute(i, j, neighbors);
            }
        }

        if (!isFinished()) {
            spawnRandomCells();
        }
    }

    @Override
    public boolean isFinished() {
        return board.isFinished();
    }

    @Override
    public boolean isAlive(int i, int j) {
        return board.isAlive(i, j);
    }

    @Override
    public int getCellsAlive() {
        return board.getCellsAlive();
    }
}
