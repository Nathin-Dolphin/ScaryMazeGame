
/**
 * Copyright (c) 2020 Nathin-Dolphin.
 * 
 * This file is under the MIT License.
 */

package source;

import java.util.LinkedList;

import source.utility.OrderedPair;

/**
 * @author Nathin Wascher
 */
public class MazeDetection {

    private static String[][] maze;

    private static MazeTile[][] mazeTiles;

    // private LinkedList<String> testList;

    private String[][] mazeFOW;

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
        if (locationCheck(characterLocation, direction, MazeVars.HALLWAY)) {

            maze[characterLocation.getX()][characterLocation.getY()] = MazeVars.HALLWAY;
            characterLocation = characterLocation.add(direction);
            maze[characterLocation.getX()][characterLocation.getY()] = character;

            return true;
        }
        return false;
    }

    /**
     * 
     * @return
     */
    public String getMazeWithFogOfWar() {
        mazeFOW = new String[MazeVars.MAZE_WIDTH][MazeVars.MAZE_HEIGHT];

        for (int w = 0; w < MazeVars.MAZE_WIDTH; w++) {
            for (int h = 0; h < MazeVars.MAZE_HEIGHT; h++) {
                mazeFOW[w][h] = maze[w][h];
            }
        }

        /**
         * characterVision(MazeVars.NORTH); characterVision(MazeVars.WEST);
         * characterVision(MazeVars.SOUTH); characterVision(MazeVars.EAST);
         */

        test(characterLocation, MazeVars.NORTH);
        test(characterLocation, MazeVars.WEST);
        test(characterLocation, MazeVars.SOUTH);
        test(characterLocation, MazeVars.EAST);
        test2();

        for (int h = 0; h < mazeFOW[0].length; h++) {
            for (int w = 0; w < mazeFOW.length; w++) {
                if (mazeFOW[w][h].equals(MazeVars.HALLWAY)) {
                    mazeFOW[w][h] = MazeVars.WALL;

                } else if (mazeFOW[w][h].equals(MazeVars.TEMP_SYMBOL)) {
                    mazeFOW[w][h] = MazeVars.HALLWAY;
                }
            }
        }

        return mazeToString(mazeFOW);
    }

    // private void characterVision(OrderedPair direction) {}

    // TODO: change method name
    private void test(OrderedPair currentPos, OrderedPair direction) {
        OrderedPair newPos = currentPos;
        while (locationCheck(newPos, direction, MazeVars.HALLWAY) && !outOfBounds) {
            if (!outOfBounds) { // TODO: Necessary ???
                newPos = newPos.add(direction);
                mazeFOW[newPos.getX()][newPos.getY()] = MazeVars.TEMP_SYMBOL;
            }
        }
    }

    // TODO: change method name
    // TODO: can see corners that are not immediately accessable
    // TODO: show a single unit of MV.hallway stemming from the MV.hallway the
    // character
    // is in
    private void test2() {
        OrderedPair currentPos = characterLocation;

        if (locationCheck(currentPos, MazeVars.NORTH_WEST, MazeVars.HALLWAY)) {
            currentPos = currentPos.add(MazeVars.NORTH_WEST);
            mazeFOW[currentPos.getX()][currentPos.getY()] = MazeVars.TEMP_SYMBOL;
            test(currentPos, MazeVars.NORTH);
            test(currentPos, MazeVars.WEST);
        }

        currentPos = characterLocation;
        if (locationCheck(currentPos, MazeVars.NORTH_EAST, MazeVars.HALLWAY)) {
            currentPos = currentPos.add(MazeVars.NORTH_EAST);
            mazeFOW[currentPos.getX()][currentPos.getY()] = MazeVars.TEMP_SYMBOL;
            test(currentPos, MazeVars.NORTH);
            test(currentPos, MazeVars.EAST);
        }

        currentPos = characterLocation;
        if (locationCheck(currentPos, MazeVars.SOUTH_WEST, MazeVars.HALLWAY)) {
            currentPos = currentPos.add(MazeVars.SOUTH_WEST);
            mazeFOW[currentPos.getX()][currentPos.getY()] = MazeVars.TEMP_SYMBOL;
            test(currentPos, MazeVars.SOUTH);
            test(currentPos, MazeVars.WEST);
        }

        currentPos = characterLocation;
        if (locationCheck(currentPos, MazeVars.SOUTH_EAST, MazeVars.HALLWAY)) {
            currentPos = currentPos.add(MazeVars.SOUTH_EAST);
            mazeFOW[currentPos.getX()][currentPos.getY()] = MazeVars.TEMP_SYMBOL;
            test(currentPos, MazeVars.SOUTH);
            test(currentPos, MazeVars.EAST);
        }
    }

    private String mazeToString(String[][] tempMaze) {
        String mazeString = "";

        for (int h = 0; h < tempMaze[0].length; h++) {
            for (int w = 0; w < tempMaze.length; w++) {
                mazeString += tempMaze[w][h] + "";
            }
            mazeString += "\n";
        }

        return mazeString;
    }

    @Override
    public String toString() {
        return mazeToString(maze);
    }
}
