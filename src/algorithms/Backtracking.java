/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;
import java.util.ArrayList;
import java.util.Stack;
import maze_generator.Cell;
import maze_generator.MazeGenerator;

/**
 *
 * @author travis Bonneau
 */
public class Backtracking {

    public static void solve(Cell[][] cells) {
        Stack<Cell> stack = new Stack<>();
        int r = (int) (Math.random() * cells.length);
        int c = (int) (Math.random() * cells[0].length);
        stack.push(cells[r][c]);
        while (!stack.isEmpty()) {
            Cell curr = stack.pop();
            r = curr.getRow();
            c = curr.getCol();
            ArrayList<Cell> neighbors = MazeGenerator.getAvailableNeighbors(r, c);
            if (neighbors.size() > 0) {
                if (neighbors.size() > 1) {
                    stack.push(curr);
                }
                Cell temp = neighbors.get((int) (Math.random() * neighbors.size()));
                curr.openTo(temp);
                stack.push(temp);
            }
        }
    }
    
}
