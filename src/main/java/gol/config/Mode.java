package gol.config;

public enum Mode {

    SIMPLE(1, "Simple game of life", new SimpleGameOfLifeGenerator()),
    ROW_EXECUTOR(2, "Row executor game of life", new RowExecutorGameOfLifeGenerator()),
    BOARD_DIVIDER(2, "Board divider game of life", new BoardDividerGameOfLifeGenerator());

    private final int index;
    private final String desc;
    private final GameOfLifeGenerator golGenerator;

    Mode(int index, String desc, GameOfLifeGenerator golGenerator) {
        this.index = index;
        this.desc = desc;
        this.golGenerator = golGenerator;
    }

    public static Mode fromIndex(int index) {
        for (Mode mode : values()) {
            if (mode.index == index) {
                return mode;
            }
        }
        throw new IllegalArgumentException(Integer.toString(index));
    }

    public int getIndex() {
        return index;
    }

    public String getDesc() {
        return desc;
    }

    public GameOfLifeGenerator getGolGenerator() {
        return golGenerator;
    }
}
