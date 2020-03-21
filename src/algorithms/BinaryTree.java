/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;
import maze_generator.Cell;
import static maze_generator.MazeGenerator.inBounds;

/**
 *
 * @author Travis Bonneau
 */
public class BinaryTree {
    
    public static void solve(Cell[][] cells){
        for(int r=cells.length-1; r>=0; r--){
            for(int c=cells[r].length-1; c>=0; c--){
                Cell temp = cells[r][c];
                int random = (int)(Math.random()*2);
                if(!inBounds(r-1, c) && inBounds(r, c-1)){
                    temp.openTo(cells[r][c-1]);
                } else if(!inBounds(r, c-1) && inBounds(r-1, c)) {
                    temp.openTo(cells[r-1][c]);
                } else if (random == 0 && inBounds(r, c-1)) {
                    temp.openTo(cells[r][c-1]);
                } else if (random ==1 && inBounds(r-1, c)) {
                    temp.openTo(cells[r-1][c]);
                }
            }
        }
    }
    
}
