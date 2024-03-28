package org.jetbrains.assignment;

public class Util {
    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    static Direction getDirection(Coordinates start, Coordinates end) {
        if (end.x() > start.x()) {
            return Direction.EAST;
        } else if (end.x() < start.x()) {
            return Direction.WEST;
        } else if (end.y() > start.y()) {
            return Direction.NORTH;
        } else {
            return Direction.SOUTH;
        }
    }

    static int calculateSteps(Coordinates start, Coordinates end) {
        return Math.max(Math.abs(end.x() - start.x()), Math.abs(end.y() - start.y()));
    }
}
