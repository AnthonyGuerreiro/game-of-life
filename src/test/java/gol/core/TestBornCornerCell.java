package gol.core;

import org.junit.Test;

public class TestBornCornerCell extends AbstractTest {

    private final static int HEIGHT = 3;
    private final static int WIDTH = 3;

    public TestBornCornerCell() {
        super(HEIGHT, WIDTH);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void testOverPopulatedCenterCell() {
        gameOfLife.step();

        assertAlive(0, 0);
        assertAlive(0, 1);
        assertDead(0, 2);

        assertAlive(1, 0);
        assertAlive(1, 1);
        assertDead(1, 2);

        assertDead(2, 0);
        assertDead(2, 1);
        assertDead(2, 2);
    }

    @Override
    public void initialize(Board board) {
        board.spawn(0, 1);
        board.spawn(1, 0);
        board.spawn(1, 1);
    }
}
