package gol.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class AbstractTest {

    protected final GameOfLife gameOfLife;

    public AbstractTest(int height, int width) {
        Board board = createBoard(height, width, 0f);
        initialize(board);
        gameOfLife = createGameOfLife(board, 0, 0.5f);
    }

    protected abstract Board createBoard(int height, int width, double aliveProbability);

    protected abstract void initialize(Board board);

    protected abstract GameOfLife createGameOfLife(Board board, int randomCellsBornPerTurn, double interval);

    protected void assertAlive(int i, int j) {
        assertTrue(gameOfLife.isAlive(i, j));
    }

    protected void assertDead(int i, int j) {
        assertFalse(gameOfLife.isAlive(i, j));
    }
}
