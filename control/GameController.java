package control;

import java.util.ArrayList;
import models.GameCell;
import models.GameBoard;
import java.util.List;
import models.GameColors;
import models.GamePlayer;
import models.GameRow;
import models.GamePlayerType;
import view.IO;
import models.IndexOfMarbel;
import models.Target;
import models.TargetAndColor;
import static view.IO.printMassage;
import static view.IO.printWrongNumber;

public class GameController {

    private GameBoard board = new GameBoard();

    static GamePlayerType type;
    static GameManager game;

    static int getAction(GamePlayer player, int i) {

        int selection;
        if (player.getType() == type.COMPUTER) {

            IO.printMassage("choose action:");
            selection = IO.getInputFromUser("1.let the computer play   2.exit to menu");
            while (!SettingController.checkValidateSelect(1, 2, selection)) {
                selection = IO.getInputFromUser("1.let the computer play   2.exit to menu");
            }

            return selection;
        } else {
            selection = IO.getNextPlayerAction(player.getName(), player.getColor().get(i));
        }

        while (!(SettingController.checkValidateSelect(1, 2, selection))) {
            IO.printWrongNumber();
            selection = IO.getNextPlayerAction(player.getName(), player.getColor().get(i));
        }
        return selection;

    }


    public void initGame(List<GamePlayer> players) {
        initBoard();
        initPlayersOnBoard(players);

    }

    public void initBoard() {
        List<GameRow> boardCells = new ArrayList<GameRow>();
        for (int i = 0; i < 17; i++) {
            GameRow rowCells = new GameRow();
            int columnNum = (i % 2 == 0) ? 13 : 12;
            int numOfCellsInRow = IO.rowsGameSize[i];
            int offset = (columnNum - numOfCellsInRow) / 2;
            for (int j = 0; j < columnNum; j++) {
                if (j < offset || j > numOfCellsInRow + offset - 1) {
                    rowCells.getRow().add(new GameCell(false));
                } else {
                    rowCells.getRow().add(new GameCell(i, j, GameColors.EMPTY, true));
                }
            }
            boardCells.add(rowCells);

        }
        board.setGameBoard(boardCells);

    }

    private void initPlayersOnBoard(List<GamePlayer> players) {

        int numPlayers = players.size();
        int numColorsPerPlayer = players.get(0).getColor().size();

        for (int i = 0; i < numPlayers; i++) {
            GamePlayer player = players.get(i);
            for (int j = 0; j < numColorsPerPlayer; j++) {
                GameColors color = player.getColor().get(j);
                occupyTriangle(color, numColorsPerPlayer, numPlayers, players.get(i).getName());
            }
        }
    }

    //asks which triangle is free according to a pre-defined order
    //and occupy it
    private void occupyTriangle(GameColors color, int numOfColorsForEachPlayer, int numOfPlayers, String playerName) {

        switch (numOfColorsForEachPlayer) {

            case 1:
                setAsOneColor(color, playerName);
                break;
            case 2:
                if (numOfPlayers == 2) {
                    setAsTwoColors(color, numOfPlayers, playerName);
                } else {
                    setAsThreeColors(color, playerName);
                }
                break;
            case 3:
                setAsThreeColors(color, playerName);
                break;

        }

    }

    private void occupyVerticalTriangle(GameColors gameColor, int startRow, boolean isExtend, boolean isFirstCells) {
        int numCellsToFill = isExtend ? 1 : 4;
        for (int i = startRow; i < startRow + 4; i++) {
            occupyCellsInRow(i, numCellsToFill, isFirstCells, gameColor);
            if (isExtend) {
                numCellsToFill++;
            } else {
                numCellsToFill--;
            }
        }
    }

    private void occupyCellsInRow(int rowNumber, int numOfCellsToFill, boolean isFirst, GameColors color) {
        GameRow row = board.getGameBoard().get(rowNumber);

        int indexToStart = (isFirst) ? 0 : row.getRow().size() - 1;

        if (isFirst) {

            for (int i = 0; i < row.getRow().size(); i++) {
                GameCell cell = row.getRow().get(i);
                if (cell.isValid() && numOfCellsToFill > 0) {
                    cell.setColor(color);
                    numOfCellsToFill--;
                }

            }
        } else {

            for (int i = row.getRow().size() - 1; i >= 0; i--) {
                GameCell cell = row.getRow().get(i);
                if (cell.isValid() && numOfCellsToFill > 0) {
                    cell.setColor(color);
                    numOfCellsToFill--;
                }

            }

        }

    }

