package gol.config;

import gol.core.GameOfLife;
import gol.core.impl.gol.SimpleGameOfLife;

public class SimpleGameOfLifeGenerator implements GameOfLifeGenerator {

    @Override
    public GameOfLife create(int height, int width, double aliveProbability, int randomCellsBornPerTurn,
                             double interval) {
        return new SimpleGameOfLife(height, width, aliveProbability, randomCellsBornPerTurn, interval);
    }
}
