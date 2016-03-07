package control;

import java.util.ArrayList;
import java.util.List;
import models.GamePlayer;
import view.IO;

public class GameManager { // class that in charge of the flow of the game

    public static List<GamePlayer> players = new ArrayList<GamePlayer>();


    private SettingController settingController = new SettingController();
    private static GameController gamecontroller = new GameController();
    private static boolean usersWantsToPlay = true;
    private static boolean keepPlaying = true;

    public void startGame() {
        players = null;
        
        while (players == null) {
          players = settingController.getNewGameInfo();
        }
         if (settingController.IfTheGameFromXmlfile()) {
            XmlLoaderAndSaver.PutTheCurrentPlayerFirstFromXml(players);
            XmlLoaderAndSaver.InitBoardFromXmlFile(gamecontroller);
        } 
         else{   
        gamecontroller.initGame(players);
         }
        IO.printPlayerList(players, SettingController.listTargetAndColor );
        IO.printBoard(gamecontroller.getBoard());

        RunTheGame();

        

    }

    public static void RunTheGame()  {
        while (usersWantsToPlay) {
            usersWantsToPlay = PlayGame();
        }
    }

    public static boolean PlayGame()  {

        int PlayerSize;

        finishGame:
        while (keepPlaying) {
            
            
            for (int i =0 ; i < players.size() ; i++ ) {
                GamePlayer player = players.get(i);
                PlayerSize =players.size();
                
                changePlayer:
                for (int j = 0; j < player.getColor().size(); j++) {

                  
                        int selection = GameController.getAction(player, j);
                        
                        keepPlaying = gamecontroller.DoActionInnerGame(selection, player, player.getColor().get(j), j, players);
                        
                      
                          if (!keepPlaying) {
                            continue finishGame;
                        }
                      
                        if(PlayerSize != players.size()) {
                            i = players.size() -1;
                            player = players.get(i);
                             
                              continue changePlayer;
                        }
                     
                      
                    } 
                }

            }

 
        IO.printMassage("End Game. Hope You Enjoyed Thank you for playing!");
        return false;
    }

    public static void RemovePlayerFromList(GamePlayer playerToRemove) {

        for (int i = 0; i < players.size(); i++) {
            if (playerToRemove.equals(players.get(i))) {
                players.remove(i);
            }
        }

    }
}
