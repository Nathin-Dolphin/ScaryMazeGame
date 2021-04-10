
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
     * <p>
     * {@code MazeVars.PLAYER}
     * <p>
     * {@code MazeVars.MONSTER}
     * <p>
     * {@code MazeVars.EXIT}
     */
    private char tileType;

    /**
     * The color of the tile.
     */
    private Color tileColor;

    private boolean isPlayer;

    private boolean visibleToPlayer;

    // private boolean isMonster;

    // private boolean visibleToMonster;

    public MazeTile(char tileType) throws InvalidTileTypeException {
        this.tileType = tileType;
        setTileColor();
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

    private void setTileColor() throws InvalidTileTypeException {
        switch (tileType) {
        case MazeVars.WALL:
            tileColor = MazeVars.WALL_COLOR;
            break;

        case MazeVars.HALLWAY:
            if (visibleToPlayer) {
                tileColor = MazeVars.HALLWAY_COLOR;
            } else {
                tileColor = MazeVars.WALL_COLOR;
            }
            break;

        case MazeVars.PLAYER:
            tileColor = MazeVars.PLAYER_COLOR;
            break;

        case MazeVars.MONSTER:
            tileColor = MazeVars.MONSTER_COLOR;
            break;

        case MazeVars.EXIT:
            tileColor = MazeVars.EXIT_COLOR;
            break;

        default:
            throw new InvalidTileTypeException("" + tileType);
        }
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

    private void changeTile(char tile) throws InvalidTileTypeException {
        this.tileType = tile;
        setTileColor();
        repaint();
    }

    public char getTileType() {
        return tileType;
    }

    public void setTileType(char tileType) throws InvalidTileTypeException {
        changeTile(tileType);
    }

    public Color getTileColor() {
        return tileColor;
    }

    /**
     * 
     * @param isPlayer
     * @throws InvalidTileTypeException
     */
    public void setIsPlayer(boolean isPlayer) throws InvalidTileTypeException {
        if (!this.isPlayer && compareTiles(MazeVars.HALLWAY)) {
            this.isPlayer = isPlayer;
            changeTile(MazeVars.PLAYER);

        } else if (this.isPlayer) {
            this.isPlayer = isPlayer;
            changeTile(MazeVars.HALLWAY);
        }
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    /**
     * 
     * @param visibleToPlayer
     * @throws InvalidTileTypeException
     */
    public void setVisibleToPlayer(boolean visibleToPlayer) throws InvalidTileTypeException {
        if (compareTiles(MazeVars.HALLWAY)) {
            this.visibleToPlayer = visibleToPlayer;
            changeTile(MazeVars.HALLWAY);
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
