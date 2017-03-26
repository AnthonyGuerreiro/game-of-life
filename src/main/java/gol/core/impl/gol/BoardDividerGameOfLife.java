package gol.core.impl.gol;

import gol.core.Board;
import gol.core.GameOfLife;
import gol.core.NeighborCounter;
import gol.core.impl.board.ThreadSafeBoard;
import gol.display.Display;
import gol.display.NoDisplay;
import gol.validator.GameOfLifeValidator;

import java.util.concurrent.*;

public class BoardDividerGameOfLife implements GameOfLife {

    private final static int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    private final int height;
    private final int width;
    private final double interval;
    private final int randomCellsBornPerTurn;
    private final NeighborCounter neighborCounter = new NeighborCounter();
    private final int[] ROWS_PER_THREAD;
    private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
    private Board board;
    private Board nextBoard;
    private final CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT, computeCallback());

    public BoardDividerGameOfLife(int height, int width, double aliveProbability, int randomCellsBornPerTurn,
                                  double interval) {
        new GameOfLifeValidator().validate(height, width, aliveProbability, randomCellsBornPerTurn, interval);
        this.height = height;
        this.width = width;
        this.interval = interval;
        this.randomCellsBornPerTurn = randomCellsBornPerTurn;
        this.board = createBoard(height, width, aliveProbability);
        this.nextBoard = copy(this.board);

        this.ROWS_PER_THREAD = new int[THREAD_COUNT];
        initRowsPerThread();
    }

    private Board createBoard(int height, int width, double aliveProbability) {
        return new ThreadSafeBoard(height, width, aliveProbability);
    }

    private Board copy(Board board) {
        return new ThreadSafeBoard(board);
    }

    private void initRowsPerThread() {
        int sum = 0;
        int rowsPerThread = height % THREAD_COUNT;

        for (int i = 0; i < THREAD_COUNT - 1; i++) {
            sum += rowsPerThread;
            ROWS_PER_THREAD[i] = rowsPerThread;
        }
        ROWS_PER_THREAD[THREAD_COUNT - 1] = height - sum;
    }

    /**
     * Not thread safe.
     * This constructor must be called safely
     */
    public BoardDividerGameOfLife(Board board, int randomCellsBornPerTurn, double interval) {
        this.height = board.getHeight();
        this.width = board.getWidth();
        this.interval = interval;
        this.randomCellsBornPerTurn = randomCellsBornPerTurn;
        this.board = copy(board);
        this.nextBoard = copy(this.board);
        this.ROWS_PER_THREAD = new int[THREAD_COUNT];
        initRowsPerThread();
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
        resetBarrier();
        int start = 0;
        int end = ROWS_PER_THREAD[0];
        for (int i = 0; i < THREAD_COUNT; i++) {
            int _start = start;
            int _end = end;
            executor.execute(processRows(_start, _end));

            boolean isLastLine = i != THREAD_COUNT - 1;
            if (isLastLine) {
                start = ROWS_PER_THREAD[i];
                end = ROWS_PER_THREAD[i + 1];
            }
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

    private void resetBarrier() {
        barrier.reset();
    }

    private FutureTask<Void> processRows(int start, int end) {

        Callable<Void> callable = () -> {
            for (int i = start; i < end; i++) {
                for (int j = 0; j < width; j++) {
                    int neighbors = neighborCounter.count(board, i, j);
                    compute(i, j, neighbors);
                }
            }
            barrier.await();
            return null;
        };

        return new FutureTask<>(callable);
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

    private Runnable computeCallback() {
        return () -> {
            if (!isFinished()) {
                spawnRandomCells();
            }
        };
    }

    private void spawnRandomCells() {
        for (int i = 0; i < randomCellsBornPerTurn; i++) {
            nextBoard.spawnRandom();
        }
    }
}
