
/**
 * Copyright (c) 2020 Nathin-Dolphin.
 * 
 * This file is under the MIT License.
 */

package source;

import java.awt.Color;
import java.awt.GridLayout;

import java.util.LinkedList;

import javax.swing.JPanel;

import source.utility.OrderedPair;

/**
 * @author Nathin Wascher
 */
public class MazeDetection {

    private static final OrderedPair[] DIRECTIONS_LIST = new OrderedPair[] { MazeVars.EAST, MazeVars.NORTH, MazeVars.WEST,
            MazeVars.SOUTH, MazeVars.EAST, MazeVars.NORTH };

    private static MazeTile[][] maze;

    private static LinkedList<OrderedPair> tilesVisibleToPlayer;

    private static JPanel mazePanel;

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
    public MazeTile[][] getMaze() {
        return maze;
    }

    /**
     * 
     * @param maze
     */
    public void setMaze(MazeTile[][] maze) {
        MazeDetection.maze = maze;
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
     * 
     * @param currentPos
     * @param area
     * @param tileType
     * @return
     */
    public boolean locationCheck(OrderedPair currentPos, OrderedPair[] area, char tileType) {
        for (int i = 0; i < area.length; i++) {
            if (!locationCheck(currentPos, area[i], tileType)) {
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
     * @param tileType
     * @return
     */
    public boolean locationCheck(OrderedPair currentPos, OrderedPair direction, char tileType) {
        outOfBounds = false;

        try {
            OrderedPair newPosition = currentPos.add(direction);
            if (maze[newPosition.getX()][newPosition.getY()].compareTiles(tileType)) {
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
     * @param tileType
     * @return
     */
    public boolean locationCheck(OrderedPair currentPos, int xAxis, int yAxis, char tileType) {
        try {
            OrderedPair newPosition = currentPos.add(xAxis, yAxis);
            if (maze[newPosition.getX()][newPosition.getY()].compareTiles(tileType)) {
                return true;
            }
        } catch (IndexOutOfBoundsException i) {
        }
        return false;
    }

    /**
     * 
     * @param tileType
     * @param direction
     * @return
     */
    public boolean moveCharacter(char tileType, OrderedPair direction) {
        if (locationCheck(characterLocation, direction, MazeVars.HALLWAY)) {

            maze[characterLocation.getX()][characterLocation.getY()].setTileType(MazeVars.HALLWAY);
            characterLocation = characterLocation.add(direction);
            maze[characterLocation.getX()][characterLocation.getY()].setTileType(tileType);

            return true;
        }
        return false;
    }

    /**
     * 
     * @return
     */
    public JPanel initializeFogOfWar() {
        tilesVisibleToPlayer = new LinkedList<OrderedPair>();

        // height and width are reversed so the rows and
        // columns appear correctly on the panel
        mazePanel = new JPanel(new GridLayout(MazeVars.MAZE_HEIGHT, MazeVars.MAZE_WIDTH));
        mazePanel.setBackground(Color.GRAY);

        for (int h = 0; h < MazeVars.MAZE_HEIGHT; h++) {
            for (int w = 0; w < MazeVars.MAZE_WIDTH; w++) {
                mazePanel.add(maze[w][h]);
            }
        }

        updateFogOfWar();

        // for (int z = 0; z < mazePanel.getComponentCount(); z++) {
        // System.out.println(mazePanel.getComponent(z));
        // }

        return mazePanel;
    }

    /**
     * 
     * @return
     */
    public void updateFogOfWar() {
        OrderedPair[] direction = new OrderedPair[3];
        OrderedPair currentPos;
        OrderedPair sidePos;

        for (int q = 0; q < tilesVisibleToPlayer.size(); q++) {
            currentPos = tilesVisibleToPlayer.get(q);
            maze[currentPos.getX()][currentPos.getY()].setVisibleToPlayer(false);
        }

        tilesVisibleToPlayer.clear();

        for (int t = 1; t < DIRECTIONS_LIST.length - 1; t++) {
            currentPos = characterLocation;

            for (int f = 0; f < direction.length; f++) {
                direction[f] = DIRECTIONS_LIST[f + (t - 1)];
            }

            while (locationCheck(currentPos, direction[1], MazeVars.HALLWAY) && !outOfBounds) {
                if (!outOfBounds) { // TODO: Necessary ???
                    currentPos = currentPos.add(direction[1]);
                    maze[currentPos.getX()][currentPos.getY()].setVisibleToPlayer(true);
                    tilesVisibleToPlayer.add(currentPos);

                    if (locationCheck(currentPos, direction[0], MazeVars.HALLWAY)) {
                        sidePos = currentPos.add(direction[0]);
                        maze[sidePos.getX()][sidePos.getY()].setVisibleToPlayer(true);
                        tilesVisibleToPlayer.add(sidePos);
                    }

                    if (locationCheck(currentPos, direction[2], MazeVars.HALLWAY)) {
                        sidePos = currentPos.add(direction[2]);
                        maze[sidePos.getX()][sidePos.getY()].setVisibleToPlayer(true);
                        tilesVisibleToPlayer.add(sidePos);
                    }
                }
            }
        }
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
