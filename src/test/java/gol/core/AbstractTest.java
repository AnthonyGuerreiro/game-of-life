package gol.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class AbstractTest {

    protected final GameOfLife gameOfLife;
    private final int height;
    private final int width;

    public AbstractTest(int height, int width) {
        this.height = height;
        this.width = width;
        Board board = createBoard(height, width);
        initialize(board);
        gameOfLife = createGameOfLife(board);
    }

    private Board createBoard(int height, int width) {
        return new Board(height, width, 0f);
    }

    protected abstract void initialize(Board board);

    private GameOfLife createGameOfLife(Board board) {
        return new GameOfLife(board, 0, 0.5f);
    }

    public void assertAlive(int i, int j) {
        assertTrue(gameOfLife.isAlive(i, j));
    }

    public void assertDead(int i, int j) {
        assertFalse(gameOfLife.isAlive(i, j));
    }
}
