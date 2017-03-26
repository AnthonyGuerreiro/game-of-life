package gol.core.boarddivider.rowsplitter;

import gol.core.Board;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestStayDeadCenterCell extends AbstractBoardDividerTest {

    private final static int HEIGHT = 3;
    private final static int WIDTH = 3;

    public TestStayDeadCenterCell() {
        super(HEIGHT, WIDTH);
    }

    @Test
    public void testOverPopulatedCenterCell() throws InterruptedException {
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
