
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

    /**
     * 
     */
    protected static final OrderedPair[] DIRECTIONS_LIST = new OrderedPair[] { MazeVars.EAST, MazeVars.NORTH,
            MazeVars.WEST, MazeVars.SOUTH, MazeVars.EAST, MazeVars.NORTH };

    /**
     * 
     */
    protected static MazeTile[][] maze;

    /**
     * 
     */
    protected static LinkedList<OrderedPair> tilesVisibleToPlayer;

    /**
     * 
     */
    protected OrderedPair entityLocation;

    /**
     * 
     */
    protected boolean outOfBounds;

    public MazeDetection() {
    }

    /**
     * 
     * @return
     */
    public boolean isValidMaze() {
        int[] westOfExit = new int[] { MazeVars.MAZE_WIDTH - 2, MazeVars.MAZE_HEIGHT - 1 };
        int[] northOfExit = new int[] { MazeVars.MAZE_WIDTH - 1, MazeVars.MAZE_HEIGHT - 2 };

        char a = maze[westOfExit[0]][westOfExit[1]].getTileType();
        char b = maze[northOfExit[0]][northOfExit[1]].getTileType();

        // return false if 'a' and 'b' are a wall
        if (Character.compare(MazeVars.WALL, a) == 0 && Character.compare(MazeVars.WALL, b) == 0) {
            return false;
        }

        return true;
    }

    /**
     * If one of the checks neither matches the inputted tile nor is out of bounds
     * of the maze, then it will return false.
     * 
     * @param pointOfReference
     * @param areaToCheck
     * @param tileType
     * @return
     */
    public boolean locationCheck(OrderedPair pointOfReference, OrderedPair[] areaToCheck, char tileType) {
        for (int i = 0; i < areaToCheck.length; i++) {
            // If the tiles match up, then continue to check the next location, else if they
            // do not match up and it is NOT out of bounds of the maze, then return false
            if (!locationCheck(pointOfReference.add(areaToCheck[i]), tileType)) {
                if (!outOfBounds) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 
     * @param locationToCheck
     * @param tileType
     * @return
     */
    public boolean locationCheck(OrderedPair locationToCheck, char tileType) {
        outOfBounds = false;

        try {
            if (maze[locationToCheck.getX()][locationToCheck.getY()].compareTiles(tileType)) {
                return true;
            }
        } catch (IndexOutOfBoundsException i) {
            outOfBounds = true;
        }
        return false;
    }

    /**
     * 
     * @param pointOfReference
     * @param direction
     * @param isMonster
     * @return
     */
    public boolean entityVisionCheck(OrderedPair pointOfReference, OrderedPair[] direction, boolean isMonster) {
        OrderedPair currentPos = pointOfReference;
        OrderedPair sidePos;

        // TODO: add ability to see diagonally

        for (int sidepath = 0; sidepath <= 2; sidepath = sidepath + 2) {
            sidePos = currentPos.add(direction[1].add(direction[sidepath]));

            while (locationCheck(sidePos, MazeVars.HALLWAY)) {
                if (checkForPlayer(sidePos)) {
                    return true;
                } else {
                    setVisibility(sidePos, true);
                }
                sidePos = sidePos.add(direction[sidepath]);
            }
        }

        while (locationCheck(currentPos.add(direction[1]), MazeVars.HALLWAY) && !outOfBounds) {
            currentPos = currentPos.add(direction[1]);

            if (checkForPlayer(currentPos)) {
                return true;
            } else {
                setVisibility(currentPos, true);
            }

            for (int sidepath = 0; sidepath <= 2; sidepath = sidepath + 2) {
                sidePos = currentPos.add(direction[sidepath]);

                if (locationCheck(sidePos, MazeVars.HALLWAY)) {
                    if (checkForPlayer(sidePos)) {
                        return true;
                    } else {
                        setVisibility(sidePos, false);
                    }
                }
            }
        }
        return false;
    }

    private boolean checkForPlayer(OrderedPair currentPos) {
        if (maze[currentPos.getX()][currentPos.getY()].isPlayer()) {
            return true;
        }
        return false;
    }

    private void setVisibility(OrderedPair currentPos, boolean isFullyVisible) {
        if (isFullyVisible) {
            maze[currentPos.getX()][currentPos.getY()].setVisibleToPlayer(true);

        } else {
            maze[currentPos.getX()][currentPos.getY()].setWasVisibleToPlayer(true);
        }
        tilesVisibleToPlayer.add(currentPos);
    }

    @Override
    public String toString() {
        String mazeString = "";

        for (int h = 0; h < maze[0].length; h++) {
            for (int w = 0; w < maze.length; w++) {
                mazeString += maze[w][h].getTileType() + "";
            }
            mazeString += "\n";
        }

        return mazeString;
    }
}
