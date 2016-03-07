/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Maor.a
 */
public class GameBoard {

    private List<GameRow> gameBoard = new ArrayList<GameRow>();
    private List<GameCell> cells = new ArrayList<GameCell>();

    public List<GameCell> getCells() {
        return cells;
    }

    public void setCells(List<GameCell> cells) {
        this.cells = cells;
    }

    public List<GameRow> getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(List<GameRow> gameBoard) {
        this.gameBoard = gameBoard;
    }

    public boolean CheckValidMarbelOnBoard(IndexOfMarbel marbel, boolean allColors, GameColors color) {

        if (marbel.getRow() > 16 || marbel.getRow() < 0) {
            return false;
        }
        if (marbel.getRow() % 2 == 0) {
            if (marbel.getCol() < 0 || marbel.getCol() >= 13) {
                return false;
            }
        }
        if (marbel.getRow() % 2 != 0) {
            if (marbel.getCol() < 0 || marbel.getCol() >= 12) {
                return false;
            }
        }

        GameCell cell = this.gameBoard.get(marbel.getRow()).getRow().get(marbel.getCol());
        if (!cell.isValid) {
            return false;
        }
        if (!allColors && (cell.getColor() != color)) {
            return false;
        }
        return true;
    }

    public void setNewMarbelPlace(IndexOfMarbel choosenMarbel, IndexOfMarbel placeToMove) {

        GameColors color = this.gameBoard.get(choosenMarbel.getRow()).getRow().get(choosenMarbel.getCol()).getColor();
        this.gameBoard.get(choosenMarbel.getRow()).getCellInRow(choosenMarbel.getCol()).setColor(GameColors.EMPTY);
        this.gameBoard.get(placeToMove.getRow()).getCellInRow(placeToMove.getCol()).setColor(color);
    }

    public boolean CheckLegalMove(IndexOfMarbel choosenMarbel, IndexOfMarbel placeToMove, boolean isJump) {

        List<IndexOfMarbel> validMarbleList = getValidCellsAround(choosenMarbel, isJump);
        if (isJump) {

            List<IndexOfMarbel> validJumpList = getValidCellsAround(placeToMove, isJump);
            for (IndexOfMarbel srcMarble : validMarbleList) {
                for (IndexOfMarbel jumpMarble : validJumpList) {
                    if (srcMarble.equals(jumpMarble)
                            && this.gameBoard.get(srcMarble.getRow()).getCellInRow(srcMarble.getCol()).getColor() != GameColors.EMPTY) {
                        return true;
                    }
                }
            }
        }
        for (IndexOfMarbel validMarble : validMarbleList) {
            if (placeToMove.equals(validMarble)) {
                return true;
            }
        }
        return false;
    }

    public List<IndexOfMarbel> getValidCellsAround(IndexOfMarbel choosenMarbel, boolean isJump) {
        int row = choosenMarbel.getRow();
        int col = choosenMarbel.getCol();

        if (row % 2 == 0) {
            return getMarbelsFromEvenRow(row, col, isJump);
        } else {
            return getMarbelsFromOddRow(row, col, isJump);
        }
    }

    private List<IndexOfMarbel> getMarbelsFromEvenRow(int row, int col, boolean isJump) {

        List<IndexOfMarbel> checkedMarbels = new ArrayList<IndexOfMarbel>();
        List<IndexOfMarbel> validMarbels = new ArrayList<IndexOfMarbel>();

        if (row == 0) {
            row++;
        }
        if (row == 16) {
            row--;
        }
        if (col == 0) {
            col++;
        }
        if (col == 12) {
            col--;
        }

        checkedMarbels.add(new IndexOfMarbel(row - 1, col - 1));
        checkedMarbels.add(new IndexOfMarbel(row - 1, col));
        checkedMarbels.add(new IndexOfMarbel(row, col + 1));
        checkedMarbels.add(new IndexOfMarbel(row + 1, col));
        checkedMarbels.add(new IndexOfMarbel(row + 1, col - 1));
        checkedMarbels.add(new IndexOfMarbel(row, col - 1));

        for (int i = 0; i < 6; i++) {
            if (CheckValidMarbelOnBoard(checkedMarbels.get(i), isJump, GameColors.EMPTY)) {
                validMarbels.add(checkedMarbels.get(i));
            }
        }

        return validMarbels;
    }

    private List<IndexOfMarbel> getMarbelsFromOddRow(int row, int col, boolean isJump) {
        List<IndexOfMarbel> checkedMarbels = new ArrayList<IndexOfMarbel>();
        List<IndexOfMarbel> validMarbels = new ArrayList<IndexOfMarbel>();

        if (row == 0) {
            row++;
        }
        if (row == 16) {
            row--;
        }
        if (col == 0) {
            col++;
        }
        if (col == 11) {
            col--;
        }

        checkedMarbels.add(new IndexOfMarbel(row - 1, col));
        checkedMarbels.add(new IndexOfMarbel(row - 1, col + 1));
        checkedMarbels.add(new IndexOfMarbel(row, col + 1));
        checkedMarbels.add(new IndexOfMarbel(row + 1, col + 1));
        checkedMarbels.add(new IndexOfMarbel(row + 1, col));
        checkedMarbels.add(new IndexOfMarbel(row, col - 1));

        for (int i = 0; i < 6; i++) {

            if (CheckValidMarbelOnBoard(checkedMarbels.get(i), isJump, GameColors.EMPTY)) {
                validMarbels.add(checkedMarbels.get(i));
            }
        }
        return validMarbels;
    }

    public boolean canJampAgain(IndexOfMarbel choosenMarbel, IndexOfMarbel placeToMove) {

        List<IndexOfMarbel> validMarbleList = getValidCellsAround(choosenMarbel, true);

        for (IndexOfMarbel marbel : validMarbleList) {
            if (marbel.equals(placeToMove)) {
                return false;
            }

        }
        return true;
    }

