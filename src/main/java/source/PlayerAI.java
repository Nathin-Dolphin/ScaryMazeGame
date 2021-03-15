
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
public class PlayerAI extends MazeDetection {
    private static boolean isDead;
    private static boolean reachedExit;

    public PlayerAI() {
    }

    public boolean isDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        PlayerAI.isDead = isDead;
    }

    public boolean getReachedExit() {
        return reachedExit;
    }

    public void setReachedExit(boolean reachedExit) {
        PlayerAI.reachedExit = reachedExit;
    }

    public void initializePlayerAI(OrderedPair playerLocation, String[][] maze) {
        setCharacterLocation(playerLocation);
        // setMaze(maze);
    }

    /**
     * 
     * @return
     */
    public boolean moveUp() {
        if (locationCheck(getCharacterLocation(), 0, -1, HALLWAY)) {

            return true;
        }
        return false;
    }
}
