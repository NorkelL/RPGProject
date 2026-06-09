package entities.util;

public enum Direction {
    EAST(0),
    SOUTH(1),
    WEST(2),
    NORTH(3);

    private final int value;

    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getRotation() {
        return value * 90;
    }

    public static Direction getDirectionByRotation(int rotation) {
        rotation = rotation % 360;
        int value = rotation / 90;
        return Direction.byValue(value);
    }

    public static Direction byValue(int value) {
        value = value < 0 ? value + 4 : value;
        value = value % 4;
        switch (value) {
            case 0: return EAST;
            case 1: return SOUTH;
            case 2: return WEST;
            default: return NORTH;
        }
    }
}
