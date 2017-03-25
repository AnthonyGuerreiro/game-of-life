package gol.core;

import gol.display.Display;

public interface GameOfLife {

    void play(Display display) throws InterruptedException;
    void compute();
}
