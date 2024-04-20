import javax.swing.*;

/**
 * The `Position` class represents a single cell in the maze.
 * It extends the `JLabel` class to allow for easy rendering in the GUI.
 */
public class Position extends JLabel {
    private final int xCord; // The x-coordinate of the cell
    private final int yCord; // The y-coordinate of the cell
    private final boolean isWall; // Flag indicating if the cell is a wall
    private final boolean isGoal; // Flag indicating if the cell is the goal
    // Flag indicating if the cell is part of the path
    private boolean isAgent; // Flag indicating if the cell contains an agent


    /**
     * Sets the flag indicating if the cell contains an agent.
     *
     * @param agent `true` if the cell contains an agent, `false` otherwise.
     */
    public void setAgent(boolean agent) {
        isAgent = agent;
    }

    /**
     * Returns `true` if the cell is a wall, `false` otherwise.
     *
     * @return `true` if the cell is a wall, `false` otherwise.
     */
    public boolean isWall() {
        return isWall;
    }

    /**
     * Returns `true` if the cell is the goal, `false` otherwise.
     *
     * @return `true` if the cell is the goal, `false` otherwise.
     */
    public boolean isGoal() {
        return isGoal;
    }

    /**
     * Returns the x-coordinate of the cell.
     *
     * @return The x-coordinate of the cell.
     */
    public int getXCord() {
        return xCord;
    }




    /**
     * Returns the y-coordinate of the cell.
     *
     * @return The y-coordinate of the cell.
     */
    public int getYCord() {
        return yCord;
    }

    /**
     * Returns `true` if the cell contains an agent, `false` otherwise.
     *
     * @return `true` if the cell contains an agent, `false` otherwise.
     */
    public boolean isAgent() {
        return isAgent;
    }

    /**
     * Constructs a new instance of the `Position` class with the given parameters.
     *
     * @param xCord    The x-coordinate of the cell.
     * @param yCord    The y-coordinate of the cell.
     * @param isWall   `true` if the cell is a wall, `false` otherwise.
     * @param isGoal   `true` if the cell is the goal, `false` otherwise.
     * @param isAgent  `true` if the cell contains an agent, `false` otherwise.

     */
    public Position(int xCord, int yCord, boolean isWall, boolean isGoal, boolean isAgent) {
        this.xCord = xCord;
        this.yCord = yCord;
        this.isGoal = isGoal;
        this.isWall = isWall;
        this.isAgent = isAgent;
    }
}