/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maor.a
 */
public class GameRow {

    private List<GameCell> row = new ArrayList<GameCell>();

    public List<GameCell> getRow() {
        return row;
    }

    public void setRow(List<GameCell> row) {
        this.row = row;
    }
    
    
      public GameCell getCellInRow(int placeInRow) {
        for (int i = 0; i < row.size(); i++) {
            if (i==placeInRow ) {
                return row.get(i);
            }
        }
        return row.get(0);
    }
    

    public GameCell getFirstValidCell() {
        for (int i = 0; i < row.size(); i++) {
            if (row.get(i).isValid) {
                return row.get(i);
            }
        }
        return row.get(0);
    }

    public GameCell getLastValidCell() {
        for (int i = row.size() - 1; i >= 0; i--) {
            if (row.get(i).isValid) {
                return row.get(i);
            }
        }
        return row.get(row.size() - 1);
    }

    int getPlaceFirstValidCell() {

        for (int i = 0; i < row.size(); i++) {
            if (row.get(i).isValid) {
                return i;
            }
        }
        return 0;

    }

}
