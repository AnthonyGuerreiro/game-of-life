package gol.core;

public class NeighborCounter {

    public int count(Board board, int i, int j) {
        int neighborsCount = 0;

        //0, height is top-left
        boolean top = isTop(i, board);
        boolean bottom = isBottom(i);
        boolean left = isLeft(j);
        boolean right = isRight(j, board);

        if (!bottom && !left && board.isAlive(i - 1, j - 1)) {
            neighborsCount++;
        }
        if (!bottom && board.isAlive(i - 1, j)) {
            neighborsCount++;
        }
        if (!bottom && !right && board.isAlive(i - 1, j + 1)) {
            neighborsCount++;
        }
        if (!left && board.isAlive(i, j - 1)) {
            neighborsCount++;
        }
        if (!right && board.isAlive(i, j + 1)) {
            neighborsCount++;
        }
        if (!top && !left && board.isAlive(i + 1, j - 1)) {
            neighborsCount++;
        }
        if (!top && board.isAlive(i + 1, j)) {
            neighborsCount++;
        }
        if (!top && !right && board.isAlive(i + 1, j + 1)) {
            neighborsCount++;
        }
        return neighborsCount;
    }


    private boolean isTop(int i, Board board) {
        return i == board.getHeight() - 1;
    }

    private boolean isBottom(int i) {
        return i == 0;
    }

    private boolean isLeft(int j) {
        return j == 0;
    }

    private boolean isRight(int j, Board board) {
        return j == board.getWidth() - 1;
    }
}
