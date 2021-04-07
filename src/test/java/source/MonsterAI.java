
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
        setCharacterLocation(monsterLocation);
    }
}
