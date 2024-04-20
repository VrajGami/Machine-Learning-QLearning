import javax.swing.*;
import java.util.Random;

/**
 * The `LearningAgent` class represents an agent that learns to navigate a maze using Q-learning.
 * It is responsible for the training and testing phases of the Q-learning algorithm, as well as updating the GUI with the agent's progress.
 */
public class LearningAgent implements Runnable {
    private int currentEpisode = 0; // The current episode of the training phase
    private final Map map; // The map of the maze
    private int stepsTaken = 0; // The number of steps taken by the agent
    private static final double DISCOUNT_FACTOR = 0.9; // The discount factor used in the Q-learning algorithm
    private static final double INITIAL_EPSILON = 1.0; // The initial exploration rate
    private static final double MIN_EPSILON = 0.01; // The minimum exploration rate
    private static final int MAX_STEPS = 1000; // The maximum number of training steps
    private static final double LEARNING_RATE = 0.3; // The learning rate used in the Q-learning algorithm
    private static final int NUM_EPISODES = 120; // The number of episodes in the training phase

    private final Position[][] maze; // The maze representation
    private final int length; // The length of the maze
    private final int width; // The width of the maze
    private static double[][] qTable; // The Q-table, which stores the Q-values for each state-action pair
    private static final Object qTableLock = new Object(); // A lock object to ensure thread-safe access to the Q-table
    private final Position goalPosition; // The position of the goal in the maze
    private Position agentPosition; // The current position of the agent
    private final Random random; // A random number generator
    private final ML_GUI gui; // The GUI instance
    private final int position; // The index of the agent

    /**
     * Constructs a new instance of the `LearningAgent` class.
     *
     * @param map      The map of the maze.
     * @param gui      The GUI instance.
     * @param position The index of the agent.
     */
    public LearningAgent(Map map, ML_GUI gui, int position) {
        this.position = position;
        this.maze = map.getConvertedMaze();
        this.map = map;
        this.agentPosition = map.getAgentPosition(position);
        this.length = maze.length;
        this.width = maze[0].length;
        this.goalPosition = map.getGoalPosition();
        this.random = new Random();
        this.gui = gui;
        if (qTable == null) {
            initializeQTable();
        }
    }

