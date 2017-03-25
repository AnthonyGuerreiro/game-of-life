package gol.app;

import gol.config.Mode;
import gol.core.GameOfLife;
import gol.display.ConsoleDisplay;

import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class Main {

    public final static int HEIGHT = 4;
    public final static int WIDTH = 4;
    public final static float ALIVE_PROBABILITY = 0.3f;
    public final static int RANDOM_CELLS_BORN_PER_TURN = 0;
    public final static float INTERVAL = 0.5f;
    public final static int MODE = Mode.SIMPLE.getIndex();

    public static void main(String[] args) throws InterruptedException {

        boolean showHelp = args.length != 0 && !args[0].matches("\\d+");
        if (showHelp) {
            showHelp();
            return;
        }

        int height = getInt(0, args, HEIGHT);
        int width = getInt(1, args, WIDTH);
        float aliveProbability = getFloat(2, args, ALIVE_PROBABILITY);
        int ramdomCellsBornPerTurn = getInt(3, args, RANDOM_CELLS_BORN_PER_TURN);
        float interval = getFloat(4, args, INTERVAL);
        int mode = getInt(5, args, MODE);
        if (mode >= Mode.values().length) {
            mode = MODE;
        }
        start(height, width, aliveProbability, ramdomCellsBornPerTurn, interval, mode);
    }

    private static void showHelp() {
        String msg = "Usage: ./java -jar <file> [height] [width] [aliveProbability] [randomCellsBornPerTurn] [interval] [mode]";
        System.out.println(msg);

        System.out.println("Modes:");
        List<Mode> modes = Arrays.stream(Mode.values())
                                 .sorted(comparing(Mode::getIndex))
                                 .collect(toList());
        modes.forEach(Main::printMode);
    }

    private static int getInt(int index, String[] args, int defaultValue) {
        if (args.length <= index) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(args[index]);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static float getFloat(int index, String[] args, float defaultValue) {
        if (args.length <= index) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(args[index]);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static void start(int height, int width, float aliveProbability, int randomCellsBornPerTurn,
                              float interval, int modeIndex) throws InterruptedException {

        Mode mode = Mode.fromIndex(modeIndex);
        GameOfLife gol = mode.getGolGenerator()
                             .create(height, width, aliveProbability, randomCellsBornPerTurn, interval);
        gol.play(new ConsoleDisplay());
    }

    private static void printMode(Mode mode) {
        System.out.println(mode.getIndex() + ") " + mode.getDesc());
    }
}
