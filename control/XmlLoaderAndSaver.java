/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Chinese_CheckersGame.xml.Board;
import Chinese_CheckersGame.xml.Cell;
import Chinese_CheckersGame.xml.ChineseCheckers;
import Chinese_CheckersGame.xml.Color;
import Chinese_CheckersGame.xml.ColorType;
import Chinese_CheckersGame.xml.PlayerType;
import Chinese_CheckersGame.xml.Players;
import Chinese_CheckersGame.xml.Players.Player;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import models.GameBoard;
import models.GameColors;
import static models.GameColors.BLACK;
import models.GamePlayer;
import models.GamePlayerType;
import static models.GamePlayerType.HUMAN;
import models.GameRow;
import models.Target;
import models.TargetAndColor;
import view.IO;

/**
 *
 * @author yarik
 */
public class XmlLoaderAndSaver {

    static boolean isfirstSave = true;
    static boolean isSamedirection = false;
    static String PathFromTheUser;
    private static String nameOfSavedFile;
    public static ChineseCheckers chineseCheckersClass;

    public static ChineseCheckers LoadGameFromXmlFile() {
        String thePathToTheFile = GetThePathToTheFile();

        try {
            File xmlFile = new File(thePathToTheFile);
            JAXBContext jaxbContext = JAXBContext.newInstance(ChineseCheckers.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ChineseCheckers chineseCheckersClass = (ChineseCheckers) jaxbUnmarshaller.unmarshal(xmlFile);
            return chineseCheckersClass;
        } catch (JAXBException e) {
            return null;
        }

    }

    public static void SaveGameToXmlFile(GameBoard board, List<GamePlayer> players, String nameOfCurrentPlayer) {
        try {
            ChineseCheckers chineseCheckersClass = new ChineseCheckers();
            JAXBContext jaxbContext = JAXBContext.newInstance(ChineseCheckers.class);
            SetBoardToXml(chineseCheckersClass, board);
            SetPlayerListtoXml(chineseCheckersClass, players);
            chineseCheckersClass.setCurrentPlayer(nameOfCurrentPlayer);
            Marshaller marshaller = jaxbContext.createMarshaller();

            if (!isSamedirection) {
                nameOfSavedFile = IO.SetaNameToXmlFile();
            }

            marshaller.marshal(chineseCheckersClass, new File(nameOfSavedFile));

            MoveXmlFileToSpecificPlace();

        } catch (Exception e) {
            IO.printMassage("error,didn't save the Game");
        }

    }

    private static void SetBoardToXml(ChineseCheckers chineseCheckersClass, GameBoard board) {
        int index = 0;
        List<GameRow> row = board.getGameBoard();
        Board xmlboard = new Board();
        for (GameRow rowFromGame : row) {
            for (int i = 0; i < rowFromGame.getRow().size(); i++) {
                if (!(rowFromGame.getRow().get(i).isColorEmpty() || rowFromGame.getRow().get(i).getColor() == null)) {
                    Cell cell = new Cell();
                    cell.setCol(rowFromGame.getRow().get(i).getCol() - IO.RangeFromtheFirstIndex[index] + 1);
                    cell.setRow(rowFromGame.getRow().get(i).getRow() + 1);
                    SetColor(cell, rowFromGame.getRow().get(i).getColor());
                    xmlboard.getCell().add(cell);
                }
            }
            index++;
        }
        chineseCheckersClass.setBoard(xmlboard);
    }

    private static void SetColor(Cell cell, GameColors color) {
        switch (color) {

            case BLACK:
                cell.setColor(Color.BLACK);
                break;
            case WHITE:
                cell.setColor(Color.WHITE);
                break;
            case RED:
                cell.setColor(Color.RED);
                break;
            case GREEN:
                cell.setColor(Color.GREEN);
                break;
            case BLUE:
                cell.setColor(Color.BLUE);
                break;
            case YELLOW:
                cell.setColor(Color.YELLOW);
                break;

        }
    }

    private static void SetPlayerListtoXml(ChineseCheckers chineseCheckersClass, List<GamePlayer> players) {
        Players allplayers = new Players();

        for (GamePlayer player : players) {
            Players.Player onePlayer = new Players.Player();
            onePlayer.setName(player.getName());
            SetType(onePlayer, player);
            SetColors(onePlayer, player.getColor());

            allplayers.getPlayer().add(onePlayer);

        }
        chineseCheckersClass.setPlayers(allplayers);

    }

    private static void SetType(Player onePlayer, GamePlayer player) {
        if (player.getType() == HUMAN) {
            onePlayer.setType(PlayerType.HUMAN);
        } else {
            onePlayer.setType(PlayerType.COMPUTER);
        }
    }

    private static void SetColors(Player onePlayer, List<GameColors> color) {

        for (GameColors item : color) {
            ColorType type = new ColorType();
            ColorType.Target xmlColorTarget = new ColorType.Target();

            xmlColorTarget.setCol(TargetAndColor.GetTargetByColor(item, SettingController.listTargetAndColor).getCol());
            xmlColorTarget.setRow(TargetAndColor.GetTargetByColor(item, SettingController.listTargetAndColor).getRow());

            switch (item) {
                case BLACK:
                    type.setColor(Color.BLACK);
                    type.setTarget(xmlColorTarget);
                    break;
                case WHITE:
                    type.setTarget(xmlColorTarget);
                    type.setColor(Color.WHITE);
                    break;
                case RED:
                    type.setTarget(xmlColorTarget);
                    type.setColor(Color.RED);
                    break;
                case GREEN:
                    type.setTarget(xmlColorTarget);
                    type.setColor(Color.GREEN);
                    break;
                case BLUE:
                    type.setTarget(xmlColorTarget);
                    type.setColor(Color.BLUE);
                    break;
                case YELLOW:
                    type.setTarget(xmlColorTarget);
                    type.setColor(Color.YELLOW);
                    break;

            }
            onePlayer.getColorDef().add(type);
        }
    }

    private static void MoveXmlFileToSpecificPlace() {

        if (isfirstSave) {
            PathFromTheUser = IO.setDirectionOfTheFile();
            MoveFile();
            isfirstSave = false;
        } else if ((!isfirstSave) && isSamedirection) {
            MoveFile();
        } else {
            PathFromTheUser = IO.setDirectionOfTheFile();
            MoveFile();
        }

    }

    private static void MoveFile() {
        String absolutePath = null;
        String filePath = null;
        File temp = new File(nameOfSavedFile);
        absolutePath = temp.getAbsolutePath();
        filePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
        File afile = new File(absolutePath);

        if (afile.renameTo(new File(PathFromTheUser + "\\" + afile.getName()))) {
            System.out.println("File Saved successful!");
        } else {
            System.out.println("Failed to move the File, Saved in The Folder Of the Project");
            PathFromTheUser = filePath;
        }

    }

    private static boolean CheckIfTheFileExist(String pathTotheFile) {
        File xmlfile = new File(pathTotheFile);
        if (xmlfile.exists() && (!xmlfile.isDirectory())) {
            return true;
        } else {
            return false;
        }
    }

    private static String GetThePathToTheFile() {

        IO.printMassage("Choose option of Loading:");
        int selection = GetTheSelection();

        String path = GetThePathAfterSelection(selection);

        while (!(CheckIfTheFileExist(path))) {
            IO.printMassage("The file doesn't exist");
            IO.printMassage("Give the name of the file that you whant to load:");
            selection = GetTheSelection();
            path = GetThePathAfterSelection(selection);
        }
        return path;
    }

    private static String GetThePathAfterSelection(int selection) {

        if (selection == 1) {
            IO.printMassage("Give the name of the file:");
            return IO.SetaNameToXmlFile();
        } else {
            IO.printMassage("Give the name of the file that you whant to load:");
            String nameOfTheloadFile = IO.SetaNameToXmlFile();
            String pathTotheFile = IO.setDirectionOfTheFile();
            return pathTotheFile + "\\" + nameOfTheloadFile;
        }
    }

    private static int GetTheSelection() {

        int selection = IO.getInputFromUser("1. Get the file from project folder   2. Get the file from other direction");

        while (!(SettingController.checkValidateSelect(1, 2, selection))) {
            IO.printWrongNumber();
            selection = IO.getInputFromUser("1. Get the file from project folder   2. Get the file from other direction");
        }
        return selection;

    }

    public static boolean CheckIfligalFile() {
        if (!(CheckIfLigalBoard())) {
            return false;
        }

        if (!(CheckIflegalTargetColorType())) {
            return false;
        }

        return true;
    }

    public static boolean CheckIfLigalBoard() {
        return CheckAllTheCells();
    }

    private static boolean CheckAllTheCells() {
        int[] colorArray = new int[6];
        for (int i = 0; i < chineseCheckersClass.getBoard().getCell().size(); i++) {
            Cell item = chineseCheckersClass.getBoard().getCell().get(i);
            if (!(CheckIflegalColor(item.getColor(), colorArray))) {
                return false;
            }
            if (item.getRow() > 17 || item.getRow() < 1) {
                return false;
            }
            if (item.getRow() % 2 == 0) {
                if (item.getCol() < 1 || item.getCol() > 12) {
                    return false;
                }
            }
            if (item.getRow() % 2 != 0) {
                if (item.getCol() < 1 || item.getCol() > 13) {
                    return false;
                }
            }
        }
        if (!(CheckIfTheNumberOfMarbelIsCorrect(colorArray))) {
            return false;
        }
        return true;
    }

    private static boolean CheckIflegalTargetColorType() {

        for (Player playeritem : chineseCheckersClass.getPlayers().getPlayer()) {
            if (!(CheckIflegalType(playeritem.getType()))) {
                return false;
            }
            for (ColorType coloritem : playeritem.getColorDef()) {
                if (!(CheckIflegalTarget(coloritem.getTarget()))) {
                    return false;
                }
                if (!(CheckIflegalColor(coloritem.getColor()))) {
                    return false;
                }

            }
        }
        return true;
    }

    private static boolean CheckIflegalTarget(ColorType.Target target) {
        if (CheckIfRowAndCollegal(target, 1, 1)) {
            return true;
        }
        if (CheckIfRowAndCollegal(target, 5, 1)) {
            return true;
        }
        if (CheckIfRowAndCollegal(target, 5, 13)) {
            return true;
        }
        if (CheckIfRowAndCollegal(target, 13, 1)) {
            return true;
        }
        if (CheckIfRowAndCollegal(target, 13, 13)) {
            return true;
        }
        if (CheckIfRowAndCollegal(target, 17, 1)) {
            return true;
        }

        return false;

    }

    private static boolean CheckIfRowAndCollegal(ColorType.Target target, int row, int col) {
        if ((target.getRow() == row) && target.getCol() == col) {
            return true;
        } else {
            return false;
        }

    }

    private static boolean CheckIflegalType(PlayerType type) {
        if (type == PlayerType.COMPUTER) {
            return true;
        }
        if (type == PlayerType.HUMAN) {
            return true;
        }
        return false;
    }

    private static boolean CheckIflegalColor(Color color) {
        if (color == Color.BLACK) {
            return true;
        }
        if (color == Color.BLUE) {
            return true;
        }
        if (color == Color.GREEN) {
            return true;
        }
        if (color == Color.RED) {
            return true;
        }
        if (color == Color.WHITE) {
            return true;
        }
        if (color == Color.YELLOW) {
            return true;
        }
        return false;
    }

    private static boolean CheckIflegalColor(Color color, int[] colors) {
        if (color == Color.BLACK) {
            colors[0]++;
            return true;
        }
        if (color == Color.BLUE) {
            colors[1]++;
            return true;
        }
        if (color == Color.GREEN) {
            colors[2]++;
            return true;
        }
        if (color == Color.RED) {
            colors[3]++;
            return true;
        }
        if (color == Color.WHITE) {
            colors[4]++;
            return true;
        }
        if (color == Color.YELLOW) {
            colors[5]++;
            return true;
        }
        return false;
    }

    private static boolean CheckIfTheNumberOfMarbelIsCorrect(int[] colorArray) {
        for (int i = 0; i < 6; i++) {
            if (!(colorArray[i] == 0 || colorArray[i] == 10)) {
                return false;
            }
        }
        return true;
    }

    public static void InitBoardFromXmlFile(GameController gamecontroller) {
        List<Cell> allTheCellsFromBoard;
        allTheCellsFromBoard = XmlLoaderAndSaver.chineseCheckersClass.getBoard().getCell();

        gamecontroller.initBoard();

        for (Cell item : allTheCellsFromBoard) {
            PutCellIntoTheBoard(item, gamecontroller.getBoard());
        }
        IO.printBoard(gamecontroller.getBoard());

    }

    private static void PutCellIntoTheBoard(Cell cellFromXml, GameBoard board) {
        Color colorFromXml = cellFromXml.getColor();
        int rowFromXml = cellFromXml.getRow() - 1;
        int colFromXml = cellFromXml.getCol() - 1;
        SetTheColorToCell(colorFromXml, rowFromXml, colFromXml, board);

    }

    private static void SetTheColorToCell(Color colorFromXml, int rowFromXml, int colFromXml, GameBoard board) {
        switch (colorFromXml) {

            case BLACK:
                board.getGameBoard().get(rowFromXml).getCellInRow(colFromXml + (IO.RangeFromtheFirstIndex[rowFromXml])).setColor(GameColors.BLACK);
                break;
            case WHITE:
                board.getGameBoard().get(rowFromXml).getCellInRow(colFromXml + (IO.RangeFromtheFirstIndex[rowFromXml])).setColor(GameColors.WHITE);
                break;
            case RED:
                board.getGameBoard().get(rowFromXml).getCellInRow(colFromXml + (IO.RangeFromtheFirstIndex[rowFromXml])).setColor(GameColors.RED);
                break;
            case GREEN:
                board.getGameBoard().get(rowFromXml).getCellInRow(colFromXml + (IO.RangeFromtheFirstIndex[rowFromXml])).setColor(GameColors.GREEN);
                break;
            case BLUE:
                board.getGameBoard().get(rowFromXml).getCellInRow(colFromXml + (IO.RangeFromtheFirstIndex[rowFromXml])).setColor(GameColors.BLUE);
                break;
            case YELLOW:
                board.getGameBoard().get(rowFromXml).getCellInRow(colFromXml + (IO.RangeFromtheFirstIndex[rowFromXml])).setColor(GameColors.YELLOW);
                break;

        }
    }

    public static void PutTheCurrentPlayerFirstFromXml(List<GamePlayer> players) {

        String nameOfCurrentPlayer = XmlLoaderAndSaver.chineseCheckersClass.getCurrentPlayer();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals(nameOfCurrentPlayer)) {
                SwapTheItemWithFirstIndex(players, i);
            }
        }
    }

    private static void SwapTheItemWithFirstIndex(List<GamePlayer> players, int index) {
        GamePlayer tempPlayer;
        tempPlayer = players.get(index);
        players.set(index, players.get(0));
        players.set(0, tempPlayer);
    }

    public static List<GamePlayer> GetAllPlayersFromXmlFile() {
        boolean ifComputer;

        List<GamePlayer> newplayers = new ArrayList<GamePlayer>();
        List<Players.Player> playersList = chineseCheckersClass.getPlayers().getPlayer();
        for (Players.Player item : playersList) {

            GamePlayer newplayer = new GamePlayer();
            newplayer.setName(item.getName());
            ifComputer = CheckIfComputerOrHuman(item.getType());
            if (ifComputer) {
                newplayer.setType(GamePlayerType.COMPUTER);
            } else {
                newplayer.setType(GamePlayerType.HUMAN);
            }
            SetColorsToThePlayer(newplayer, item);
            newplayers.add(newplayer);
        }
        return newplayers;
    }

    private static void SetColorsToThePlayer(GamePlayer newplayer, Players.Player xmlplayer) {
        List<GameColors> colors = new ArrayList<GameColors>();

        for (ColorType item : xmlplayer.getColorDef()) {
            TargetAndColor targetandcolor = new TargetAndColor();
            Target target = new Target();
            target.setCol(item.getTarget().getCol());
            target.setRow(item.getTarget().getRow());

            switch (item.getColor()) {
                case BLACK:
                    colors.add(GameColors.BLACK);
                    targetandcolor.SetTargetAndColor(xmlplayer.getName(), GameColors.BLACK, target);
                    break;
                case WHITE:
                    colors.add(GameColors.WHITE);
                    targetandcolor.SetTargetAndColor(xmlplayer.getName(), GameColors.WHITE, target);
                    break;
                case RED:
                    colors.add(GameColors.RED);
                    targetandcolor.SetTargetAndColor(xmlplayer.getName(), GameColors.RED, target);
                    break;
                case GREEN:
                    colors.add(GameColors.GREEN);
                    targetandcolor.SetTargetAndColor(xmlplayer.getName(), GameColors.GREEN, target);
                    break;
                case BLUE:
                    colors.add(GameColors.BLUE);
                    targetandcolor.SetTargetAndColor(xmlplayer.getName(), GameColors.BLUE, target);
                    break;
                case YELLOW:
                    colors.add(GameColors.YELLOW);
                    targetandcolor.SetTargetAndColor(xmlplayer.getName(), GameColors.YELLOW, target);
                    break;

            }
            SettingController.listTargetAndColor.add(targetandcolor);
        }
        newplayer.setColor(colors);
    }

    private static boolean CheckIfComputerOrHuman(PlayerType type) {
        if (type == PlayerType.COMPUTER) {
            return true;
        } else {
            return false;
        }
    }

    public static List<GamePlayer> getInfoFromXmlFile(SettingController settingcontoller) { 
        settingcontoller.setPlayFromXml(true);
        try {
            XmlLoaderAndSaver.chineseCheckersClass = XmlLoaderAndSaver.LoadGameFromXmlFile();
            if (XmlLoaderAndSaver.CheckIfligalFile()) {
                return XmlLoaderAndSaver.GetAllPlayersFromXmlFile();
            } else {
                IO.printMassage("Illigal xml file!");
                return null;

            }
        } catch (Exception e) {
            IO.printMassage("Illigal xml file!");
            settingcontoller.setPlayFromXml(false);
            return null;
        }

    }

}
