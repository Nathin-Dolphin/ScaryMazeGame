
/**
 * Copyright (c) 2020 Nathin-Dolphin.
 * 
 * This file is under the MIT License.
 */

package source;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import source.utility.SimpleFrame;

/**
 * @author Nathin Wascher
 */
public class ScaryMazeGameWindow {

    private static final SimpleFrame FRAME = new SimpleFrame("ScaryMazeGame", "Spooky Maze");

    private static final MazeGenerator MAZE_GEN = new MazeGenerator();

    private static final int FAILSAFE_NUM = 100;

    private static PlayerAI playerAI;

    // private static MonsterAI monsterAI;

    private static ScheduledExecutorService executorService;

    private static WindowListener windowListener;

    private static KeyListener keyListener;

    /**
     * 
     */
    public ScaryMazeGameWindow() {
        playerAI = new PlayerAI();
        // monsterAI = new MonsterAI();

        generateMaze();
        implementListeners();
        setUpFrame();

        startGame();
    }

    private void generateMaze() {
        int failSafe = 0;

        do {
            MAZE_GEN.generateMaze();
            failSafe++;
            // System.out.println(MAZE_GEN.toString());

        } while (!MAZE_GEN.isValidMaze() && failSafe <= FAILSAFE_NUM);

        playerAI.initializePlayerAI(MAZE_GEN.getStartLocation());
        // have the monster initially spawn next to the exit
        // monsterAI.initializeMonsterAI(mazeGen.getExitLocation());

        System.out.println(MAZE_GEN.toString());
    }

    private void implementListeners() {
        windowListener = new WindowAdapter() {
            public void windowClosing(WindowEvent w) {
                executorService.shutdown();
            }
        };

        keyListener = new KeyAdapter() {
            public void keyReleased(KeyEvent k) {
                playerUpdate(k);
            }
        };
    }

    private void setUpFrame() {
        FRAME.add(playerAI.initializeFogOfWar());
        FRAME.addKeyListener(keyListener);
        FRAME.addWindowListener(windowListener);
        FRAME.setFullscreen(true); // TODO: Does not go in fullscreen
        FRAME.setVisible(true);
    }

    private void startGame() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(ScaryMazeGameWindow::monsterUpdate, 1, 1, TimeUnit.SECONDS);
    }

    private static void gameUpdate() {
        if (!executorService.isShutdown()) {
            playerAI.updateFogOfWar();
            System.out.println("game updated");
            // System.out.println(playerAI.toString());

            if (playerAI.isDead() || playerAI.onExit()) {
                // FRAME.removeKeyListener((keyListener));
                executorService.shutdown();
                System.out.println("Thread terminated");
            }
        }
    }

    private static void monsterUpdate() {
        // if (monsterAI.moveMonster()) {
        // gameUpdate();
        // }
    }

    private void playerUpdate(KeyEvent k) {
        if (k.getKeyCode() == KeyEvent.VK_UP || k.getKeyCode() == KeyEvent.VK_W) { // North
            if (playerAI.moveCharacter(MazeVars.NORTH)) {
                gameUpdate();
            }

        } else if (k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_A) { // West
            if (playerAI.moveCharacter(MazeVars.WEST)) {
                gameUpdate();
            }

        } else if (k.getKeyCode() == KeyEvent.VK_DOWN || k.getKeyCode() == KeyEvent.VK_S) { // South
            if (playerAI.moveCharacter(MazeVars.SOUTH)) {
                gameUpdate();
            }

        } else if (k.getKeyCode() == KeyEvent.VK_RIGHT || k.getKeyCode() == KeyEvent.VK_D) { // East
            if (playerAI.moveCharacter(MazeVars.EAST)) {
                gameUpdate();
            }
        }
    }
}
