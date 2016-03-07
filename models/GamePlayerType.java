/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


public enum GamePlayerType {

    HUMAN,
    COMPUTER;

    public String value() {
        return name();
    }

    public static GamePlayerType fromValue(String v) {
        return valueOf(v);
    }

}
