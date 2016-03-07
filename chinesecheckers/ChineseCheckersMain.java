package chinesecheckers;

import control.GameManager;
import javax.xml.bind.JAXBException;

public class ChineseCheckersMain {

    public static void main(String[] args)throws JAXBException {
        GameManager game = new GameManager();
        game.startGame();
       

    }

}
