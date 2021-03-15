
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

    public void initializeMonsterAI(OrderedPair monsterLocation, String[][] maze) {
        setCharacterLocation(monsterLocation);
        // setMaze(maze);
    }

    public boolean moveMonster() {

        return false;
    }
}
