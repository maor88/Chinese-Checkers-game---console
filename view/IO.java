package view;

import java.util.List;
import java.util.Scanner;
import models.GameBoard;
import models.GameRow;
import models.GameColors;
import models.GamePlayer;
import models.IndexOfMarbel;
import models.TargetAndColor;
import models.Target;

public class IO {

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public final static int[] rowsGameSize = {1, 2, 3, 4, 13, 12, 11, 10, 9, 10, 11, 12, 13, 4, 3, 2, 1};
    public final static int[] RangeFromtheFirstIndex = {6, 5, 5, 4, 0, 0, 1, 1, 2, 1, 1, 0, 0, 4, 5, 5, 6};
    static Scanner sc = new Scanner(System.in);
    static int choosen;

    public static int printMainMenu() {
        System.out.println("************************************************************");
        System.out.println("Welcome to Chinese Checkers Game , please choose:");
        choosen = printStartMessage();

        while (!CheckMainMenuInput(choosen)) {
            System.out.println("Please enter the right input");
            choosen = printStartMessage();
        }

        return choosen;
    }

    private static int printStartMessage() {
        String massage = "1.load game   Or     2.new game";
        return getInputFromUser(massage);
    }

    private static boolean CheckMainMenuInput(int numberToCheck) {

        if (numberToCheck == 1) {
            return true;
        }
        return numberToCheck == 2;
    }

    public static int NumberOfUsers() {

        String massage = "Please enter number of players between 2-6 :";
        return getInputFromUser(massage);

    }

    public static int setNumberOfColors() {

        String massage = "Please enter number of colors for each player:";
        return getInputFromUser(massage);

    }

    public static int setNumberOfHumanPlayers() {

        String massage = "Please enter number of Humen players:";
        return getInputFromUser(massage);

    }

    public static String setPlayerName() {
        System.out.println("Please enter player name");
        return sc.next();
    }

    public static void printWrongNumber() {
        System.out.println("You entered wrong");
    }

    public static void printMassage(String massage) {
        System.out.println(massage);
    }

    public static int getInputFromUser(String massage) {

        int res = 0;
        boolean isInputNumber = false;

        do {
            try {
                System.out.println(massage);
                res = sc.nextInt();
                isInputNumber = true;
            } catch (Exception e) {
                System.out.println("This is not a number , try again");
                sc.nextLine();
            }
        } while (!isInputNumber);

        return res;

    }

    public static void printBoard(GameBoard board) {

        int i;
        int indexOfLine = 0;
        for (GameRow row : board.getGameBoard()) {
            indexOfLine++;
            if (indexOfLine < 10) {
                System.out.print(" " + ANSI_BLACK + indexOfLine + ". ");
            } else {
                System.out.print(ANSI_BLACK + indexOfLine + ". ");
            }

            for (i = 0; i < row.getRow().size(); i++) {

                boolean isValidCell = row.getRow().get(i).isValid();
                GameColors colorForCell = row.getRow().get(i).getColor();

                if (row.getRow().size() % 2 == 0) {
                    System.out.print(" ");

                    if (isValidCell) {
                        printTheColor(colorForCell);
                    } else {
                        System.out.print(" ");
                    }

                } else {
                    if (isValidCell) {
                        printTheColor(colorForCell);
                    } else {
                        System.out.print(" ");
                    }

                    System.out.print(" ");
                }
            }
            System.out.println(ANSI_BLACK);
        }

    }

    private static void printTheColor(GameColors color) {

        switch (color) {

            case BLACK:
                System.out.print(ANSI_BLACK + "X");
                break;
            case BLUE:
                System.out.print(ANSI_BLUE + "X");
                break;
            case GREEN:
                System.out.print(ANSI_GREEN + "X");
                break;

            case RED:
                System.out.print(ANSI_RED + "X");
                break;

            case WHITE:
                System.out.print(ANSI_WHITE + "X");
                break;

            case YELLOW:
                System.out.print(ANSI_YELLOW + "X");
                break;

            case EMPTY:
                System.out.print(ANSI_BLACK + "O");
                break;

            default:

                break;
        }

    }

    public static int getNextPlayerAction(String PlayerName, GameColors color) {

        System.out.println(ANSI_BLACK + PlayerName + "(" + color.value() + ") choose  youre action");

        return getInputFromUser("1.move marbel   2.exit to menu");
    }

    public static IndexOfMarbel getMarbelPlaceFromUser(GameBoard board) {

        IndexOfMarbel index = new IndexOfMarbel();
        int userRow = getInputFromUser("enter row:") - 1;
        index.setRow(userRow);
        int userCol = getInputFromUser("enter index of marbel from left to right, start from 1:");
        index.setCol(board.getGameBoard().get(index.getRow()).getFirstValidCell().getCol() + userCol - 1);
        return index;
    }

    public static void printWrongRowEnterd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static IndexOfMarbel getPlaceToMove(GameBoard board) {
        System.out.println("choose place to move the marbel");
        return getMarbelPlaceFromUser(board);
    }

    public static void printMarbel(int row, int col) {
        System.out.println(row);
        System.out.println(col);

    }

    public static String SetaNameToXmlFile() {
        String tempString;
        printMassage("Write a File Name without .xml :");
        System.out.println();
        sc.nextLine();
        tempString = sc.nextLine();

        return tempString + ".xml";
    }

    public static String setDirectionOfTheFile() {
        System.out.println("Please enter the Exact direction to save the File without sign / in the end:");
        return sc.next();
    }

    public static void printPlayerList(List<GamePlayer> players, List<TargetAndColor> listTargetandColor) {
        System.out.println("************************************************************");
        System.out.println("Game Details:");
        for (GamePlayer player : players) {

            System.out.println(player.toString());
            for (int i = 0; i < player.getColor().size(); i++) {
                Target target = TargetAndColor.GetTargetByColor(player.getColor().get(i), listTargetandColor);
                GameColors color = player.getColor().get(i);
                System.out.println("target color " + color + ": (" + target.getRow() + "," + target.getCol() + ")");
            }
            System.out.println();
        }

    }
}
