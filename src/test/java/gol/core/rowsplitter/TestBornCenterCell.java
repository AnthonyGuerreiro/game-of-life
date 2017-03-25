package gol.core.rowsplitter;

import gol.core.Board;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestBornCenterCell extends AbstractRowSplitterTest {

    private final static int HEIGHT = 3;
    private final static int WIDTH = 3;

    public TestBornCenterCell() {
        super(HEIGHT, WIDTH);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void testOverPopulatedCenterCell() throws InterruptedException {
        assertEquals(3, gameOfLife.getCellsAlive());
        gameOfLife.step();

        assertDead(0, 0);
        assertAlive(0, 1);
        assertDead(0, 2);

        assertDead(1, 0);
        assertAlive(1, 1);
        assertDead(1, 2);

        assertDead(2, 0);
        assertDead(2, 1);
        assertDead(2, 2);

        assertEquals(2, gameOfLife.getCellsAlive());
    }

    @Override
    public void initialize(Board board) {
        board.spawn(0, 0);
        board.spawn(0, 1);
        board.spawn(0, 2);
    }
}
