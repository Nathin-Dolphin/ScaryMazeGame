
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
public class MonsterAI extends MazeDetection {

    public MonsterAI() {
    }

    public void initializeMonsterAI(OrderedPair monsterLocation) {
        entityLocation = monsterLocation;
    }

    /**
     * 
     * @return
     */
    public boolean moveMonster() {

        // TODO: Put stuff here

        return true;
    }

    private boolean monsterVisionCheck() {
        OrderedPair[] direction = new OrderedPair[3];
        OrderedPair currentPos;

        // For each direction on the compass (north, west, south, east)
        for (int listIndex = 1; listIndex < DIRECTIONS_LIST.length - 1; listIndex++) {
            currentPos = entityLocation;

            // Make an array of 3 adjacent directions in order (ex. west, south, and east)
            for (int arrayIndex = 0; arrayIndex < direction.length; arrayIndex++) {
                direction[arrayIndex] = DIRECTIONS_LIST[arrayIndex + (listIndex - 1)];
            }

            if (locationCheck(currentPos.add(direction[1]), MazeVars.HALLWAY) && !outOfBounds) {
                return entityVisionCheck(currentPos, direction, true);
            }
        }
        return false;
    }
}
