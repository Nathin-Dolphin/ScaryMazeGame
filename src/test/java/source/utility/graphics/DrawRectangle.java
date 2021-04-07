
/**
 * Copyright (c) 2020 Nathin-Dolphin.
 * 
 * This file is under the MIT License.
 */

package source.utility.graphics;

import java.awt.Graphics;
import java.awt.Color;

/**
 * 
 * <p>
 * <b>No Known Issues.</b>
 * <p>
 * 
 * @author Nathin Wascher
 * @version v - , 2021
 */
public class DrawRectangle {
    private Color shapeColor;

    private int xPos;
    private int yPos;
    private int width;
    private int height;

    private boolean fillShape;

    public DrawRectangle() {
        shapeColor = Color.BLACK;
    }

    /**
     * 
     * @param xPos
     * @param yPos
     * @param width
     * @param height
     */
    public DrawRectangle(int xPos, int yPos, int width, int height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        shapeColor = Color.BLACK;
    }

    /**
     * 
     * @param g
     */
    public void paint(Graphics g) {
        g.setColor(shapeColor);

        if (fillShape) {
            g.fillRect(xPos, yPos, width, height);
        } else {
            g.drawRect(xPos, yPos, width, height);
        }
    }

    public Color getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(Color shapeColor) {
        this.shapeColor = shapeColor;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isFillShape() {
        return fillShape;
    }

    public void setFillShape(boolean fillShape) {
        this.fillShape = fillShape;
    }
}
