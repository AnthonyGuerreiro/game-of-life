package gol.core;

import gol.display.Display;

public interface GameOfLife {

    void play(Display display) throws InterruptedException;
    void compute() throws InterruptedException;
    boolean isFinished();
    void step() throws InterruptedException;
    boolean isAlive(int i, int j);
    int getCellsAlive();
}
