package gol.core.rowsplitter;

import gol.core.AbstractTest;
import gol.core.Board;
import gol.core.GameOfLife;
import gol.core.impl.board.ThreadSafeBoard;
import gol.core.impl.gol.RowSplitterGameOfLife;

public abstract class AbstractRowSplitterTest extends AbstractTest {

    public AbstractRowSplitterTest(int height, int width) {
        super(height, width);
    }

    protected Board createBoard(int height, int width, double aliveProbability) {
        return new ThreadSafeBoard(height, width, aliveProbability);
    }

    protected GameOfLife createGameOfLife(Board board, int randomCellsBornPerTurn, double interval) {
        return new RowSplitterGameOfLife(board, randomCellsBornPerTurn, interval);
    }
}
