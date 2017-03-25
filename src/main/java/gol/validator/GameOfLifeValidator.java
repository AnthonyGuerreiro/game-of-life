package gol.validator;

public class GameOfLifeValidator {

    public void validate(int height, int width, double aliveProbability, int randomCellsBornPerTurn,
                         double interval) {
        if (height < 1 || width < 1) {
            throw new IllegalArgumentException("The board has to have positive dimensions");
        }

        if (aliveProbability < 0 || aliveProbability > 1) {
            throw new IllegalArgumentException("The aliveProbability must be contained in [0.0, 1.0]");
        }

        if (interval < 0f) {
            throw new IllegalArgumentException("The interval must be >= 0.0");
        }
        if (randomCellsBornPerTurn < 0) {
            throw new IllegalArgumentException("The randomCellsBornPerTurn must be >= 0");
        }
    }
}
