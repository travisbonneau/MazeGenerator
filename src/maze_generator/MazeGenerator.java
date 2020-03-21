package maze_generator;

// Name: Travis Bonneau
// Date: 5/19/2016
// Class: Computer Science III
// Lab: MazeGenerator

import algorithms.Backtracking;
import algorithms.BinaryTree;
import algorithms.HuntAndKill;
import algorithms.Sidewinder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class MazeGenerator extends JFrame {

    // add instance variables
    private int numRows;
    private int numCols;
    private int row, col, endRow, endCol;
    private boolean isAntiMaze = false;
    private int cellSize;
    private Color wallColor;
    private Color backgroundColor;
    private Color pathColor;
    private Color solveColor;
    private JPanel mazePanel;
    private JPanel backgroundPanel;
    private AlgorithmEnum alg;
    private static Cell[][] cell;
    private LinkedList<Cell> path;
    private Timer solveTimer;
    private Timer colorTimer;
    
    private final JButton newMazeButton = new JButton("New Maze");
    private final JButton optionsButton = new JButton("Options");
    private final JButton solveButton = new JButton("Solve");
    
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("File");
    private final JMenuItem size10 = new JMenuItem("10x10");
    private final JMenuItem size20 = new JMenuItem("20x20");
    private final JMenuItem size25 = new JMenuItem("25x25");
    private final JMenuItem exit = new JMenuItem("Exit");
    private final JMenu algorithmsMenu = new JMenu("Algorithm");
    private final JMenuItem backtracking = new JMenuItem("Backtracking");
    private final JMenuItem binaryTree = new JMenuItem("Binary Tree");
    private final JMenuItem huntAndKill = new JMenuItem("Hunt and Kill");
    private final JMenuItem sidewinder = new JMenuItem("Sidewinder");
    private final JMenu viewMenu = new JMenu("View");
    private final JMenuItem customize = new JMenuItem("Customize");
    private final JCheckBox partyMode = new JCheckBox("Party Mode");

    public MazeGenerator() {
        setLayout(new BorderLayout());
        initGUI();
        // add a timer for solving the maze
        setTimer();

        setTitle("MazeGenerator");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initGUI() {
        // set basic color and cell variables
        cellSize = 20;
        wallColor = Color.BLUE;
        backgroundColor = Color.BLACK;
        pathColor = Color.GREEN;
        solveColor = Color.RED;
        
        // set up title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Maze");
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 48));
        titlePanel.setBackground(Color.BLACK);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // set up menubar
        setMenu();

        // add listeners
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                keyReleasedKeyEvent(e);
            }
        });
        setFocusable(true);

        // add button panel with buttons to reset
        JPanel buttonPanel = new JPanel();
        newMazeButton.setFocusable(false);
        optionsButton.setFocusable(false);
        solveButton.setFocusable(false);
        buttonPanel.add(newMazeButton);
        buttonPanel.add(optionsButton);
        buttonPanel.add(solveButton);
        buttonPanel.setBackground(Color.BLACK);
        add(buttonPanel, BorderLayout.SOUTH);

        newMazeButton.addActionListener((ActionEvent) -> {
            setRowsAndCols(numRows, numCols);
        });
        optionsButton.addActionListener((ActionEvent) -> {
            changeOptions();
        });
        solveButton.addActionListener((ActionEvent) -> {
            solve();
        });
        backgroundPanel = new JPanel();
        mazePanel = new JPanel();
        backgroundPanel.add(mazePanel);
        backgroundPanel.setBackground(wallColor);
        mazePanel.setBackground(Color.BLACK);
        add(backgroundPanel, BorderLayout.CENTER);
        
        alg = AlgorithmEnum.BACKTRACKING;
        setRowsAndCols(25, 25);
    }

    private void changeOptions() {
        OptionsDialog dialog = new OptionsDialog(this);
    }

    private void customizeOption() {
        CustomizeDialog dialog = new CustomizeDialog(this, cellSize, wallColor, backgroundColor, pathColor, solveColor);
    }
    
    private void keyReleasedKeyEvent(KeyEvent e) {
        // check the four different key presses
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            // move up if this cell does not have a top wall
            if (cell[row][col].walls[Cell.TOP] == false) {
                moveTo(row - 1, col, Cell.TOP, Cell.BOTTOM);
                // move up more if this is a long column(loop checking top-false,left-true,right-true)
                while (cell[row][col].walls[Cell.TOP] == false && cell[row][col].walls[Cell.LEFT] && cell[row][col].walls[Cell.RIGHT]) {
                    moveTo(row - 1, col, Cell.TOP, Cell.BOTTOM);
                }
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // move up if this cell does not have a top wall
            if (cell[row][col].walls[Cell.RIGHT] == false) {
                moveTo(row, col + 1, Cell.RIGHT, Cell.LEFT);
                // move up more if this is a long column(loop checking top-false,left-true,right-true)
                while (cell[row][col].walls[Cell.RIGHT] == false && cell[row][col].walls[Cell.TOP] && cell[row][col].walls[Cell.BOTTOM]) {
                    moveTo(row, col + 1, Cell.RIGHT, Cell.LEFT);
                }
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            // move up if this cell does not have a top wall
            if (cell[row][col].walls[Cell.BOTTOM] == false) {
                moveTo(row + 1, col, Cell.BOTTOM, Cell.TOP);
                // move up more if this is a long column(loop checking top-false,left-true,right-true)
                while (cell[row][col].walls[Cell.BOTTOM] == false && cell[row][col].walls[Cell.LEFT] && cell[row][col].walls[Cell.RIGHT]) {
                    moveTo(row + 1, col, Cell.BOTTOM, Cell.TOP);
                }
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            // move up if this cell does not have a top wall
            if (cell[row][col].walls[Cell.LEFT] == false) {
                moveTo(row, col - 1, Cell.LEFT, Cell.RIGHT);
                // move up more if this is a long column(loop checking top-false,left-true,right-true)
                while (cell[row][col].walls[Cell.LEFT] == false && cell[row][col].walls[Cell.TOP] && cell[row][col].walls[Cell.BOTTOM]) {
                    moveTo(row, col - 1, Cell.LEFT, Cell.RIGHT);
                }
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            colorTimer.start();
        }
        
        // check to see if the game is over
        if (row == endRow && col == endCol) {
            String message = "Congratulations! You solved it.";
            JOptionPane.showMessageDialog(this, message);
            setRowsAndCols(numRows, numCols);
        }

    }

    private void moveTo(int nextRow, int nextCol, int firstDirection, int secondDirection) {
        // update the current cell(change boolean curr to false) and add the path(firstD)
        // update row and col to next
        // update cell[row][col] to current and add the path(secondD)
        cell[row][col].setCurrent(false);
        cell[row][col].path[firstDirection] = true;
        row = nextRow;
        col = nextCol;
        cell[row][col].setCurrent(true);
        cell[row][col].path[secondDirection] = true;

        // and repaint since changes were made
        repaint();

    }

    private void createMaze() {
        mazePanel.removeAll();
        path = new LinkedList<>();

        newMazeButton.setEnabled(false);
        optionsButton.setEnabled(false);
        solveButton.setEnabled(false);

        mazePanel.setLayout(new GridLayout(numRows, numCols, 0, 0));
        cell = new Cell[numRows][numCols];
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                cell[r][c] = new Cell(r, c, isAntiMaze, cellSize, wallColor, backgroundColor, pathColor, solveColor);
                mazePanel.add(cell[r][c]);
            }
        }
        cell[0][0].setCurrent(true);
        cell[endRow][endCol].setEnd(true);

        carvePassages();

        validate();
        pack();
        newMazeButton.setEnabled(true);
        optionsButton.setEnabled(true);
        solveButton.setEnabled(true);
        setLocationRelativeTo(null);
    }

    private void carvePassages() {
        // choose a random spot to start from
        switch (alg) {
            case BACKTRACKING:
                Backtracking.solve(cell);
                break;
            case BINARY_TREE:
                BinaryTree.solve(cell);
                break;
            case HUNT_AND_KILL:
                HuntAndKill.solve(cell);
                break;
            case SIDEWINDER:
                Sidewinder.solve(cell);
                break;
        }
    }

    public static boolean inBounds(int r, int c) {
        return ((r >= 0 && r < cell.length) && (c >= 0 && c < cell[r].length));
    }

    public static boolean isAvailable(int r, int c) {
        //must be inbounds and have all walls
        return inBounds(r, c) && cell[r][c].hasAllWalls();
    }

    public void setRowsAndCols(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        endRow = numRows - 1;
        endCol = numCols - 1;
        row = col = 0;
        createMaze();
    }

    public int getRows() {
        return numRows;
    }

    public int getCols() {
        return numCols;
    }

    public boolean isAntiMaze(){
        return isAntiMaze;
    }
    
    public void setAntiMaze(boolean isAntiMaze) {
        this.isAntiMaze = isAntiMaze;
    }

    private void solve() {
        path = bfs();
        newMazeButton.setEnabled(false);
        optionsButton.setEnabled(false);
        solveButton.setEnabled(false);
        solveTimer.start();
    }

    private LinkedList<Cell> bfs() {
        HashMap<Cell, Cell> map = new HashMap<>();
        LinkedList<Cell> queue = new LinkedList<>();
        HashSet<Cell> visited = new HashSet<>();
        queue.add(cell[0][0]);
        visited.add(cell[0][0]);
        map.put(cell[0][0], null);

        while (!queue.isEmpty()) {
            Cell node = queue.remove();
            ArrayList<Cell> neighbors = getNeighbors(node.getRow(), node.getCol());
            for (Cell n : neighbors) {
                if (!visited.contains(n)) {
                    visited.add(n);
                    queue.add(n);
                    map.put(n, node);
                    if (n.equals(cell[endRow][endCol])) {
                        queue = new LinkedList<>();
                        break;
                    }
                }
            }
        }

        Cell temp = cell[endRow][endCol];
        while (temp != null) {
            queue.addFirst(temp);
            temp = map.get(temp);
        }
        return queue;
    }

    private ArrayList<Cell> getNeighbors(int r, int c) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        Cell node = cell[r][c];
        if (inBounds(r - 1, c) && !node.walls[Cell.TOP]) {
            neighbors.add(cell[r - 1][c]);
        }
        if (inBounds(r + 1, c) && !node.walls[Cell.BOTTOM]) {
            neighbors.add(cell[r + 1][c]);
        }
        if (inBounds(r, c - 1) && !node.walls[Cell.LEFT]) {
            neighbors.add(cell[r][c - 1]);
        }
        if (inBounds(r, c + 1) && !node.walls[Cell.RIGHT]) {
            neighbors.add(cell[r][c + 1]);
        }
        return neighbors;
    }
    
    public static ArrayList<Cell> getAvailableNeighbors(int r, int c) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        if (isAvailable(r - 1, c)) {
            neighbors.add(cell[r - 1][c]);
        }
        if (isAvailable(r + 1, c)) {
            neighbors.add(cell[r + 1][c]);
        }
        if (isAvailable(r, c - 1)) {
            neighbors.add(cell[r][c - 1]);
        }
        if (isAvailable(r, c + 1)) {
            neighbors.add(cell[r][c + 1]);
        }
        return neighbors;
    }

    private void setTimer(){
        solveTimer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!path.isEmpty()) {
                    Cell node = path.removeFirst();
                    if (!path.isEmpty()) {
                        node.setSolvePath(path.getFirst());
                    }
                    //node.setSolved(true);
                    node.repaint();
                } else {
                    newMazeButton.setEnabled(true);
                    optionsButton.setEnabled(true);
                    solveButton.setEnabled(true);
                    solveTimer.stop();
                }
            }
        });
        
        colorTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int r = (int)(Math.random()*256);
                int g = (int)(Math.random()*256);
                int b = (int)(Math.random()*256);
                setupCell(cellSize, new Color(r,g,b), backgroundColor, pathColor, solveColor);
            }
        });
    }
    
    public void setupCell(int cellSize, Color wallColor, Color backgroundColor, Color pathColor, Color solveColor) {
        this.cellSize = cellSize;
        this.wallColor = wallColor;
        this.backgroundColor = backgroundColor;
        this.pathColor = pathColor;
        this.solveColor = solveColor;
        
        cell[0][0].setupCell(cellSize, wallColor, backgroundColor, pathColor, solveColor);
        backgroundPanel.setBackground(wallColor);
        mazePanel.repaint();
        revalidate();
        pack();
        setLocationRelativeTo(null);
    }
    
    public int getCellSize(){
        return cellSize;
    }
    
    private void setMenu() {
        fileMenu.add(size10);
        fileMenu.add(size20);
        fileMenu.add(size25);
        fileMenu.add(new JSeparator());
        fileMenu.add(exit);

        algorithmsMenu.add(backtracking);
        algorithmsMenu.add(binaryTree);
        algorithmsMenu.add(huntAndKill);
        algorithmsMenu.add(sidewinder);
        
        viewMenu.add(customize);
        viewMenu.add(partyMode);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(algorithmsMenu);
        setJMenuBar(menuBar);

        // ActionListener for MenuBar
        size10.addActionListener((ActionEvent) -> {
            setRowsAndCols(10, 10);
        });
        size20.addActionListener((ActionEvent) -> {
            setRowsAndCols(20, 20);
        });
        size25.addActionListener((ActionEvent) -> {
            setRowsAndCols(25, 25);
        });
        exit.addActionListener((ActionEvent) -> {
            System.exit(0);
        });

        backtracking.addActionListener((ActionEvent) -> {
            alg = AlgorithmEnum.BACKTRACKING;
            setRowsAndCols(numRows, numCols);
        });
        binaryTree.addActionListener((ActionEvent) -> {
            alg = AlgorithmEnum.BINARY_TREE;
            setRowsAndCols(numRows, numCols);
        });
        huntAndKill.addActionListener((ActionEvent) -> {
            alg = AlgorithmEnum.HUNT_AND_KILL;
            setRowsAndCols(numRows, numCols);
        });
        sidewinder.addActionListener((ActionEvent) -> {
            alg = AlgorithmEnum.SIDEWINDER;
            setRowsAndCols(numRows, numCols);
        });
        
        customize.addActionListener((ActionEvent) -> {
            customizeOption();
        });
        partyMode.addActionListener((ActionEvent) -> {
            if(partyMode.isSelected())
                colorTimer.start();
            else
                colorTimer.stop();
        });
    }

    public static void main(String[] args) {
        new MazeGenerator();
    }
}
