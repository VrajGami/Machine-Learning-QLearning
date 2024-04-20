import java.util.ArrayList;

/**
 * The `Map` class represents the maze that the learning agent navigates.
 * It provides methods to convert the maze representation, retrieve the agent and goal positions, and print the map.
 */
public class Map {
    private final ArrayList<Position> agentPositions = new ArrayList<>(); // List of agent positions
    private Position goalPosition; // The goal position in the maze
    private final Position[][] convertedMaze; // The maze representation as an array of Positions

    private int number_of_agent = 0; // The number of agents in the maze

    private final int[][] maze; // The original maze representation

    /**
     * Converts the given maze representation into an array of `Position` objects.
     *
     * @param maze The original maze representation.
     * @return The converted maze representation as an array of `Position` objects.
     */
    private Position[][] convertMaze(int[][] maze) {
        int length = maze.length;
        int width = maze[0].length;
        Position[][] mapConverted = new Position[length][width];

        for (int xCord = 0; xCord < length; xCord++) {
            for (int yCord = 0; yCord < width; yCord++) {
                if (maze[xCord][yCord] == 1) {
                    mapConverted[xCord][yCord] = new Position(xCord, yCord, true, false, false);
                } else if (maze[xCord][yCord] == 3) {
                    mapConverted[xCord][yCord] = new Position(xCord, yCord, false, true, false);
                    this.goalPosition = mapConverted[xCord][yCord];
                } else if (maze[xCord][yCord] == 2) {
                    mapConverted[xCord][yCord] = new Position(xCord, yCord, false, false, true);
                    this.agentPositions.add(mapConverted[xCord][yCord]);
                    number_of_agent++;
                } else {
                    mapConverted[xCord][yCord] = new Position(xCord, yCord, false, false, false);
                }
            }
        }

        if (goalPosition == null) {
            throw new IllegalStateException("Goal position not initialized");
        }
        return mapConverted;
    }

    /**
     * Constructs a new instance of the `Map` class with the given maze representation.
     *
     * @param maze The original maze representation.
     */
    public Map(int[][] maze) {
        this.maze = maze;
        this.convertedMaze = convertMaze(maze);
        int length = this.maze.length;
        int width = this.maze[0].length;
        printMap();
        System.out.println(length + " " + width);

        if (goalPosition == null) {
            throw new IllegalStateException("Goal position not initialized");
        }
    }

    /**
     * Returns the original maze representation.
     *
     * @return The original maze representation.
     */
    public int[][] getMaze() {
        return maze;
    }

    /**
     * Returns the converted maze representation as an array of `Position` objects.
     *
     * @return The converted maze representation.
     */
    public Position[][] getConvertedMaze() {
        return convertedMaze;
    }

    /**
     * Returns the agent position at the specified index.
     *
     * @param n The index of the agent.
     * @return The agent position.
     */
    public Position getAgentPosition(int n) {
        return this.agentPositions.get(n);
    }

    /**
     * Returns the goal position in the maze.
     *
     * @return The goal position.
     */
    public Position getGoalPosition() {
        return goalPosition;
    }

    /**
     * Prints the original maze representation.
     */
    public void printMap() {
        for (int[] row : getMaze()) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Returns the number of agents in the maze.
     *
     * @return The number of agents.
     */
    public int getNumber_of_agent() {
        return number_of_agent;
    }
}