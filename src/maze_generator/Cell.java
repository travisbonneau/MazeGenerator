package maze_generator;

// Name: Travis Bonneau
// Date: 5/19/2016
// Class: Computer Science III
// Lab: Cell

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class Cell extends JPanel {

    public boolean[] walls = {true, true, true, true};
    public boolean[] path = {false, false, false, false};
    public boolean[] solvePath = {false, false, false, false};
    
    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;
    private final int row;
    private final int col;
    
    private static int size = 20;
    private static Color backgroundColor;
    private static Color wallColor;
    private static Color pathColor;
    private static Color solveColor;
    
    private boolean current;
    private boolean end;
    private boolean isAntiMaze;
    private boolean isSolved;

    public Cell(int row, int col, boolean isAntiMaze, int size, Color wallColor, Color backgroundColor, Color pathColor, Color solveColor) {
        this.row = row;
        this.col = col;
        this.isAntiMaze = isAntiMaze;
        current = false;
        setupCell(size, wallColor, backgroundColor, pathColor, solveColor);
    }
    
    public int getRow(){
        return row;
    }
    
    public int getCol(){
        return col;
    }

    public Dimension getPreferredSize() {
        Dimension s = new Dimension(size, size);
        return s;
    }

    public boolean hasAllWalls() {
        return walls[TOP] && walls[RIGHT] && walls[BOTTOM] && walls[LEFT];
    }

    public void removeWall(int wall) {
        walls[wall] = false;
        repaint();
    }
    
    public void setSolvePath(Cell neighbor){
        if (neighbor.row == row - 1 && neighbor.col == col) {
            solvePath[TOP] = true;
            neighbor.solvePath[BOTTOM] = true;
        }
        if (neighbor.row == row + 1 && neighbor.col == col) {
            solvePath[BOTTOM] = true;
            neighbor.solvePath[TOP] = true;
        }
        if (neighbor.row == row && neighbor.col == col - 1) {
            solvePath[LEFT] = true;
            neighbor.solvePath[RIGHT] = true;
        }
        if (neighbor.row == row && neighbor.col == col + 1) {
            solvePath[RIGHT] = true;
            neighbor.solvePath[LEFT] = true;
        }
    }
    
    public void setCurrent(boolean curr){
        current = curr;
    }
    
    public void setupCell(int size, Color wallColor, Color backgroundColor, Color pathColor, Color solveColor) {
        this.size = size;
        this.wallColor = wallColor;
        this.backgroundColor = backgroundColor;
        this.pathColor = pathColor;
        this.solveColor = solveColor;
        
        setSize(size, size);
        setBackground(backgroundColor);
    }

    public void setSolved(boolean solved){
        isSolved = solved;
    }
    
    public void setEnd(boolean end){
        this.end = end;
    }
    
    public void openTo(Cell neighbor) {
        if (neighbor.row == row - 1 && neighbor.col == col) {
            removeWall(TOP);
            neighbor.removeWall(BOTTOM);
        }
        if (neighbor.row == row + 1 && neighbor.col == col) {
            removeWall(BOTTOM);
            neighbor.removeWall(TOP);
        }
        if (neighbor.row == row && neighbor.col == col - 1) {
            removeWall(LEFT);
            neighbor.removeWall(RIGHT);
        }
        if (neighbor.row == row && neighbor.col == col + 1) {
            removeWall(RIGHT);
            neighbor.removeWall(LEFT);
        }
    }

    @Override
    public boolean equals(Object obj) {
        Cell o = (Cell) obj;
        return ((row == o.row) && (col == o.col));
    }

    
    
    @Override
    public void paintComponent(Graphics graphics) {
        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, size, size);
        
        Graphics2D g = (Graphics2D) graphics;
        int mid = size/2;
        int space = (int)(size-size*0.8);
        
        g.setColor(solveColor);
        g.setStroke(new BasicStroke(size/4));
        if(end)
            g.fillOval(space, space, getWidth()-(2*space), getHeight()-(2*space));
        if(solvePath[TOP])
            g.drawLine(mid, 0, mid, mid);
        if(solvePath[RIGHT])
            g.drawLine(size, mid, mid, mid);
        if(solvePath[BOTTOM])
            g.drawLine(mid, size, mid, mid);
        if(solvePath[LEFT])
            g.drawLine(0, mid, mid, mid);
        
        g.setStroke(new BasicStroke(1));
        g.setColor(pathColor);
        if(current)
            g.fillOval(space, space, getWidth()-(2*space), getHeight()-(2*space));
        if(path[TOP])
            g.drawLine(mid, 0, mid, mid);
        if(path[RIGHT])
            g.drawLine(size, mid, mid, mid);
        if(path[BOTTOM])
            g.drawLine(mid, size, mid, mid);
        if(path[LEFT])
            g.drawLine(0, mid, mid, mid);
           
        g.setColor(wallColor);
        if(walls[TOP] ^ isAntiMaze)
            g.drawLine(0, 0, getWidth(), 0);
        if(walls[RIGHT] ^ isAntiMaze)
            g.drawLine(getWidth(), 0, getWidth(), getHeight());
        if(walls[BOTTOM] ^ isAntiMaze)
            g.drawLine(0, getHeight(), getWidth(), getHeight());
        if(walls[LEFT] ^ isAntiMaze)
            g.drawLine(0, 0, 0, getHeight());
    }
}
