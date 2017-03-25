package gol.config;

import gol.core.GameOfLife;

public interface GameOfLifeGenerator {

    GameOfLife create(int height, int width, double aliveProbability, int randomCellsBornPerTurn, double interval);
}