    private void setAsOneColor(GameColors color, String playerName) {


        if (board.getGameBoard().get(0).getFirstValidCell().isColorEmpty()) {
            occupyVerticalTriangle(color, 0, true, true);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(17, 1)));

        } else if (board.getGameBoard().get(13).getFirstValidCell().isColorEmpty()) {
            occupyVerticalTriangle(color, 13, false, true);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(1, 1)));

        } else if (board.getGameBoard().get(4).getFirstValidCell().isColorEmpty()) {
            occupyVerticalTriangle(color, 4, false, true);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(13, 13)));

        } else if (board.getGameBoard().get(9).getLastValidCell().isColorEmpty()) {
            occupyVerticalTriangle(color, 9, true, false);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(5, 1)));

        } else if (board.getGameBoard().get(4).getLastValidCell().isColorEmpty()) {
            occupyVerticalTriangle(color, 4, false, false);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(13, 1)));

        } else if (board.getGameBoard().get(9).getFirstValidCell().isColorEmpty()) {
            occupyVerticalTriangle(color, 9, true, true);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(5, 13)));
        }

    }

    private void setAsTwoColors(GameColors color, int numOfPlayers, String playerName) {

        if (board.getGameBoard().get(0).getFirstValidCell().isColorEmpty()) {
            //upper triangle free, occupy it!
            occupyVerticalTriangle(color, 0, true, true);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(17, 1)));

        } else if (board.getGameBoard().get(4).getLastValidCell().isColorEmpty()) {
            occupyVerticalTriangle(color, 4, false, false);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(13, 1)));

        } else if (board.getGameBoard().get(13).getFirstValidCell().isColorEmpty()) {
            occupyVerticalTriangle(color, 13, false, true);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(1, 1)));

        } else if (board.getGameBoard().get(9).getFirstValidCell().isColorEmpty()) {
            occupyVerticalTriangle(color, 9, true, true);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(5, 13)));
        }

    }

    private void setAsThreeColors(GameColors color, String playerName) {
        if (board.getGameBoard().get(0).getFirstValidCell().isColorEmpty()) {
            occupyVerticalTriangle(color, 0, true, true);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(17, 1)));

        } else if (board.getGameBoard().get(4).getLastValidCell().isColorEmpty()) {
            occupyVerticalTriangle(color, 4, false, false);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(13, 1)));

        } else if (board.getGameBoard().get(9).getLastValidCell().isColorEmpty()) {
            occupyVerticalTriangle(color, 9, true, false);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(5, 1)));

        } else if (board.getGameBoard().get(13).getFirstValidCell().isColorEmpty()) {
            occupyVerticalTriangle(color, 13, false, true);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(1, 1)));

        } else if (board.getGameBoard().get(9).getFirstValidCell().isColorEmpty()) {
            occupyVerticalTriangle(color, 9, true, true);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(5, 13)));

        } else if (board.getGameBoard().get(4).getFirstValidCell().isColorEmpty()) {
            occupyVerticalTriangle(color, 4, false, true);
            SettingController.listTargetAndColor.add(new TargetAndColor(playerName, color, new Target(13, 13)));

        }
    }

    public void DoNextStep(GamePlayer player, GameColors color) {

        IndexOfMarbel choosenMarbel;

        if (player.getType() == type.COMPUTER) {
            choosenMarbel = board.getMarbelFromComputer(color);

        } else {
            IO.printMassage("choose a marble you want to move");
            choosenMarbel = IO.getMarbelPlaceFromUser(board);
        }

        while (!board.CheckValidMarbelOnBoard(choosenMarbel, false, color)) {
            IO.printMassage("The marble you choosed isn't correct , choose again");
            choosenMarbel = IO.getMarbelPlaceFromUser(board);
        }

        moveMarble(choosenMarbel, player, color);

    }

    public void moveMarble(IndexOfMarbel chosenMarbel, GamePlayer player, GameColors color) {
        IndexOfMarbel placeToMove = new IndexOfMarbel();

        if (player.getType() == GamePlayerType.COMPUTER) {
            placeToMove = board.getMarbelToMoveFromComputer(chosenMarbel, TargetAndColor.GetTargetByColor(color, SettingController.listTargetAndColor));
            IO.printMassage("the computer:" + player.getName() + " searching place to move...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        } else {
            placeToMove = IO.getPlaceToMove(board);
        }

        while (!board.CheckValidMarbelOnBoard(placeToMove, false, GameColors.EMPTY)) {
            IO.printMassage("You Cant Move There");

            int result = IO.getInputFromUser("1.Choose Another Place     2. change youre marbel");
            while (!SettingController.checkValidateSelect(1, 4, result)) {
                IO.printWrongNumber();
                result = IO.getInputFromUser("1.Choose Another Place     2. change youre marbel");
            }
            if (result == 2) {
                DoNextStep(player, color);
            } else {
                placeToMove = IO.getMarbelPlaceFromUser(board);
            }
        }

        if (!board.CheckLegalMove(chosenMarbel, placeToMove, false)) {
            if (board.CheckLegalMove(chosenMarbel, placeToMove, true)) {
                board.setNewMarbelPlace(chosenMarbel, placeToMove);
                IO.printBoard(board);
                secondJump(placeToMove);

            } else {
                IO.printMassage("You Cant Move There Choose Another Place");
                moveMarble(chosenMarbel, player, color);
            }
        } else {
            board.setNewMarbelPlace(chosenMarbel, placeToMove);
            IO.printBoard(board);
        }
    }

    public boolean DoActionInnerGame(int selection, GamePlayer player, GameColors color, int index, List<GamePlayer> players) {

        boolean keepPlaying = true;

        switch (selection) {
            case 1:
                DoNextStep(player, color);
                keepPlaying = board.IsPlayerWin(color, SettingController.listTargetAndColor);

                if (!keepPlaying) {
                    IO.printMassage("congrats!! player: " + player.getName() + "win!");
                }

                break;
            case 2:
                IO.printMassage("chose youre wish:");
                int result = IO.getInputFromUser("1.save game   2.quite current player from game  3.keep playng   4.exit game");
                while (!SettingController.checkValidateSelect(1, 4, result)) {
                    IO.printWrongNumber();
                    result = IO.getInputFromUser("1.save game   2.quite current player from game  3.keep playng   4.exit game");
                }
                keepPlaying = DoMenuSelect(result, player, color, index, players);

                break;

            default:
                break;
        }
        return keepPlaying;
    }

    private boolean DoMenuSelect(int result, GamePlayer player, GameColors color, int index, List<GamePlayer> players) {
        int select = 0;
        boolean keepPlaying = true;
        switch (result) {

            case 1:
                if (XmlLoaderAndSaver.isfirstSave) {
                    XmlLoaderAndSaver.SaveGameToXmlFile(board, players, player.getName());
                    XmlLoaderAndSaver.isfirstSave = false;
                    IO.printMassage("The Game saved first Time!");
                } else {
                    IO.printMassage("Choose option of Saving:");
                    int selection = IO.getInputFromUser("1. Same direction   2. new direction");

                    while (!(SettingController.checkValidateSelect(1, 2, selection))) {
                        printWrongNumber();
                        selection = IO.getInputFromUser("1. Same direction   2. new direction");
                    }
                    if (selection == 1) {
                        XmlLoaderAndSaver.isSamedirection = true;
                    } else if (selection == 2) {
                        XmlLoaderAndSaver.isSamedirection = false;
                    }
                    XmlLoaderAndSaver.SaveGameToXmlFile(board, players, player.getName());
                }
                select = getAction(player, index);
                keepPlaying = DoActionInnerGame(select, player, color, index, players);

                break;
            case 2:
                exitPlayerFromeGame(player, players);
                IO.printBoard(board);
                IO.printMassage("The Player " + player.getName() + " removed");
                keepPlaying = !players.isEmpty();
                break;
            case 3:
                IO.printBoard(board);
                select = getAction(player, index);
                keepPlaying = DoActionInnerGame(select, player, color, index, players);
                break;
            case 4:
                keepPlaying = false;
                break;
            default:
                break;

        }
        return keepPlaying;
    }

    private void secondJump(IndexOfMarbel chosenMarbel) {
        IndexOfMarbel placeToMove = new IndexOfMarbel();

        if (jumpAgain()) {
            placeToMove = IO.getPlaceToMove(board);
            if (!board.CheckLegalMove(chosenMarbel, placeToMove, true) || !board.canJampAgain(chosenMarbel, placeToMove)) {
                IO.printMassage("You cant move There!");
                secondJump(chosenMarbel);
            } else {
                IO.printMassage("Choose marble:");
                board.setNewMarbelPlace(chosenMarbel, placeToMove);
                IO.printBoard(board);
                secondJump(placeToMove);
            }
        }

    }

    public static boolean jumpAgain() {

        printMassage(" Choose action");
        int selection = IO.getInputFromUser("1. Continue Jumping Move   2. finish step");

        while (!(SettingController.checkValidateSelect(1, 2, selection))) {
            printWrongNumber();
            selection = IO.getInputFromUser("1. Continue Jumping Move   2. finish step");
        }

        return selection == 1;

    }

    private void exitPlayerFromeGame(GamePlayer player, List<GamePlayer> players) {

        for (int i = 0; i < player.getColor().size(); i++) {

            board.TakeOutColorsFromBoard(player.getColor().get(i));

        }
        GameManager.RemovePlayerFromList(player);
    }

    public GameBoard getBoard() {
        return board;
    }

}
