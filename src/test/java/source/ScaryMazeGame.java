
/**
 * Copyright (c) 2020 Nathin-Dolphin.
 * 
 * This file is under the MIT License.
 */

package source;

/**
 * <p>
 * <b>Known Issues:</b>
 * <p>
 *  - The player can sometimes see through walls.
 * 
 * @author Nathin Wascher
 * @version v0.2 - 6th of April, 2021
 */
public final class ScaryMazeGame {

    private ScaryMazeGame() {
    }

    public static void main(String[] args) {
        System.out.println("Executing Program (ScaryMazeGame). . .\n");
        new ScaryMazeGameWindow();
    }
}
