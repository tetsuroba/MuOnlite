package engine;

import characters.Enemy;
import characters.Spider;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Robi
 */
public class EasyBattle extends Battle {

    public EasyBattle(int playerLevel, JPanel upperPanel, JPanel lowerPanel) {
        super("Könnyű", 5, upperPanel, lowerPanel);
        setEnemy(new Spider(playerLevel));

    }

    @Override
    public int xpCalc(Enemy enemy) {
        Random r = new Random();
        return r.nextInt(20) + getXpLost() + 1;
    }

}