    /**
     * Initializes the Q-table with random values.
     */
    private void initializeQTable() {
        qTable = new double[length * width][4];
        for (int i = 0; i < length * width; i++) {
            for (int j = 0; j < 4; j++) {
                qTable[i][j] = random.nextDouble(); // Initialize Q-values randomly
            }
        }
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (currentEpisode < NUM_EPISODES) {
                trainEpisode(); // Run the training episode

        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Total execution time: " + executionTime + " milliseconds");
    }

    /**
     * Runs a single training episode.
     * The agent tries to reach the goal position while updating the Q-table based on the rewards received.
     */
    private void trainEpisode() {
        currentEpisode++;
        resetAgent();
        stepsTaken = 0;
        while (!agentPosition.equals(goalPosition) && stepsTaken < MAX_STEPS) {
            int action = bestAction(agentPosition);
            Position nextPosition = possibleMoveAgent(agentPosition, action);
            System.out.println(agentPosition.getXCord() + ", " + agentPosition.getYCord());
            System.out.println(nextPosition.getXCord() + " , " + nextPosition.getYCord());
            double reward = getReward(nextPosition);
            updateQValue(agentPosition, nextPosition, reward, action);
            if (!nextPosition.isWall()) {
                agentPosition = nextPosition;
            }
            stepsTaken++;

            System.out.flush(); // Flush the output stream
        }
        System.out.println("Episode: " + currentEpisode + ", Steps taken: " + stepsTaken);
    }

    /**
     * Runs a single testing episode.
     * The agent tries to reach the goal position using the learned Q-values, and the GUI is updated accordingly.
     */
    public void testEpisode() {
        resetAgent();
        while (!agentPosition.equals(goalPosition)) {

            System.out.flush(); // Flush the output stream
            int action = selectActionForTesting();
            Position nextPosition = possibleMoveAgent(agentPosition, action);
            System.out.println("Agent takes action: " + action);
            System.out.println("Next position: (" + nextPosition.getXCord() + ", " + nextPosition.getYCord() + ")");

            // Update the maze and agent position
            maze[agentPosition.getXCord()][agentPosition.getYCord()].setAgent(false);

            maze[nextPosition.getXCord()][nextPosition.getYCord()].setAgent(true);


            // Schedule the GUI update on the EDT
            SwingUtilities.invokeLater(() -> gui.renderMaze(this.maze, this.stepsTaken, DISCOUNT_FACTOR, LEARNING_RATE, NUM_EPISODES));

            try {
                Thread.sleep(100); // Wait for 1 second before the next action
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            agentPosition = nextPosition;
            stepsTaken++;
        }
        System.out.println("Testing phase completed in " + stepsTaken + " steps");
    }



    /**
     * Resets the agent's position to the starting position and resets the steps taken.
     */
    private void resetAgent() {
        agentPosition = map.getAgentPosition(position);
        stepsTaken = 0;
    }

    /**
     * Calculates the current exploration rate (epsilon) based on the number of steps taken.
     * The exploration rate starts at `INITIAL_EPSILON` and decays linearly to `MIN_EPSILON`.
     *
     * @return The current exploration rate.
     */
    private double calculateEpsilon() {
        double epsilon = MIN_EPSILON + (INITIAL_EPSILON - MIN_EPSILON) * stepsTaken / MAX_STEPS;
        return Math.max(MIN_EPSILON, epsilon); // Ensure epsilon does not go below the minimum value
    }

    /**
     * Selects the best action for the agent based on the current exploration rate.
     *
     * @param agent The current position of the agent.
     * @return The index of the best action.
     */
    private int bestAction(Position agent) {
        double epsilon = calculateEpsilon();
        return selectActionEpsilonGreedy(agent, epsilon);
    }

    /**
     * Selects an action using the epsilon-greedy strategy.
     * With probability `epsilon`, a random action is selected (exploration).
     * Otherwise, the action with the highest Q-value is selected (exploitation).
     *
     * @param agent   The current position of the agent.
     * @param epsilon The current exploration rate.
     * @return The index of the selected action.
     */
    private int selectActionEpsilonGreedy(Position agent, double epsilon) {
        int[] actions = {0, 1, 2, 3};
        if (random.nextDouble() < epsilon) {
            // Exploration: Choose a random action
            return actions[random.nextInt(actions.length)];
        } else {
            // Exploitation: Choose the best-known action
            int bestAction = actions[0];
            double maxQValue = qTable[agent.getYCord() * length + agent.getXCord()][bestAction];
            for (int action : actions) {
                double qValue = qTable[agent.getYCord() * length + agent.getXCord()][action];
                if (qValue > maxQValue) {
                    maxQValue = qValue;
                    bestAction = action;
                }
            }
            return bestAction;
        }
    }

    /**
     * Determines the next position of the agent based on the given action.
     *
     * @param position The current position of the agent.
     * @param action   The action to be taken (0: up, 1: down, 2: left, 3: right).
     * @return The next position of the agent, or the current position if the move is not possible.
     */
    private Position possibleMoveAgent(Position position, int action) {
        int x = position.getXCord();
        int y = position.getYCord();
        Position nextPosition = null;
        switch (action) {
            case 0: // Up
                if (y - 1 >= 0) {
                    nextPosition = maze[x - 1][y];
                }
                break;
            case 1: // Down
                if (y + 1 < maze.length) {
                    nextPosition = maze[x + 1][y];
                }
                break;
            case 2: // Left
                if (x - 1 >= 0) {
                    nextPosition = maze[x][y - 1];
                }
                break;
            case 3: // Right
                if (x + 1 < maze[0].length) {
                    nextPosition = maze[x][y + 1];
                }
                break;
        }
        if (nextPosition != null && !nextPosition.isWall()) {
            return nextPosition;
        } else {
            return position;
        }
    }

    /**
     * Calculates the reward for the agent's current position.
     *
     * @param position The current position of the agent.
     * @return The reward for the current position.
     */
    private double getReward(Position position) {
        if (position.isGoal()) {
            return 100.0; // Reward for reaching the goal
        } else if (position.isWall()) {
            return -10.0; // Penalty for hitting a wall
        } else {
            return -1.0; // Penalty for each step
        }
    }

    /**
     * Updates the Q-value for the current state-action pair.
     *
     * @param position      The current position of the agent.
     * @param nextPosition  The next position of the agent.
     * @param reward        The reward for the current state-action pair.
     * @param action        The action taken by the agent.
     */
    private void updateQValue(Position position, Position nextPosition, double reward, int action) {
        int x = position.getXCord();
        int y = position.getYCord();
        double qValue;
        double maxQValue;
        double updatedQValue;

        synchronized (qTableLock) {
            qValue = qTable[y * width + x][action];
            maxQValue = getMaxQValue(nextPosition);
            updatedQValue = qValue + LEARNING_RATE * (reward + DISCOUNT_FACTOR * maxQValue - qValue);
            qTable[y * width + x][action] = updatedQValue;
        }
        System.out.println("Q-value before update: " + qValue);
        System.out.println("Updated Q-value at position (" + x + ", " + y + "), action " + action + ": " + updatedQValue);
    }

    /**
     * Finds the maximum Q-value for the given position.
     *
     * @param position The position for which the maximum Q-value is to be found.
     * @return The maximum Q-value for the given position.
     */
    private double getMaxQValue(Position position) {
        int x = position.getXCord();
        int y = position.getYCord();
        double maxQValue = Double.NEGATIVE_INFINITY;
        for (int nextAction = 0; nextAction < 4; nextAction++) {
            double qValue = qTable[y * length + x][nextAction];
            if (qValue > maxQValue) {
                maxQValue = qValue;
            }
        }
        System.out.println("max Q value is this : " + maxQValue);
        return maxQValue;
    }

    /**
     * Selects an action for the testing phase using a softmax-based approach.
     *
     * @return The index of the selected action.
     */
    private int selectActionForTesting() {
        double[] probabilities = new double[4];
        double sum = 0.0;
        for (int i = 0; i < 4; i++) {
            probabilities[i] = Math.exp(qTable[agentPosition.getYCord() * length + agentPosition.getXCord()][i]);
            sum += probabilities[i];
        }
        for (int i = 0; i < 4; i++) {
            probabilities[i] /= sum;
        }
        double rand = random.nextDouble();
        double cumulativeProbability = 0.0;
        for (int i = 0; i < 4; i++) {
            cumulativeProbability += probabilities[i];
            if (rand < cumulativeProbability) {
                return i;
            }
        }
        return random.nextInt(4);
    }


}
