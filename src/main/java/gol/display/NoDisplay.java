package gol.display;

import gol.core.Board;

public class NoDisplay implements Display {

    @Override
    public void clearScreen() {
        //nop
    }

    @Override
    public void display(Board board) {
        //nop
    }

    @Override
    public void displayStats(int iterations) {
        //nop
    }
}
