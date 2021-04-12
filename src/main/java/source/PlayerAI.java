
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

    public PlayerAI() {
    }

    public void initializePlayerAI(OrderedPair playerLocation) {
        setCharacterLocation(playerLocation);
        setCharacter("player");
    }

    public boolean isDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        PlayerAI.isDead = isDead;
    }
}
