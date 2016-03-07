package control;

import java.util.ArrayList;
import java.util.List;
import models.GameColors;
import view.IO;
import models.GamePlayer;
import models.GamePlayerType;
import models.TargetAndColor;

public class SettingController {

    private boolean playFromXml ;

    
    private final int LOAD_NEW_GAME = 1;
    final String[] names = {"RotemBot", "AssafBot", "MaorBot", "ChenBot", "YarikBot", "MomBot"};
    private List<GamePlayer> players = new ArrayList<GamePlayer>();
    static List<TargetAndColor> listTargetAndColor = new ArrayList<TargetAndColor>();
   

    public List<GamePlayer> getNewGameInfo()  {

        int userSelect = IO.printMainMenu();

        if (userSelect == LOAD_NEW_GAME) {
               return XmlLoaderAndSaver.getInfoFromXmlFile(this);
        
        } else{
            return getInfoFromUser();
        }
    }

  

    public List<GamePlayer> getInfoFromUser() {

        int numOfUsers = IO.NumberOfUsers();
        //TODO vaidate res>=2 && res<=6
        while (!(checkValidateSelect(2, 6, numOfUsers))) {
            IO.printWrongNumber();
            numOfUsers = IO.NumberOfUsers();
        }

        int numOfColors = IO.setNumberOfColors();
        //TODO validate num colors correlate with num players
        while (!(checkValidateSelect(1, (6 / numOfUsers), numOfColors))) {
            IO.printWrongNumber();
            numOfColors = IO.setNumberOfColors();
        }

        int numHuman = IO.setNumberOfHumanPlayers();
        while (numHuman > numOfUsers) {
            IO.printWrongNumber();
            numHuman = IO.setNumberOfHumanPlayers();
        }

        createPlayers(numOfUsers - numHuman, numOfColors, GamePlayerType.COMPUTER);
        createPlayers(numHuman, numOfColors, GamePlayerType.HUMAN);

        return players;
    }

    private void createPlayers(int numPlayers, int numOfColors, GamePlayerType gamePlayerType) {

        while (numPlayers > 0) {
            GamePlayer player = new GamePlayer();
            player.setType(gamePlayerType);
            String playerName = (gamePlayerType == GamePlayerType.COMPUTER) ? generateName(numPlayers) : IO.setPlayerName();
            player.setName(playerName);
            player.setColor(generateColor(numOfColors));
            players.add(player);
            numPlayers--;
        }
    }

    private List<GameColors> generateColor(int numOfColors) {
        List<GameColors> colors = new ArrayList<GameColors>();

        for (int i = 0; i < numOfColors; i++) {
            colors.add(GameColors.values()[players.size() * numOfColors + i]);
        }
        return colors;
    }

    private String generateName(int indexOfName) {

        return names[indexOfName - 1];
    }

    static boolean checkValidateSelect(int min, int max, int numToCheck) {
        return numToCheck >= min && numToCheck <= max;
    }
    
    public boolean IfTheGameFromXmlfile()
            {
                return playFromXml;
            }
    
     public void setPlayFromXml(boolean playFromXml) {
        this.playFromXml = playFromXml;
    }
}
