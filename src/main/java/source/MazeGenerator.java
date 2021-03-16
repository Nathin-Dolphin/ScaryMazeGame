
/**
 * Copyright (c) 2020 Nathin-Dolphin.
 * 
 * This file is under the MIT License.
 */

package source;

import java.util.LinkedList;

import source.utility.OrderedPair;
import source.utility.RandomGen;

/**
 * @author Nathin Wascher
 */
public class MazeGenerator extends MazeDetection {
    private static final RandomGen GEN = new RandomGen();

    // Put the these in MazeDetection ???? maybe
    private static final int MAZE_HEIGHT = 10;
    private static final int MAZE_WIDTH = 30;

    private static final OrderedPair START_POS = new OrderedPair(0, 0);
    private static final OrderedPair EXIT_POS = new OrderedPair(MAZE_WIDTH - 1, MAZE_HEIGHT - 1);

    private static LinkedList<String> chanceList;

    private static String[][] mgMaze;

    private static OrderedPair currentPos;

    private static String currentPlacementPos;

    public MazeGenerator() {
        mgMaze = new String[MAZE_WIDTH][MAZE_HEIGHT];
    }

    public OrderedPair getStartLocation() {
        return START_POS;
    }

    public OrderedPair getExitLocation() {
        return EXIT_POS;
    }

    /**
     * 
     * @return
     */
    public String[][] generateMaze() {
        for (int i = 0; i < MAZE_WIDTH; i++) {
            for (int h = 0; h < MAZE_HEIGHT; h++) {
                mgMaze[i][h] = WALL;
            }
        }

        createMainPath();

        mgMaze[0][0] = PLAYER;
        mgMaze[MAZE_WIDTH - 1][MAZE_HEIGHT - 1] = EXIT;
        setMaze(mgMaze);

        return mgMaze;
    }

    //
    private void createMainPath() {
        chanceList = new LinkedList<String>();
        currentPos = START_POS;
        int index = 0;

        if (GEN.boolGen()) {
            currentPlacementPos = "E";
        } else {
            currentPlacementPos = "S";
        }

        int failSafe = 0;
        while (!currentPos.equals(EXIT_POS) && failSafe < (MAZE_HEIGHT * MAZE_WIDTH) / 4) {
            setMaze(mgMaze);
            failSafe++;

            possibleHallwayPlacementCheck();

            if (chanceList.size() == 0) {
                failSafe = MAZE_HEIGHT * MAZE_WIDTH;
                break;
            }

            index = GEN.intGen(0, chanceList.size() - 1);
            currentPlacementPos = chanceList.get(index);

            switch (currentPlacementPos) {

            case "N":
                if (locationCheck(currentPos, NORTH_CHECK, WALL)) {
                    changeToHallway(NORTH);
                }
                break;

            case "W":
                if (locationCheck(currentPos, WEST_CHECK, WALL)) {
                    changeToHallway(WEST);
                }
                break;

            case "S":
                if (locationCheck(currentPos, SOUTH_CHECK, WALL)) {
                    changeToHallway(SOUTH);
                }
                break;

            case "E":
                if (locationCheck(currentPos, EAST_CHECK, WALL)) {
                    changeToHallway(EAST);
                }
                break;
            default:
            }
        }

        if (failSafe >= (MAZE_HEIGHT * MAZE_WIDTH) / 4) {
            System.out.println("\nERROR: " + failSafe + ": FAILSAFE LIMIT REACHED");
        }
    }

    // private void createPathBranch() {
    // }

    private void changeToHallway(OrderedPair direction) {
        currentPos = currentPos.add(direction);
        mgMaze[currentPos.getX()][currentPos.getY()] = HALLWAY;
    }

    private void possibleHallwayPlacementCheck() {
        chanceList.clear();

        // The first 'if' statement detects if the location to chack is inside the
        // boundaries of the maze. The second 'if' checks if it is a wall and if so add
        // the appropriate direction to the 'chanceList'
        if (0 <= currentPos.getY() - 1) {
            if (locationCheck(currentPos, NORTH, WALL)) { // North
                for (int i = 0; i < 2; i++) {
                    chanceList.add("N");
                }
                if ("N".equals(currentPlacementPos)) {
                    chanceList.add("N");
                }
            }
        }
        if (0 <= currentPos.getX() - 1) {
            if (locationCheck(currentPos, WEST, WALL)) { // West
                for (int i = 0; i < 2; i++) {
                    chanceList.add("W");
                }
                if ("W".equals(currentPlacementPos)) {
                    chanceList.add("W");
                }
            }
        }
        if (currentPos.getY() + 1 < MAZE_HEIGHT) {
            if (locationCheck(currentPos, SOUTH, WALL)) { // South
                chanceList.add("S");

                for (int i = 0; i < 2; i++) {
                    chanceList.add("S");
                }
                if ("S".equals(currentPlacementPos)) {
                    chanceList.add("S");
                }
            }
        }
        if (currentPos.getX() + 1 < MAZE_WIDTH) {
            if (locationCheck(currentPos, EAST, WALL)) { // East
                chanceList.add("E");

                for (int i = 0; i < 2; i++) {
                    chanceList.add("E");
                }
                if ("E".equals(currentPlacementPos)) {
                    chanceList.add("E");
                }
            }
        }
    }
}
