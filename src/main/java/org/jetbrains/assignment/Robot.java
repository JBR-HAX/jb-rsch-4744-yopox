package org.jetbrains.assignment;

public class Robot {
    final static Robot robot = new Robot();

    private int x;
    private int y;

    public void move(Util.Direction dir, int steps) {
        switch (dir) {
            case NORTH:
                y += steps;
                break;
            case SOUTH:
                y -= steps;
                break;
            case EAST:
                x += steps;
                break;
            case WEST:
                x -= steps;
                break;
        }
    }

    public Coordinates getCoordinates() {
        return new Coordinates(x, y);
    }
}
