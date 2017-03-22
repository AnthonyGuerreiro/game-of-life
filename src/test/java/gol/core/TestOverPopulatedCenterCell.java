package gol.core;

import org.junit.Test;

public class TestOverPopulatedCenterCell extends AbstractTest {

    private final static int HEIGHT = 3;
    private final static int WIDTH = 3;

    public TestOverPopulatedCenterCell() {
        super(HEIGHT, WIDTH);
    }

    @Test
    public void testOverPopulatedCenterCell() {
        gameOfLife.step();

        assertAlive(0, 0);
        assertDead(0, 1);
        assertAlive(0, 2);

        assertAlive(1, 0);
        assertDead(1, 1);
        assertAlive(1, 2);

        assertDead(2, 0);
        assertDead(2, 1);
        assertDead(2, 2);
    }

    @Override
    public void initialize(Board board) {
        board.spawn(0, 0);
        board.spawn(0, 1);
        board.spawn(0, 2);
        board.spawn(1, 1);
        board.spawn(1, 2);
    }
}
