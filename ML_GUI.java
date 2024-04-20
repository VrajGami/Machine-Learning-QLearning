import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The `ML_GUI` class represents the main graphical user interface (GUI) for the application.
 * It creates the window, the grid panel for displaying the maze, and the metadata panel for displaying additional information.
 * The class also handles the creation and management of the `LearningAgent` instances and the rendering of the maze.
 */
public class ML_GUI extends JFrame {
    private final JPanel greedPanel; // The panel for displaying the maze
    private final JPanel metadataPanel; // The panel for displaying additional information
    JButton maze1Button ;
    JButton maze2Button ;
    JButton maze3Button ;
    JButton maze4Button ;

    JButton maze5Button;
    JButton back;

    /**
     * Constructs a new instance of the `ML_GUI` class.
     * It sets up the window, creates the grid panel and metadata panel, and adds the necessary components to the frame.
     * The method also adds action listeners to the "Start with Maze 1" and "Start with Maze 2" buttons.
     */
    public ML_GUI() {
        // Define the maze configurations
        int[][] maze1 = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 2, 0, 2, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 2, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
                {1, 2, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 0, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 1, 1, 1, 0, 0, 0, 1, 3, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        int[][] maze2 = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 2, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 3, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 1, 2, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        int[][] maze3 = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 0, 1, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
                {1, 1, 1, 0, 1, 0, 1, 1, 0, 1},
                {1, 0, 0, 0, 1, 0, 2, 1, 0, 1},
                {1, 3, 1, 1, 1, 0, 1, 0, 2, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        int[][] maze4 = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 2, 1, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 2, 1, 1, 1, 1, 1, 1, 3, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        int[][] maze5= {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 2, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 2, 0, 0, 0, 1},
                {1, 0, 1, 0, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 3, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}

        };
        // Set the desired window size
        int windowWidth = 600;
        int windowHeight = 700;
        this.setSize(windowWidth, windowHeight);
        this.setMinimumSize(new Dimension(windowWidth, windowHeight));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel and set the layout
        this.setLayout(new BorderLayout());

        // Create the grid panel for the maze
        this.greedPanel = new JPanel();

        this.greedPanel.setSize(500, 500);

        GridLayout gl = new GridLayout(10, 10);
        this.greedPanel.setLayout(gl);

        // Create the metadata panel for displaying the steps
        this.metadataPanel = new JPanel();
        metadataPanel.setLayout(new FlowLayout(FlowLayout.LEFT));



        // Create the buttons
        maze1Button = createCustomButton("Start with Maze 1");
         maze2Button = createCustomButton("Start with Maze 2");
         maze3Button = createCustomButton("Start with Maze 3");
         maze4Button = createCustomButton("Start with Maze 4");
        maze5Button = createCustomButton("Start with Maze 5");

        // Create the title label with custom styling
        JLabel titleLabel = createCustomTitleLabel("Path Finding Algorithm");
        JLabel Label = createCustomTitleLabel("Using Machine Learning");
        JLabel Label1 = createCustomTitleLabel(" By Vraj Gami ");
        greedPanel.add(titleLabel, BorderLayout.NORTH);
        greedPanel.add(Label);
        greedPanel.add(Label1);
        // Add the buttons to the metadata panel
        greedPanel.add(maze1Button);
        greedPanel.add(maze2Button);
        greedPanel.add(maze3Button);
        greedPanel.add(maze4Button);
        greedPanel.add(maze5Button);
        greedPanel.setBackground(Color.GRAY);
        // Add the panels to the main frame
        this.add(this.greedPanel, BorderLayout.CENTER);
        this.add(metadataPanel, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Add action listeners to the buttons
        maze1Button.addActionListener(e -> startWithMaze(maze1));
        maze2Button.addActionListener(e -> startWithMaze(maze2));
        maze3Button.addActionListener(e -> startWithMaze(maze3));
        maze4Button.addActionListener(e -> startWithMaze(maze4));
        maze5Button.addActionListener(e -> startWithMaze(maze5));
    }

    /**
     * Starts the simulation with the given maze configuration.
     *
     * @param maze The maze configuration to use.
     */
    private void startWithMaze(int[][] maze) {
        Map map = new Map(maze);
        Position[][] convertedMaze = map.getConvertedMaze();
        renderMaze(convertedMaze, 0, 0, 0, 0);

        // Create an instance of LearningAgent and start its thread
        int numAgents = map.getNumber_of_agent(); // Number of concurrent agents
        List<LearningAgent> agents = new ArrayList<>();
        List<Thread> agentThreads = new ArrayList<>();

        for (int i = 0; i < numAgents; i++) {
            LearningAgent agent = new LearningAgent(map, this, i);
            agents.add(agent);
            Thread agentThread = new Thread(agent);
            agentThreads.add(agentThread);
            agentThread.start();
        }

        for (Thread agentThread : agentThreads) {
            try {
                agentThread.join(); // Wait for the training thread to complete
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Run testing phase for each agent in separate threads
        for (LearningAgent agent : agents) {
            Thread testThread = new Thread(agent::testEpisode);
            testThread.start();
        }
    resetGUI();
    }
    private void resetGUI() {
        this.greedPanel.removeAll();
        this.metadataPanel.removeAll();
        // Create the title label with custom styling
        JLabel titleLabel = createCustomTitleLabel("Path Finding Algorithm");
        JLabel Label = createCustomTitleLabel("Using Machine Learning");
        JLabel Label1 = createCustomTitleLabel("By Vraj Gami");
        greedPanel.add(titleLabel, BorderLayout.NORTH);
        greedPanel.add(Label);
        greedPanel.add(Label1);
        // Add the buttons to the metadata panel
        greedPanel.add(maze1Button);
        greedPanel.add(maze2Button);
        greedPanel.add(maze3Button);
        greedPanel.add(maze4Button);
        greedPanel.add(maze5Button);
        greedPanel.setBackground(Color.GRAY);

        greedPanel.revalidate();
        greedPanel.repaint();
        metadataPanel.revalidate();
        metadataPanel.repaint();
    }

    /**
     * Renders the maze and the additional information in the GUI.
     *
     * @param convertedMaze    The maze to be rendered.
     * @param stepsTaken       The number of steps taken.
     * @param discountFactor   The discount factor used in the Q-learning algorithm.
     * @param learningRate     The learning rate used in the Q-learning algorithm.
     * @param numEpisodes      The number of episodes in the training phase.
     */
    public void renderMaze(Position[][] convertedMaze, int stepsTaken, double discountFactor, double learningRate, int numEpisodes) {
        this.greedPanel.removeAll();
        this.metadataPanel.removeAll();
        int iconSize = 70; // Adjust the size as needed
        Image pacmanImage = new ImageIcon("[PUT YOUR PATH]").getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        Image flagImage = new ImageIcon("[PUT YOUR PATH]").getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        Image wallImage = new ImageIcon("[PUT YOUR PATH]").getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        // Render the maze
        for (Position[] positions : convertedMaze) {
            for (Position position : positions) {
                if (position.isWall()) {
                    position.setIcon(new ImageIcon(wallImage));
                } else if (position.isGoal()) {
                    position.setIcon(new ImageIcon(flagImage));
                } else if (position.isAgent()) {
                    position.setIcon(new ImageIcon(pacmanImage));
                } else {
                    position.setIcon(new ImageIcon("C:\\Users\\Uesr\\Downloads\\pacman\\src\\path.png"));
                }
                this.greedPanel.add(position);
            }
        }

        // Display the additional information
        JLabel stepsTakenLabel = new JLabel("Steps Taken: " + stepsTaken);
        JLabel discountFactorLabel = new JLabel("Discount Factor: " + discountFactor);
        JLabel learningRateLabel = new JLabel("Learning Rate: " + learningRate);
        JLabel numEpisodesLabel = new JLabel("Number of Episodes: " + numEpisodes);
        back = new JButton(" BACK ->");
        back.setBackground(Color.GRAY);

        metadataPanel.add(stepsTakenLabel);
        metadataPanel.add(discountFactorLabel);
        metadataPanel.add(learningRateLabel);
        metadataPanel.add(numEpisodesLabel);
        metadataPanel.add(back);
        back.addActionListener(e -> resetGUI());

        metadataPanel.revalidate();
        metadataPanel.repaint();

        greedPanel.revalidate();
        greedPanel.repaint();
    }
    /**
     * Creates a custom button with a specific font, padding, and hover effect.
     *
     * @param text The text to be displayed on the button.
     * @return The custom button.
     */
    private JButton createCustomButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(120, 50));
        button.setMargin(new Insets(10, 20, 10, 20));
        button.setBackground(Color.GRAY);
        button.setForeground(Color.YELLOW);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.BLACK);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.GRAY);
            }
        });
        return button;
    }

    /**
     * Creates a custom title label with a specific font and styling.
     *
     * @return The custom title label.
     */
    private JLabel createCustomTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        Font font = new Font("Verdana", Font.BOLD, 24);
        HashMap<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.WEIGHT_BOLD);
        Font underlinedFont = font.deriveFont(attributes);
        label.setFont(underlinedFont);
        label.setForeground(Color.orange);
        label.setPreferredSize(new Dimension(0, 80));
        label.setBackground(Color.BLACK);
        label.setOpaque(true);
        return label;
    }

    /**
     * The main entry point of the application.
     * It creates a new instance of the `ML_GUI` class, which starts the simulation.
     *
     * @param args The command-line arguments (not used).
     */
    public static void main(String[] args) {
        new ML_GUI();
    }
}