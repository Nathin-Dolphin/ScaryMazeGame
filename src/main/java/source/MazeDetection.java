
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
    public boolean locationCheck(OrderedPair currentPos, int[][] area, String symbol) {
        for (int i = 0; i < area.length; i++) {
            if (!locationCheck(currentPos, area[i][0], area[i][1], symbol)) {
                return false;
            }
        }

        return true;
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
            if (maze[currentPos.getX() + xAxis][currentPos.getY() + yAxis].equals(symbol)) {
                return true;
            }
        } catch (IndexOutOfBoundsException i) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @param xAxis
     * @param yAxis
     */
    public void moveCharacter(int xAxis, int yAxis) {
        maze[characterLocation.getX()][characterLocation.getY()] = HALLWAY;
        maze[characterLocation.getX() + xAxis][characterLocation.getY() + yAxis] = PLAYER;
    }
}
