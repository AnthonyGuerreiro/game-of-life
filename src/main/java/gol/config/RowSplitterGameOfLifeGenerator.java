package gol.config;

import gol.core.GameOfLife;
import gol.core.impl.gol.RowSplitterGameOfLife;

public class RowSplitterGameOfLifeGenerator implements GameOfLifeGenerator {

    @Override
    public GameOfLife create(int height, int width, double aliveProbability, int randomCellsBornPerTurn,
                             double interval) {
        return new RowSplitterGameOfLife(height, width, aliveProbability, randomCellsBornPerTurn, interval);
    }
}
