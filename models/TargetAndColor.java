/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;

/**
 *
 * @author yarik
 */
public class TargetAndColor {

    Target target;
    String playerName;
    GameColors color;

    
    public TargetAndColor(String playerName, GameColors color, Target target) {
        this.playerName = playerName;
        this.color = color;
        this.target = target;
    }
    public TargetAndColor() {
        this.playerName = null;
        this.color = null;
        this.target = null;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public GameColors getColor() {
        return color;
    }

    public void setColor(GameColors color) {
        this.color = color;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }
    
    public void SetTargetAndColor(String playerName, GameColors color, Target target) {
        this.playerName = playerName;
        this.color = color;
        this.target = target;
    }
    
       public static Target GetTargetByColor(GameColors color , List<TargetAndColor> listTargetAndColor)
    {
        for(TargetAndColor item : listTargetAndColor)
        {
            if(item.color == color)
            {
                return item.getTarget();
            }
        }
        return null;
    }
    
               public static TargetAndColor GetTargetAndColorByColor(GameColors color , List<TargetAndColor> listTargetAndColor)
    {
        for(TargetAndColor item : listTargetAndColor)
        {
            if(item.color == color)
            {
                return item;
            }
        }
        return null;
    }
       
}
