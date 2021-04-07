
/**
 * Copyright (c) 2020 Nathin-Dolphin.
 * 
 * This file is under the MIT License.
 */

package source;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author Nathin Wascher
 */
public class MazeTile extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -8218783969461452328L;

    private String symbol;

    private Color tileColor;

    public MazeTile(String symbol, Color tileColor) {
        this.symbol = symbol;
        this.tileColor = tileColor;
    }

    /**
     * 
     * @param g
     */
    public void paint(Graphics g) {
        g.setColor(tileColor);

        // TODO: Have width and height get determined by size of its JPanel
        g.fillRect(0, 0, 10, 10);
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Color getTileColor() {
        return tileColor;
    }

    public void setTileColor(Color tileColor) {
        this.tileColor = tileColor;
    }

}
