package gol.core;

public interface Board {

    void spawn(int i, int j);

    boolean isAlive(int i, int j);

    void kill(int i, int j);

    void spawnRandom();

    boolean isFinished();

    int getHeight();

    int getWidth();

    int getCellsAlive();
}
