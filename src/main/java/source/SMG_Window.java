
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
public class SMG_Window implements DocumentListener {
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
    public SMG_Window() {
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

        playerAI.initializePlayerAI(MAZE_GEN.getStartLocation(), maze);
        // monsterAI.initializeMonsterAI(mazeGen.getExitLocation(), maze);

        printMaze();
    }

    private static String printMaze() {
        mazeString = "";
        System.out.print("\n");

        for (int i = 0; i < maze.length; i++) {
            for (int h = 0; h < maze[0].length; h++) {
                System.out.print(maze[i][h] + "");
                mazeString += maze[i][h] + "";
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
        executorService.scheduleAtFixedRate(SMG_Window::monsterUpdate, 1, 1, TimeUnit.SECONDS);
    }

    private static void gameUpdate() {
        if (playerAI.isDead() || playerAI.getReachedExit()) {
            executorService.shutdown();

        } else {
            // playerAI.maze = maze;
            // monsterAI.setMaze(maze);

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
        if (k.getKeyCode() == KeyEvent.VK_UP) {
            if (playerAI.moveUp()) {
                // maze = playerAI.maze;
                gameUpdate();
            }

        } else if (k.getKeyCode() == KeyEvent.VK_LEFT) {
            return;

        } else if (k.getKeyCode() == KeyEvent.VK_DOWN) {
            return;

        } else if (k.getKeyCode() == KeyEvent.VK_RIGHT) {
            return;

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
