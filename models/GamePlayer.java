/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


import java.util.List;



public class GamePlayer {
    
    
    
        protected List<GameColors> color;
      
        protected GamePlayerType type;
      
        protected String name;

    public List<GameColors> getColor() {
        return color;
    }

    public void setColor(List<GameColors> color) {
        this.color = color;
    }

    public GamePlayerType getType() {
        return type;
    }

    public void setType(GamePlayerType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GamePlayer{" + "color=" + color + ", type=" + type + ", name=" + name + '}';
    }
}
