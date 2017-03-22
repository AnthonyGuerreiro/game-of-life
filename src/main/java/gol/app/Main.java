package gol.app;

import gol.core.GameOfLife;
import gol.display.ConsoleDisplay;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        GameOfLife gol = new GameOfLife(4, 4, 0.3f, 0, 0.5f);
        gol.play(new ConsoleDisplay());
    }
}
