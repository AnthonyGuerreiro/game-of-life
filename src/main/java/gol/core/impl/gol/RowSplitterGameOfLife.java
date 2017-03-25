package gol.core.impl.gol;

import gol.core.Board;
import gol.core.GameOfLife;
import gol.core.NeighborCounter;
import gol.core.impl.board.ThreadSafeBoard;
import gol.display.Display;
import gol.display.NoDisplay;
import gol.validator.GameOfLifeValidator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RowSplitterGameOfLife implements GameOfLife {

    private final static int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    private final int height;
    private final int width;
    private final double interval;
    private final int randomCellsBornPerTurn;
    private final NeighborCounter neighborCounter = new NeighborCounter();
    private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

    private Board board;
    private Board nextBoard;
    private CountDownLatch latch;

    public RowSplitterGameOfLife(int height, int width, double aliveProbability, int randomCellsBornPerTurn,
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
        return new ThreadSafeBoard(height, width, aliveProbability);
    }

    private Board copy(Board board) {
        return new ThreadSafeBoard(board);
    }

    /**
     * Not thread safe.
     * This constructor must be called safely
     */
    public RowSplitterGameOfLife(Board board, int randomCellsBornPerTurn, double interval) {
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
    public void compute() throws InterruptedException {
        createNewLatch();

        for (int i = 0; i < height; i++) {
            int row = i;
            executor.execute(() -> processRow(row));
        }

        latch.await();
        if (!isFinished()) {
            spawnRandomCells();
        }
    }

    @Override
    public boolean isFinished() {
        return board.isFinished();
    }

    @Override
    public void step() throws InterruptedException {
        compute();
        commit();
    }

    @Override
    public boolean isAlive(int i, int j) {
        return board.isAlive(i, j);
    }

    @Override
    public int getCellsAlive() {
        return board.getCellsAlive();
    }

    private void commit() {
        this.board = this.nextBoard;
        this.nextBoard = copy(this.board);
    }

    private void createNewLatch() {
        latch = new CountDownLatch(height);
    }

    private void processRow(int i) {

        for (int j = 0; j < width; j++) {
            int neighbors = neighborCounter.count(board, i, j);
            compute(i, j, neighbors);
        }
        latch.countDown();
    }

    private void spawnRandomCells() {
        for (int i = 0; i < randomCellsBornPerTurn; i++) {
            nextBoard.spawnRandom();
        }
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
}
