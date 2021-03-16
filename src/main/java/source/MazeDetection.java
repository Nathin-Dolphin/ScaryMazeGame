
/**
 * Copyright (c) 2020 Nathin-Dolphin.
 * 
 * This file is under the MIT License.
 */

package source;

import source.utility.OrderedPair;

/**
 * @author Nathin Wascher
 */
public class MazeDetection {
    /**
     */
    public static final OrderedPair NORTH = new OrderedPair(0, -1);

    /**
     */
    public static final OrderedPair WEST = new OrderedPair(-1, 0);

    /**
     */
    public static final OrderedPair SOUTH = new OrderedPair(0, 1);

    /**
     */
    public static final OrderedPair EAST = new OrderedPair(1, 0);

    /**
     */
    public static final OrderedPair[] NORTH_CHECK = new OrderedPair[] { NORTH, NORTH.add(NORTH), NORTH.add(EAST),
            NORTH.add(WEST) };

    /**
     */
    public static final OrderedPair[] WEST_CHECK = new OrderedPair[] { WEST, WEST.add(WEST), WEST.add(NORTH),
            WEST.add(SOUTH) };

    /**
     */
    public static final OrderedPair[] SOUTH_CHECK = new OrderedPair[] { SOUTH, SOUTH.add(SOUTH), SOUTH.add(EAST),
            SOUTH.add(WEST) };

    /**
     */
    public static final OrderedPair[] EAST_CHECK = new OrderedPair[] { EAST, EAST.add(EAST), EAST.add(NORTH),
            EAST.add(SOUTH) };

    /**
     */
    public static final String HALLWAY = " ";

    /**
     */
    public static final String WALL = "#";

    /**
     */
    public static final String EXIT = "X";

    /**
     */
    public static final String PLAYER = "O";

    /**
     */
    public static final String MONSTER = "&";

    private static String[][] maze;

    private OrderedPair characterLocation;

    private boolean outOfBounds;

    public MazeDetection() {
    }

    /**
     * 
     * @return
     */
    public OrderedPair getCharacterLocation() {
        return characterLocation;
    }

    /**
     * 
     * @param characterLocation
     */
    public void setCharacterLocation(OrderedPair characterLocation) {
        this.characterLocation = characterLocation;
    }

    /**
     * 
     * @return
     */
    public String[][] getMaze() {
        return maze;
    }

    /**
     * 
     * @param maze
     */
    public void setMaze(String[][] maze) {
        MazeDetection.maze = maze;
    }

    /**
     * 
     * @param currentPos
     * @param area
     * @param symbol
     * @return
     */
    public boolean locationCheck(OrderedPair currentPos, OrderedPair[] area, String symbol) {
        for (int i = 0; i < area.length; i++) {
            if (!locationCheck(currentPos, area[i], symbol)) {
                if (!outOfBounds) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 
     * @param currentPos
     * @param direction
     * @param symbol
     * @return
     */
    public boolean locationCheck(OrderedPair currentPos, OrderedPair direction, String symbol) {
        outOfBounds = false;

        try {
            OrderedPair newPosition = currentPos.add(direction);
            if (maze[newPosition.getX()][newPosition.getY()].equals(symbol)) {
                return true;
            }
        } catch (IndexOutOfBoundsException i) {
            outOfBounds = true;
        }
        return false;
    }

    /**
     * 
     * @param currentPos
     * @param xAxis
     * @param yAxis
     * @param symbol
     * @return
     */
    public boolean locationCheck(OrderedPair currentPos, int xAxis, int yAxis, String symbol) {
        try {
            OrderedPair newPosition = currentPos.add(xAxis, yAxis);
            if (maze[newPosition.getX()][newPosition.getY()].equals(symbol)) {
                return true;
            }
        } catch (IndexOutOfBoundsException i) {
        }
        return false;
    }

    /**
     * 
     * @param character
     * @param direction
     * @return
     */
    public boolean moveCharacter(String character, OrderedPair direction) {
        if (locationCheck(characterLocation, direction, HALLWAY)) {

            maze[characterLocation.getX()][characterLocation.getY()] = HALLWAY;
            characterLocation = characterLocation.add(direction);
            maze[characterLocation.getX()][characterLocation.getY()] = character;

            return true;
        }
        return false;
    }
}
