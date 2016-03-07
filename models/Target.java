/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author yarik
 */
  public class Target {

        private int row;
        private int col;

        public Target(){}
            
                
        public Target (int row , int col) {
            this.row = row;
            this.col = col;
        }
        
        public void setRow(int row) {
            this.row = row;
        }

        public void setCol(int col) {
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

    }
