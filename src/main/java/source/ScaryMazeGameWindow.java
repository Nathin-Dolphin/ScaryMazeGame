
/**
 * Copyright (c) 2020 Nathin-Dolphin.
 * 
 * This file is under the MIT License.
 */

package source;

import java.awt.Font;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextArea;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import source.utility.SimpleFrame;

/**
 * @author Nathin Wascher
 */
public class ScaryMazeGameWindow implements DocumentListener {
    private static final SimpleFrame FRAME = new SimpleFrame("ScaryMazeGame", "Spooky Maze");

    private static final MazeGenerator MAZE_GEN = new MazeGenerator();

    private static PlayerAI playerAI;

    // private static MonsterAI monsterAI;

    private static ScheduledExecutorService executorService;

    private static WindowListener windowListener;

    private static KeyListener keyListener;

    private static JTextArea textArea;

    private static String[][] maze;

    private static String mazeString;

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
        do {
            maze = MAZE_GEN.generateMaze();

        } while (maze[maze.length - 2][maze[0].length - 1].equals(MazeDetection.WALL)
                && maze[maze.length - 1][maze[0].length - 2].equals(MazeDetection.WALL));

        playerAI.initializePlayerAI(MAZE_GEN.getStartLocation());
        // have the monster initially spawn next to the exit
        // monsterAI.initializeMonsterAI(mazeGen.getExitLocation());

        printMaze();
    }

    private static String printMaze() {
        mazeString = "";
        System.out.print("\n");

        for (int h = 0; h < maze[0].length; h++) {
            for (int w = 0; w < maze.length; w++) {
                System.out.print(maze[w][h] + "");
                mazeString += maze[w][h] + "";
            }
            System.out.print("\n");
            mazeString += "\n";
        }

        return mazeString;
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
        textArea = new JTextArea(mazeString);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 5 * 3));

        // textArea.getDocument().addDocumentListener(this);
        textArea.addKeyListener(keyListener);

        FRAME.add(textArea);
        FRAME.addWindowListener(windowListener);
        FRAME.setFullscreen(true);
        FRAME.setVisible(true);
    }

    private void startGame() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(ScaryMazeGameWindow::monsterUpdate, 1, 1, TimeUnit.SECONDS);
    }

    private static void gameUpdate() {
        if (playerAI.isDead() || playerAI.getReachedExit()) {
            executorService.shutdown();

        } else {
            textArea.setText(printMaze());
        }
        System.out.println("game updated");
    }

    private static void monsterUpdate() {
        // if (monsterAI.moveMonster()) {
        // gameUpdate();
        // }
    }

    private void playerUpdate(KeyEvent k) {
        if (k.getKeyCode() == KeyEvent.VK_UP || k.getKeyCode() == KeyEvent.VK_W) { // North
            if (playerAI.moveCharacter(MazeDetection.PLAYER, MazeDetection.NORTH)) {
                gameUpdate();
            }
            // check if space is exit here?????

        } else if (k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_A) { // West
            if (playerAI.moveCharacter(MazeDetection.PLAYER, MazeDetection.WEST)) {
                gameUpdate();
            }

        } else if (k.getKeyCode() == KeyEvent.VK_DOWN || k.getKeyCode() == KeyEvent.VK_S) { // South
            if (playerAI.moveCharacter(MazeDetection.PLAYER, MazeDetection.SOUTH)) {
                gameUpdate();
            }

        } else if (k.getKeyCode() == KeyEvent.VK_RIGHT || k.getKeyCode() == KeyEvent.VK_D) { // East
            if (playerAI.moveCharacter(MazeDetection.PLAYER, MazeDetection.EAST)) {
                gameUpdate();
            }

        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}