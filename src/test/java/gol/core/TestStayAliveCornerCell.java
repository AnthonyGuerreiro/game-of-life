package gol.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestStayAliveCornerCell extends AbstractTest {

    private final static int HEIGHT = 3;
    private final static int WIDTH = 3;

    public TestStayAliveCornerCell() {
        super(HEIGHT, WIDTH);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void testOverPopulatedCenterCell() {
        assertEquals(3, gameOfLife.getCellsAlive());
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

        assertEquals(4, gameOfLife.getCellsAlive());
    }

    @Override
    public void initialize(Board board) {
        board.spawn(0, 0);
        board.spawn(0, 1);
        board.spawn(1, 0);
    }
}
