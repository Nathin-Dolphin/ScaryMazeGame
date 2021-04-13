
/**
 * Copyright (c) 2020 Nathin-Dolphin.
 * 
 * This file is under the MIT License.
 */

package source;

import java.awt.GridLayout;

import java.util.LinkedList;

import javax.swing.JPanel;

import source.utility.OrderedPair;

/**
 * @author Nathin Wascher
 */
public class PlayerAI extends MazeDetection {

    private static JPanel mazePanel;

    public PlayerAI() {
    }

    public void initializePlayerAI(OrderedPair playerLocation) {
        entityLocation = playerLocation;
    }

    /**
     * 
     * @param direction
     * @return
     */
    public boolean movePlayer(OrderedPair direction) {
        if (locationCheck(entityLocation.add(direction), MazeVars.HALLWAY)) {

            maze[entityLocation.getX()][entityLocation.getY()].setIsPlayer(false);
            entityLocation = entityLocation.add(direction);
            maze[entityLocation.getX()][entityLocation.getY()].setIsPlayer(true);

            return true;
        }
        return false;
    }

    /**
     * 
     * @return
     */
    public boolean isPlayerDead() {
        if (maze[entityLocation.getX()][entityLocation.getY()].isMonster()) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @return Returns true if the character is on the exit and false otherwise.
     */
    public boolean onExit() {
        if (maze[entityLocation.getX()][entityLocation.getY()].isExit()) {
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
        mazePanel.setBackground(MazeVars.WALL_COLOR);

        for (int h = 0; h < MazeVars.MAZE_HEIGHT; h++) {
            for (int w = 0; w < MazeVars.MAZE_WIDTH; w++) {
                mazePanel.add(maze[w][h]);
            }
        }

        updateFogOfWar();

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

        // For each direction on the compass (north, west, south, east)
        for (int listIndex = 1; listIndex < DIRECTIONS_LIST.length - 1; listIndex++) {
            currentPos = entityLocation;

            // Make an array of 3 adjacent directions (ex. west, south, and east)
            for (int arrayIndex = 0; arrayIndex < direction.length; arrayIndex++) {
                direction[arrayIndex] = DIRECTIONS_LIST[arrayIndex + (listIndex - 1)];
            }

            if (locationCheck(currentPos.add(direction[1]), MazeVars.HALLWAY) && !outOfBounds) {
                entityVisionCheck(currentPos, direction, false);
            }
        }
    }
}
