/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.util.ArrayList;
import maze_generator.Cell;
import maze_generator.MazeGenerator;

/**
 *
 * @author TravisBonneau
 */
public class Sidewinder {

    public static void solve(Cell[][] cells) {
        ArrayList<Cell> runSet = new ArrayList<>();

        for(int c=0; c<cells[0].length; c++){
            if(MazeGenerator.inBounds(0, c+1)){
                cells[0][c].openTo(cells[0][c + 1]);
            }
        }
        
        for (int r = 1; r < cells.length; r++) {
            for (int c = 0; c < cells[r].length; c++) {
                runSet.add(cells[r][c]);
                int rand = (int) (Math.random() * 2);
                if (rand == 0 && MazeGenerator.inBounds(r, c + 1)) {
                    cells[r][c].openTo(cells[r][c + 1]);
                } else {
                    openNorth(runSet, cells);
                }
            }
            if(!runSet.isEmpty())
                openNorth(runSet, cells);
        }
    }

    private static void openNorth(ArrayList<Cell> runSet, Cell[][] cells) {
        int randIndex = (int) (Math.random() * runSet.size());
        Cell tempCell = runSet.get(randIndex);
        int randR = tempCell.getRow();
        int randC = tempCell.getCol();
        tempCell.openTo(cells[randR - 1][randC]);

        runSet.clear();
    }

}
