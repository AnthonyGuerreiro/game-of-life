package gol.core.rowsplitter;

import gol.core.Board;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestUnderPopulatedCornerCell extends AbstractRowSplitterTest {

    private final static int HEIGHT = 3;
    private final static int WIDTH = 3;

    public TestUnderPopulatedCornerCell() {
        super(HEIGHT, WIDTH);
    }

    @Test
    public void testUnderPopulatedCenterCell() throws InterruptedException {
        assertEquals(2, gameOfLife.getCellsAlive());
        gameOfLife.step();

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                assertDead(i, j);
            }
        }
        assertEquals(0, gameOfLife.getCellsAlive());
    }

    @Override
    public void initialize(Board board) {
        board.spawn(0, 0);
        board.spawn(0, 1);
    }
}
