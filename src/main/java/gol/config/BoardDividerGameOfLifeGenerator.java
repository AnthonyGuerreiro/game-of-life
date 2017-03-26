package gol.config;

import gol.core.GameOfLife;
import gol.core.impl.gol.BoardDividerGameOfLife;

public class BoardDividerGameOfLifeGenerator implements GameOfLifeGenerator {

    @Override
    public GameOfLife create(int height, int width, double aliveProbability, int randomCellsBornPerTurn,
                             double interval) {
        return new BoardDividerGameOfLife(height, width, aliveProbability, randomCellsBornPerTurn, interval);
    }
}
