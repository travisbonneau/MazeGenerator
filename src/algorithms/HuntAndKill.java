/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import maze_generator.Cell;
import maze_generator.MazeGenerator;

/**
 *
 * @author TravisBonneau
 */
public class HuntAndKill {

    public static void solve(Cell[][] cells) {
        int r = (int) (Math.random() * cells.length);
        int c = (int) (Math.random() * cells[0].length);
        Point p = new Point(r, c);
        
        do{
            randomWalk(p.x, p.y, cells);                                        // X represents R, and Y represents C
            p = getNonVisitedPoint(cells);
        }while(p != null);

    }
    
    private static void randomWalk(int r, int c, Cell[][] cells){
        ArrayList<Cell> availableNeighbors = MazeGenerator.getAvailableNeighbors(r, c);
        if(availableNeighbors.isEmpty())
            return;
        int randIndex = (int)(Math.random()*availableNeighbors.size());
        Cell rand = availableNeighbors.get(randIndex);
        cells[r][c].openTo(rand);
        randomWalk(rand.getRow(), rand.getCol(), cells);
    }
    
    private static Point getNonVisitedPoint(Cell[][] cells){
        for(int r=0; r<cells.length; r++){
            for(int c=0; c<cells[r].length; c++){
                Point temp = getCellNextToOpenCell(r, c);
                if(cells[r][c].hasAllWalls() && temp != null){
                    cells[r][c].openTo(cells[temp.x][temp.y]);
                    return new Point(r, c);
                }
            }
        }
        return null;
    }
    
    private static Point getCellNextToOpenCell(int r, int c){
        if(MazeGenerator.inBounds(r-1, c) && !MazeGenerator.isAvailable(r-1, c))
            return new Point(r-1, c);
        if(MazeGenerator.inBounds(r+1, c) && !MazeGenerator.isAvailable(r+1, c))
            return new Point(r+1, c);
        if(MazeGenerator.inBounds(r, c-1) && !MazeGenerator.isAvailable(r, c-1))
            return new Point(r, c-1);
        if(MazeGenerator.inBounds(r, c+1) && !MazeGenerator.isAvailable(r, c+1))
            return new Point(r, c+1);
        return null;
    }

    
    
}
