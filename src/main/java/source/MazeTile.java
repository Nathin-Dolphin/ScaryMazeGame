
/**
 * Copyright (c) 2020 Nathin-Dolphin.
 * 
 * This file is under the MIT License.
 */

package source;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * @author Nathin Wascher
 */
public class MazeTile extends JComponent {

    /**
     */
    private static final long serialVersionUID = -8218783969461452328L;

    /**
     * TileType is only allowed to be one of the following:
     * <p>
     * {@code MazeVars.WALL}
     * <p>
     * {@code MazeVars.HALLWAY}
     */
    private char tileType;

    /**
     * The color of the tile.
     */
    private Color tileColor;

    private boolean isPlayer;

    private boolean visibleToPlayer;

    private boolean wasVisibleToPlayer;

    private boolean isMonster;

    private boolean visibleToMonster;

    private boolean isExit;

    public MazeTile(char tileType) throws InvalidTileTypeException {
        changeTile(tileType);
    }

    /**
     * 
     * @param g
     */
    public void paint(Graphics g) {
        g.setColor(tileColor);

        g.fillRect(0, 0, getWidth() + 1, getHeight() + 1);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void changeTile(char tile) throws InvalidTileTypeException {
        this.tileType = tile;
        setTileColor();
    }

    private void setTileColor() throws InvalidTileTypeException {
        switch (tileType) {
        case MazeVars.WALL:
            tileColor = MazeVars.WALL_COLOR;
            break;

        case MazeVars.HALLWAY:
            if (isPlayer) {
                tileColor = MazeVars.PLAYER_COLOR;

            } else if (isExit) {
                if (visibleToPlayer && isMonster) {
                    tileColor = MazeVars.MONSTER_COLOR;

                } else {
                    tileColor = MazeVars.EXIT_COLOR;
                }

            } else if (visibleToPlayer) {
                tileColor = MazeVars.HALL_COLOR;
                wasVisibleToPlayer = true;

            } else if (wasVisibleToPlayer) {
                tileColor = MazeVars.REMEMBER_HALL_COLOR;

            } else {
                tileColor = MazeVars.WALL_COLOR;
            }
            break;

        default:
            throw new InvalidTileTypeException("" + tileType);
        }
        repaint();
    }

    /**
     * @param tile
     * @return Returns true if the parameter {@code tile} matches this object's
     *         internal {@code tileType}. Returns false otherwise.
     */
    public boolean compareTiles(char tile) {
        if (Character.compare(tileType, tile) == 0) {
            return true;
        }
        return false;
    }

    public char getTileType() {
        return tileType;
    }

    /**
     * Only use this method to change the tile to a wall or hallway. To change what
     * character is on this tile, use {@code setIsPlayer(bool)}.
     * 
     * @param tileType
     * @throws InvalidTileTypeException
     * 
     * @see #setIsPlayer(boolean isPLayer)
     */
    public void setTileType(char tileType) throws InvalidTileTypeException {
        changeTile(tileType);
    }

    public Color getTileColor() {
        return tileColor;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    /**
     * 
     * @param isPlayer
     * @throws InvalidTileTypeException
     */
    public void setIsPlayer(boolean isPlayer) throws InvalidTileTypeException {
        if (compareTiles(MazeVars.HALLWAY)) {
            this.isPlayer = isPlayer;
            visibleToPlayer = true;
            setTileColor();
        }
    }

    public boolean getVisibleToPlayer() {
        return visibleToPlayer;
    }

    /**
     * 
     * @param visibleToPlayer
     * @throws InvalidTileTypeException
     */
    public void setVisibleToPlayer(boolean visibleToPlayer) throws InvalidTileTypeException {
        if (compareTiles(MazeVars.HALLWAY)) {
            this.visibleToPlayer = visibleToPlayer;
            setTileColor();
        }
    }

    /**
     * 
     * @return
     */
    public boolean getWasVisibleToPlayer() {
        return wasVisibleToPlayer;
    }

    /**
     * 
     * @param wasVisibleToPlayer
     */
    public void setWasVisibleToPlayer(boolean wasVisibleToPlayer) {
        if (compareTiles(MazeVars.HALLWAY)) {
            this.wasVisibleToPlayer = wasVisibleToPlayer;
            setTileColor();
        }
    }

    public boolean isMonster() {
        return isMonster;
    }

    /**
     * 
     * @param isMonster
     */
    public void setIsMonster(boolean isMonster) {
        if (compareTiles(MazeVars.HALLWAY)) {
            this.isMonster = isMonster;
            visibleToMonster = true;
        }
    }

    /**
     * 
     * @param visibleToMonster
     */
    public void setVisibleToMonster(boolean visibleToMonster) {
        if (compareTiles(MazeVars.HALLWAY)) {
            this.visibleToMonster = visibleToMonster;
        }
    }

    public boolean isExit() {
        return isExit;
    }

    /**
     * 
     * @param isExit
     */
    public void setIsExit(boolean isExit) {
        if (compareTiles(MazeVars.HALLWAY)) {
            this.isExit = isExit;
            setTileColor();
        }
    }

    @Override
    public String toString() {
        String rgbString = "(" + tileColor.getRed() + ", " + tileColor.getGreen() + ", " + tileColor.getBlue() + ")";

        return tileType + ":" + rgbString;
    }

    @SuppressWarnings("serial")
    private class InvalidTileTypeException extends RuntimeException {
        InvalidTileTypeException(String str) {
            super("\'" + str + "\' is not a valid tileType");
        }
    }
}
