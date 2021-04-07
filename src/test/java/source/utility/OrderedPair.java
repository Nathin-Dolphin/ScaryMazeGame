
/**
 * Copyright (c) 2020 Nathin-Dolphin.
 * 
 * This file is under the MIT License.
 */

package source.utility;

/**
 * Instances of this class act as coordinates to a 2-dimensional plane, or in
 * the likely case, a 2-dimensional array.
 * 
 * <p>
 * <b>No Known Issues.</b>
 * <p>
 * 
 * @author Nathin Wascher
 * @version v1.0.1 - April 6, 2021
 */
public class OrderedPair {
    private int x;
    private int y;

    public OrderedPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String[] convertToArrayStrings() {
        return new String[] { Integer.toString(this.x), Integer.toString(this.y) };
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    /**
     * 
     * @param xPoint
     * @param yPoint
     * @return
     */
    public boolean equals(int xPoint, int yPoint) {
        if (this.x == xPoint && this.y == yPoint) {
            return true;
        }
        return false;
    }

    /**
     * 
     * This method does not directly change the values of either {@code OrderedPair}.
     * 
     * @param orderedPair
     * @return
     */
    public OrderedPair add(OrderedPair orderedPair) {
        return add(orderedPair.getX(), orderedPair.getY());
    }

    public OrderedPair add(int xPoint, int yPoint) {
        return new OrderedPair(this.x + xPoint, this.y + yPoint);
    }

    public OrderedPair subtract(OrderedPair orderedPair) {
        return subtract(orderedPair.getX(), orderedPair.getY());
    }

    public OrderedPair subtract(int xPoint, int yPoint) {
        return new OrderedPair(this.x + xPoint, this.y + yPoint);
    }

    public OrderedPair multiply(OrderedPair orderedPair) {
        return multiply(orderedPair.getX(), orderedPair.getY());
    }

    public OrderedPair multiply(int xPoint, int yPoint) {
        return new OrderedPair(this.x * xPoint, this.y * yPoint);
    }

    public OrderedPair divide(OrderedPair orderedPair) throws ArithmeticException {
        return divide(orderedPair.getX(), orderedPair.getY());
    }

    public OrderedPair divide(int xPoint, int yPoint) throws ArithmeticException {
        return new OrderedPair(this.x / xPoint, this.y / yPoint);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
