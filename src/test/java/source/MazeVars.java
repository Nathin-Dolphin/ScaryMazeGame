
/**
 * Copyright (c) 2020 Nathin-Dolphin.
 * 
 * This file is under the MIT License.
 */

package source;

import java.awt.Color;

import source.utility.OrderedPair;

/**
 * @author Nathin Wascher
 */
public final class MazeVars {

        /**
         * The height of the maze.
         */
        public static final int MAZE_HEIGHT = 15;

        /**
         * The width of the maze.
         */
        public static final int MAZE_WIDTH = 30;

        /**
         */
        public static final OrderedPair NORTH = new OrderedPair(0, -1);

        /**
         */
        public static final OrderedPair WEST = new OrderedPair(-1, 0);

        /**
         */
        public static final OrderedPair SOUTH = new OrderedPair(0, 1);

        /**
         */
        public static final OrderedPair EAST = new OrderedPair(1, 0);

        /**
         */
        public static final OrderedPair NORTH_WEST = NORTH.add(WEST);

        /**
         */
        public static final OrderedPair NORTH_EAST = NORTH.add(EAST);

        /**
         */
        public static final OrderedPair SOUTH_WEST = SOUTH.add(WEST);

        /**
         */
        public static final OrderedPair SOUTH_EAST = SOUTH.add(EAST);

        /**
         */
        public static final OrderedPair[] NORTH_CHECK = new OrderedPair[] { NORTH, NORTH.add(NORTH), NORTH.add(EAST),
                        NORTH.add(WEST) };

        /**
         */
        public static final OrderedPair[] WEST_CHECK = new OrderedPair[] { WEST, WEST.add(WEST), WEST.add(NORTH),
                        WEST.add(SOUTH) };

        /**
         */
        public static final OrderedPair[] SOUTH_CHECK = new OrderedPair[] { SOUTH, SOUTH.add(SOUTH), SOUTH.add(EAST),
                        SOUTH.add(WEST) };

        /**
         */
        public static final OrderedPair[] EAST_CHECK = new OrderedPair[] { EAST, EAST.add(EAST), EAST.add(NORTH),
                        EAST.add(SOUTH) };

        /**
         * The initial start location of the player character.
         */
        public static final OrderedPair START_POS = new OrderedPair(0, 0);

        /**
         * The location of the maze exit.
         */
        public static final OrderedPair EXIT_POS = new OrderedPair(MAZE_WIDTH - 1, MAZE_HEIGHT - 1);

        /**
         */
        public static final Color HALLWAY_COLOR = Color.WHITE;

        /**
         */
        public static final Color WALL_COLOR = Color.BLACK;

        /**
         */
        public static final Color EXIT_COLOR = Color.GREEN;

        /**
         */
        public static final Color PLAYER_COLOR = Color.BLUE;

        /**
         */
        public static final Color MONSTER_COLOR = Color.RED;

        /**
         */
        public static final String TEMP_SYMBOL = "*";

        /**
         */
        public static final String HALLWAY = "░";

        /**
         */
        public static final String WALL = "█";

        /**
         */
        public static final String EXIT = "X";

        /**
         */
        public static final String PLAYER = "!!";

        /**
         */
        public static final String MONSTER = "&";

        private MazeVars() {
        }

}
