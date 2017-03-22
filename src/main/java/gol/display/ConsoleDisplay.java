package gol.display;

import gol.core.Board;

public class ConsoleDisplay implements Display {

    @Override
    public void display(Board board) {
        int height = board.getHeight();
        int width = board.getWidth();

        print('-', width + 2);
        print('\n', 1);

        for (int i = 0; i < height; i++) {
            print('|', 1);
            for (int j = 0; j < width; j++) {
                print(getChar(board, i, j), 1);
            }
            print('|', 1);
            print('\n', 1);
        }

        print('-', width + 2);
        System.out.println();
    }

    private void print(char c, int times) {
        for (int i = 0; i < times; i++) {
            System.out.print(c);
        }
    }

    private char getChar(Board board, int i, int j) {
        boolean alive = board.getCell(i, j);
        return alive ? '*' : ' ';
    }

    @Override
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void displayStats(int iterations) {
        System.out.println("Game lasted for " + iterations + " iterations");
    }
}
