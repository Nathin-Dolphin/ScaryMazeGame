
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

    private static final OrderedPair[] DIRECTIONS_LIST = new OrderedPair[] { MazeVars.EAST, MazeVars.NORTH,
            MazeVars.WEST, MazeVars.SOUTH, MazeVars.EAST, MazeVars.NORTH };

    private static MazeTile[][] maze;

    private static LinkedList<OrderedPair> tilesVisibleToPlayer;

    private static JPanel mazePanel;

    private OrderedPair characterLocation;

    private String character;

    private boolean outOfBounds;

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
     * 
     * @param pointOfReference
     * @param areaToCheck
     * @param tileType
     * @return
     */
    public boolean locationCheck(OrderedPair pointOfReference, OrderedPair[] areaToCheck, char tileType) {
        for (int i = 0; i < areaToCheck.length; i++) {
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
     * @param direction
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
     * @param tileType
     * @param direction
     * @return
     */
    public boolean moveCharacter(OrderedPair direction) {
        if (locationCheck(characterLocation.add(direction), MazeVars.HALLWAY)) {

            maze[characterLocation.getX()][characterLocation.getY()].setIsPlayer(false);
            characterLocation = characterLocation.add(direction);
            maze[characterLocation.getX()][characterLocation.getY()].setIsPlayer(true);

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

        for (int index = 0; index < tilesVisibleToPlayer.size(); index++) {
            currentPos = tilesVisibleToPlayer.get(index);
            maze[currentPos.getX()][currentPos.getY()].setVisibleToPlayer(false);
        }
        tilesVisibleToPlayer.clear();

        for (int listIndex = 1; listIndex < DIRECTIONS_LIST.length - 1; listIndex++) {
            currentPos = characterLocation;

            for (int arrayIndex = 0; arrayIndex < direction.length; arrayIndex++) {
                direction[arrayIndex] = DIRECTIONS_LIST[arrayIndex + (listIndex - 1)];
            }

            if (locationCheck(currentPos.add(direction[1]), MazeVars.HALLWAY) && !outOfBounds) {
                playerVision(currentPos, direction);
            }
        }
    }

    private void playerVision(OrderedPair currentPos, OrderedPair[] direction) {
        OrderedPair sidePos;

        for (int sidepath = 0; sidepath <= 2; sidepath = sidepath + 2) {
            sidePos = currentPos.add(direction[1].add(direction[sidepath]));

            while (locationCheck(sidePos, MazeVars.HALLWAY)) {
                maze[sidePos.getX()][sidePos.getY()].setVisibleToPlayer(true);
                tilesVisibleToPlayer.add(sidePos);
                sidePos = sidePos.add(direction[sidepath]);
            }
        }

        while (locationCheck(currentPos.add(direction[1]), MazeVars.HALLWAY) && !outOfBounds) {
            currentPos = currentPos.add(direction[1]);
            maze[currentPos.getX()][currentPos.getY()].setVisibleToPlayer(true);
            tilesVisibleToPlayer.add(currentPos);

            for (int sidepath = 0; sidepath <= 2; sidepath = sidepath + 2) {
                sidePos = currentPos.add(direction[sidepath]);

                if (locationCheck(sidePos, MazeVars.HALLWAY)) {
                    maze[sidePos.getX()][sidePos.getY()].setVisibleToPlayer(true);
                    tilesVisibleToPlayer.add(sidePos);
                }
            }
        }
    }

    /**
     * 
     * @return Returns true if the character is on the exit and false otherwise.
     */
    public boolean onExit() {
        if (maze[characterLocation.getX()][characterLocation.getY()].isExit()) {
            return true;
        }
        return false;
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

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
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
