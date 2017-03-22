package gol.core;

import org.junit.Test;

public class TestStayDeadCenterCell extends AbstractTest {

    private final static int HEIGHT = 3;
    private final static int WIDTH = 3;

    public TestStayDeadCenterCell() {
        super(HEIGHT, WIDTH);
    }

    @Test
    public void testOverPopulatedCenterCell() {
        gameOfLife.step();

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                assertDead(i, j);
            }
        }
    }

    @Override
    public void initialize(Board board) {
        board.spawn(0, 0);
        board.spawn(0, 1);
    }
}
