package gol.core.simple;

import gol.core.AbstractTest;
import gol.core.Board;
import gol.core.GameOfLife;
import gol.core.impl.board.SimpleBoard;
import gol.core.impl.gol.SimpleGameOfLife;

public abstract class AbstractSimpleTest extends AbstractTest {

    public AbstractSimpleTest(int height, int width) {
        super(height, width);
    }

    protected Board createBoard(int height, int width, double aliveProbability) {
        return new SimpleBoard(height, width, aliveProbability);
    }

    protected GameOfLife createGameOfLife(Board board, int randomCellsBornPerTurn, double interval) {
        return new SimpleGameOfLife(board, randomCellsBornPerTurn, interval);
    }
}
