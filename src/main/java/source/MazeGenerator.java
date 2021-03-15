
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

    private static final int MAZE_HEIGHT = 10;
    private static final int MAZE_WIDTH = 30;

    private static final OrderedPair START_POS = new OrderedPair(0, 0);
    private static final OrderedPair EXIT_POS = new OrderedPair(MAZE_HEIGHT - 1, MAZE_WIDTH - 1);

    private static LinkedList<String> chanceList;

    private static String[][] mgMaze;

    private static OrderedPair currentPos;

    private static String currentPlacementPos = "E";

    public MazeGenerator() {
        mgMaze = new String[MAZE_HEIGHT][MAZE_WIDTH];
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
        for (int i = 0; i < MAZE_HEIGHT; i++) {
            for (int h = 0; h < MAZE_WIDTH; h++) {
                mgMaze[i][h] = WALL;
            }
        }

        createMainPath();

        mgMaze[0][0] = PLAYER;
        mgMaze[MAZE_HEIGHT - 1][MAZE_WIDTH - 1] = EXIT;
        setMaze(mgMaze);

        return mgMaze;
    }

    //
    private void createMainPath() {
        chanceList = new LinkedList<String>();
        currentPos = START_POS;
        int index = 0;

        int failSafe = 0;
        while (!currentPos.equals(EXIT_POS) && failSafe < (MAZE_WIDTH * MAZE_HEIGHT) / 4) {
            setMaze(mgMaze);
            failSafe++;

            possibleHallwayPlacementCheck();

            if (chanceList.size() == 0) {
                failSafe = MAZE_WIDTH * MAZE_HEIGHT;
                break;
            }

            index = GEN.intGen(0, chanceList.size() - 1);
            currentPlacementPos = chanceList.get(index);

            switch (currentPlacementPos) {

            case "N":
                if (locationCheck(currentPos, new int[][] { { 0, -1 }, { 1, -1 }, { 0, -1 - 1 }, { -1, -1 } }, WALL)) {
                    changeToHallway(0, -1);
                }
                break;

            case "W":
                if (locationCheck(currentPos, new int[][] { { -1, 0 }, { -1, 1 }, { -1 - 1, 0 }, { -1, -1 } }, WALL)) {
                    changeToHallway(-1, 0);
                }
                break;

            case "S":
                if (locationCheck(currentPos, new int[][] { { 0, 1 }, { 1, 1 }, { 0, 1 + 1 }, { -1, 1 } }, WALL)) {
                    changeToHallway(0, 1);
                }
                break;

            case "E":
                if (locationCheck(currentPos, new int[][] { { 1, 0 }, { 1, 1 }, { 1 + 1, 0 }, { 1, -1 } }, WALL)) {
                    changeToHallway(1, 0);
                }
                break;
            default:
            }
        }

        if (failSafe >= (MAZE_WIDTH * MAZE_HEIGHT) / 4) {
            System.out.println("\nERROR: " + failSafe + ": FAILSAFE LIMIT REACHED");
        }
    }

    // private void createPathBranch() {
    // }

    private void changeToHallway(int horizontalIncrement, int verticalIncrement) {
        currentPos = currentPos.add(horizontalIncrement, verticalIncrement);
        mgMaze[currentPos.getX()][currentPos.getY()] = HALLWAY;
    }

    private void possibleHallwayPlacementCheck() {
        chanceList.clear();

        if (0 <= currentPos.getY() - 1) {
            if (locationCheck(currentPos, 0, -1, WALL)) { // North
                for (int i = 0; i < 2; i++) {
                    chanceList.add("N");
                }
                if ("N".equals(currentPlacementPos)) {
                    chanceList.add("N");
                }
            }
        }
        if (0 <= currentPos.getX() - 1) {
            if (locationCheck(currentPos, -1, 0, WALL)) { // West
                for (int i = 0; i < 2; i++) {
                    chanceList.add("W");
                }
                if ("W".equals(currentPlacementPos)) {
                    chanceList.add("W");
                }
            }
        }
        if (currentPos.getY() + 1 < MAZE_WIDTH) {
            if (locationCheck(currentPos, 0, 1, WALL)) { // South
                chanceList.add("S");

                for (int i = 0; i < 2; i++) {
                    chanceList.add("S");
                }
                if ("S".equals(currentPlacementPos)) {
                    chanceList.add("S");
                }
            }
        }
        if (currentPos.getX() + 1 < MAZE_HEIGHT) {
            if (locationCheck(currentPos, 1, 0, WALL)) { // East
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
