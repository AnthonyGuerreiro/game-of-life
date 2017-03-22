package gol.display;

import gol.core.Board;

public interface Display {

    void clearScreen();
    void display(Board board);
    void displayStats(int iterations);
}
