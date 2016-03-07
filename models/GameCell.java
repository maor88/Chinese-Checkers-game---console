/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


/**
 *
 * @author Maor.a
 */
public class GameCell {
    protected int row;
    protected int col;
    protected GameColors color;
    protected boolean isValid;

    public GameCell(){}
    public GameCell(boolean isValid){
        this.isValid = isValid;
    }
    public GameCell(boolean isValid, GameColors gameColors){
        this.isValid = isValid;
        this.color = gameColors;
    }
    public GameCell(int row, int col, GameColors color, boolean isValid) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.isValid = isValid;
    }
    
    public GameCell getCell() {
        return this;
        
    }
    
    
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public GameColors getColor() {
        return color;
    }

    public void setColor(GameColors color) {
        this.color = color;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }
    
    public boolean isColorEmpty(){
        return this.color == GameColors.EMPTY;
    }

    
}
