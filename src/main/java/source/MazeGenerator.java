
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

    private static final int SIDE_PATH_CHANCE = 15;

    private static LinkedList<LinkedList<String>> directionChanceList;

    private static LinkedList<OrderedPair> currentPosList;

    private static MazeTile[][] mgMaze;

    public MazeGenerator() {
        mgMaze = new MazeTile[MazeVars.MAZE_WIDTH][MazeVars.MAZE_HEIGHT];
    }

    public OrderedPair getStartLocation() {
        return MazeVars.START_POS;
    }

    public OrderedPair getExitLocation() {
        return MazeVars.EXIT_POS;
    }

    /**
     * 
     */
    public void generateMaze() {
        for (int w = 0; w < MazeVars.MAZE_WIDTH; w++) {
            for (int h = 0; h < MazeVars.MAZE_HEIGHT; h++) {
                mgMaze[w][h] = new MazeTile(MazeVars.WALL);
            }
        }

        initializeLists();
        createMainPath();

        mgMaze[0][0].setTileType(MazeVars.PLAYER);
        mgMaze[MazeVars.MAZE_WIDTH - 1][MazeVars.MAZE_HEIGHT - 1].setTileType(MazeVars.EXIT);
        setMaze(mgMaze);
    }

    private void initializeLists() {
        directionChanceList = new LinkedList<LinkedList<String>>();
        directionChanceList.add(new LinkedList<String>());

        currentPosList = new LinkedList<OrderedPair>();
        currentPosList.add(MazeVars.START_POS);
    }

    private void createMainPath() {
        LinkedList<String> directionChance;
        String direction;

        int failSafe = 0;
        int index = 0;

        while (!currentPosList.contains(MazeVars.EXIT_POS) && failSafe < (MazeVars.MAZE_HEIGHT * MazeVars.MAZE_WIDTH)) {
            for (int path = 0; path < currentPosList.size(); path++) {
                setMaze(mgMaze);
                failSafe++;

                possibleHallwayPlacementCheck(path);
                directionChance = directionChanceList.get(path);

                if (directionChance.size() == 0) {
                    System.out.println("\n\tWARNING: PATH CAN NOT CONTINUE");

                    currentPosList.remove(path);
                    directionChanceList.remove(path);

                    if (directionChanceList.size() == 0) {
                        failSafe = (MazeVars.MAZE_WIDTH * MazeVars.MAZE_HEIGHT) * 2;
                    }

                    break;
                }

                index = GEN.intGen(0, directionChance.size() - 1);
                direction = directionChance.get(index);

                switch (direction) {

                case "N":
                    changeToHallway(MazeVars.NORTH, path);
                    break;

                case "W":
                    changeToHallway(MazeVars.WEST, path);
                    break;

                case "S":
                    changeToHallway(MazeVars.SOUTH, path);
                    break;

                case "E":
                    changeToHallway(MazeVars.EAST, path);
                    break;

                default:
                    System.out.println("\nERROR: \'" + direction + "\' IS NOT A VALID DIRECTION ");
                }

                // Disabled
                if (GEN.intGen(0, SIDE_PATH_CHANCE) == -1) {
                    createSidePath(path);
                }
            }
        }

        if (failSafe >= (MazeVars.MAZE_HEIGHT * MazeVars.MAZE_WIDTH)) {
            System.out.println("\nERROR: " + failSafe + ": FAILSAFE LIMIT REACHED");
        }
    }

    private void createSidePath(int path) {
        int pathNum = path;
        OrderedPair currentPos = currentPosList.get(pathNum);
        LinkedList<String> directionChance = directionChanceList.get(pathNum);
        String direction;

        currentPosList.add(currentPos);
        directionChanceList.add(directionChance);

        pathNum++;

        possibleHallwayPlacementCheck(pathNum);
        directionChance = directionChanceList.get(pathNum);

        if (directionChance.size() == 0) {
            System.out.println("\n\tWARNING: SIDE PATH IS UNABLE TO BE CREATED");

            currentPosList.removeLast();
            directionChanceList.removeLast();

            return;
        }

        int index = GEN.intGen(0, directionChance.size() - 1);
        direction = directionChance.get(index);

        switch (direction) {

        case "N":
            changeToHallway(MazeVars.NORTH, pathNum);
            break;

        case "W":
            changeToHallway(MazeVars.WEST, pathNum);
            break;

        case "S":
            changeToHallway(MazeVars.SOUTH, pathNum);
            break;

        case "E":
            changeToHallway(MazeVars.EAST, pathNum);
            break;

        default:
            System.out.println("\nERROR: \'" + direction + "\' IS NOT A VALID DIRECTION ");
        }
    }

    private void changeToHallway(OrderedPair direction, int path) {
        OrderedPair currentPos = currentPosList.get(path);
        currentPos = currentPos.add(direction);

        currentPosList.remove(path);
        currentPosList.add(path, currentPos);

        mgMaze[currentPos.getX()][currentPos.getY()].setTileType(MazeVars.HALLWAY);
    }

    private void possibleHallwayPlacementCheck(int path) {
        OrderedPair currentPos = currentPosList.get(path);
        LinkedList<String> directionChance = new LinkedList<String>();

        // The first 'if' statement detects if the location to chack is inside the
        // boundaries of the maze. The second 'if' checks if it is a MazeVars.wall and
        // if so add
        // the appropriate direction to the 'chanceList'
        if (0 <= currentPos.getY() - 1) {
            if (locationCheck(currentPos, MazeVars.NORTH_CHECK, MazeVars.WALL)) { // North
                for (int i = 0; i < 2; i++) {
                    directionChance.add("N");
                }
            }
        }
        if (0 <= currentPos.getX() - 1) {
            if (locationCheck(currentPos, MazeVars.WEST_CHECK, MazeVars.WALL)) { // West
                for (int i = 0; i < 2; i++) {
                    directionChance.add("W");
                }
            }
        }
        if (currentPos.getY() + 1 < MazeVars.MAZE_HEIGHT) {
            if (locationCheck(currentPos, MazeVars.SOUTH_CHECK, MazeVars.WALL)) { // South
                directionChance.add("S");

                for (int i = 0; i < 2; i++) {
                    directionChance.add("S");
                }
            }
        }
        if (currentPos.getX() + 1 < MazeVars.MAZE_WIDTH) {
            if (locationCheck(currentPos, MazeVars.EAST_CHECK, MazeVars.WALL)) { // East
                directionChance.add("E");

                for (int i = 0; i < 2; i++) {
                    directionChance.add("E");
                }
            }
        }

        directionChanceList.remove(path);
        directionChanceList.add(path, directionChance);
    }
}
