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
public enum GameColors {
    BLACK(0),
    WHITE(1),
    RED(2),
    GREEN(3),
    BLUE(4),
    YELLOW(5),
    EMPTY(6);

      
    private int number;
    private String color;
    
    public String value() {
        return name();
    }

    public static GameColors fromValue(String v) {
        return valueOf(v);
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    
    GameColors(int number){
        this.number = number;
    }
    
    
    
}