    public void TakeOutColorsFromBoard(GameColors colorTODelete) {

        int i;

        for (GameRow row : this.gameBoard) {

            for (i = 0; i < row.getRow().size(); i++) {

                GameColors color = row.getRow().get(i).getColor();
                if (colorTODelete == color) {
                    row.getRow().get(i).setColor(GameColors.EMPTY);
                }
            }
        }

    }

    private boolean IsMarbelValidToMove(IndexOfMarbel marbel, GameColors color) {

        if (CheckValidMarbelOnBoard(marbel, false, color)) {
            if (getValidCellsAround(marbel, false).size() > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public IndexOfMarbel getMarbelToMoveFromComputer(IndexOfMarbel choosenMarbel, Target target) {

        List<IndexOfMarbel> validPlacesTomove = new ArrayList<IndexOfMarbel>();
        IndexOfMarbel marbel;
        validPlacesTomove = getValidCellsAround(choosenMarbel, false);

        marbel = findBestMarbelToMove(validPlacesTomove, target);

        return marbel;
    }

    private IndexOfMarbel findBestMarbelToMove(List<IndexOfMarbel> validPlacesTomove, Target target) {

        IndexOfMarbel min = validPlacesTomove.get(0);
        int minDistance = calculateDistanceToTarget(validPlacesTomove.get(0), target);
        int toCheck;
        for (int i = 1; i < validPlacesTomove.size(); i++) {
            toCheck = calculateDistanceToTarget(validPlacesTomove.get(i), target);
            if (toCheck < minDistance) {
                min = validPlacesTomove.get(i);
                minDistance = toCheck;
            }
        }
        return min;
    }

    private int calculateDistanceToTarget(IndexOfMarbel marbel, Target target) {

        int sumDistance;
        int row;
        int col;

        col = marbel.getCol() - target.getCol();
        if (col < 0) {
            col = col * -1;
        }
        row = marbel.getRow() - target.getRow();
        if (row < 0) {
            row = row * -1;
        }
        sumDistance = row + col;

        return sumDistance;
    }

    public IndexOfMarbel getMarbelFromComputer(GameColors colorToTake) {
        List<IndexOfMarbel> MarbelsOption = new ArrayList<IndexOfMarbel>();

        Random rand = new Random();

        for (int i = 0; i < gameBoard.size(); i++) {
            GameRow row = this.gameBoard.get(i);

            for (int j = 0; j < row.getRow().size(); j++) {

                GameColors color = row.getRow().get(j).getColor();
                if (colorToTake == color) {

                    IndexOfMarbel marbel = new IndexOfMarbel(i, j);

                    if (IsMarbelValidToMove(marbel, color)) {
                        MarbelsOption.add(marbel);
                    }
                }

            }
        }

        int n = rand.nextInt(MarbelsOption.size());
        return MarbelsOption.get(n);
    }

    public boolean IsPlayerWin(GameColors color, List<TargetAndColor> listTargetAndColor) {

        boolean win;
        TargetAndColor targetToCheck = new TargetAndColor();
        targetToCheck = TargetAndColor.GetTargetAndColorByColor(color, listTargetAndColor);
        win = isTriangleTargetFull(targetToCheck);

        return !win;
    }

    private boolean isTriangleTargetFull(TargetAndColor targetToCheck) {

        GameColors color = targetToCheck.getColor();
        int rowToStart = targetToCheck.getTarget().getRow();
        int colToStart = targetToCheck.getTarget().getCol();

        boolean win = false;

        if (rowToStart == 1 && colToStart == 1) {
            win = IsTriangleFull(color, 0, true, true);

        } else if (rowToStart == 17 && colToStart == 1) {
            win = IsTriangleFull(color, 13, false, true);

        } else if (rowToStart == 13 && colToStart == 13) {
            win = IsTriangleFull(color, 9, true, false);

        } else if (rowToStart == 5 && colToStart == 13) {
            win = IsTriangleFull(color, 4, false, false);

        } else if (rowToStart == 13 && colToStart == 1) {
            win = IsTriangleFull(color, 9, true, true);

        } else if (rowToStart == 5 && colToStart == 1) {
            win = IsTriangleFull(color, 4, false, true);

        }
        return win;

    }

    private boolean IsTriangleFull(GameColors gameColor, int startRow, boolean isExtend, boolean isFirstCells) {

        boolean win = true;

        int numCellsToFill = isExtend ? 1 : 4;
        for (int i = startRow; (i < startRow + 4) && win == true; i++) {
            win = IsRowFull(i, numCellsToFill, isFirstCells, gameColor);
            if (isExtend) {
                numCellsToFill++;
            } else {
                numCellsToFill--;
            }
        }
        return win;
    }

    private boolean IsRowFull(int rowNumber, int numOfCellsToFill, boolean isFirst, GameColors color) {

        GameRow row = gameBoard.get(rowNumber);
        boolean win = true;

        if (isFirst) {

            for (int i = 0; i < row.getRow().size(); i++) {
                GameCell cell = row.getRow().get(i);
                if (cell.isValid() && numOfCellsToFill > 0) {
                    if (cell.getColor() != color) {
                        win = false;
                    }
                    numOfCellsToFill--;
                }
            }
        } else {

            for (int i = row.getRow().size() - 1; i >= 0; i--) {
                GameCell cell = row.getRow().get(i);
                if (cell.isValid() && numOfCellsToFill > 0) {
                    if (cell.getColor() != color) {
                        win = false;
                    }
                    numOfCellsToFill--;
                }
            }
        }
        return win;
    }
}
